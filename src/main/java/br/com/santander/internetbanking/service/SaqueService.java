package br.com.santander.internetbanking.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.santander.internetbanking.config.ResourceBusinessException;
import br.com.santander.internetbanking.entity.Cliente;
import br.com.santander.internetbanking.entity.Movimentacao;
import br.com.santander.internetbanking.model.TTaxa;
import br.com.santander.internetbanking.model.Taxa;
import br.com.santander.internetbanking.repository.ClienteRepository;
import br.com.santander.internetbanking.repository.MovimentacaoRepository;
import br.com.santander.internetbanking.resource.SaqueResource;

@Service
public class SaqueService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private MovimentacaoRepository movimentacaoRepository;

	public SaqueResource saque(SaqueResource saque) {
		Cliente cliente = clienteRepository.findByNumeroConta(saque.getNumeroConta());
		if (cliente == null) {
			throw new ResourceBusinessException(HttpStatus.BAD_REQUEST, "400", "Conta não encontrada.");
		}

		Taxa taxa = verificaTaxa(saque, cliente);
		BigDecimal novoSaldo = cliente.getSaldo().subtract(taxa.getValor());
		cliente.setSaldo(novoSaldo);
		clienteRepository.save(cliente);
		movimentacaoRepository.save(new Movimentacao(cliente.getIdCliente(), LocalDate.now(), taxa));
		return new SaqueResource(cliente);
	}

	public Taxa verificaTaxa(SaqueResource saqueResource, Cliente cliente) {
		BigDecimal taxa = BigDecimal.ZERO;
		BigDecimal valor = saqueResource.getValor();
		BigDecimal cem = new BigDecimal("100");
		BigDecimal trezentos = new BigDecimal("300");
		boolean bTaxa = false;
		TTaxa regraAplicada = TTaxa.NENHUM;

		if (valor.compareTo(cem) < 0) {
			taxa = BigDecimal.ZERO;
			regraAplicada = TTaxa.NENHUM;
		} else if (valor.compareTo(trezentos) < 0) {
			taxa = new BigDecimal("0.004");
			bTaxa = true;
			regraAplicada = TTaxa.ENTRE100E300;
		} else {
			taxa = new BigDecimal("0.01");
			bTaxa = true;
			regraAplicada = TTaxa.MAIOR300;
		}

		if (cliente.getPlanoExclusive()) {
			taxa = BigDecimal.ZERO;
			bTaxa = false;
			regraAplicada = TTaxa.EXCLUSIVE;
		}

		BigDecimal valorComTaxa = valor.add(valor.multiply(taxa));

		return new Taxa(valor, valorComTaxa, regraAplicada, bTaxa);
	}
}
