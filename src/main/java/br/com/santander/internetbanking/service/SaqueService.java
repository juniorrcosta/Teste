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
	    BigDecimal valor = saqueResource.getValor();
	    BigDecimal cem = new BigDecimal("100");
	    BigDecimal trezentos = new BigDecimal("300");
	    boolean bTaxa = true;
	    TTaxa regraAplicada = TTaxa.NENHUM;

	    if (cliente.getPlanoExclusive()) {
	        return new Taxa(valor, valor, TTaxa.EXCLUSIVE, false);
	    }

	    BigDecimal taxa = valor.compareTo(cem) < 0 ? BigDecimal.ZERO :
	            valor.compareTo(trezentos) < 0 ? new BigDecimal("0.004") : new BigDecimal("0.01");

	    BigDecimal valorComTaxa = valor.add(valor.multiply(taxa));
	    regraAplicada = valor.compareTo(cem) < 0 ? TTaxa.NENHUM :
	            valor.compareTo(trezentos) < 0 ? TTaxa.ENTRE100E300 : TTaxa.MAIOR300;

	    return new Taxa(valor, valorComTaxa, regraAplicada, bTaxa);
	}

}
