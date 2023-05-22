package br.com.santander.internetbanking.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.santander.internetbanking.config.ResourceBusinessException;
import br.com.santander.internetbanking.entity.Cliente;
import br.com.santander.internetbanking.entity.Movimentacao;
import br.com.santander.internetbanking.repository.ClienteRepository;
import br.com.santander.internetbanking.repository.MovimentacaoRepository;
import br.com.santander.internetbanking.resource.DepositoResource;

@Service
public class DepositoService {

	@Autowired
    private ClienteRepository clienteRepository;
	
	@Autowired
    private MovimentacaoRepository movimentacaoRepository;


    public DepositoResource deposito(DepositoResource deposito) {
    	 Cliente cliente = clienteRepository.findByNumeroConta(deposito.getNumeroConta());
    	 
         if(cliente == null){
        	 throw new ResourceBusinessException(HttpStatus.BAD_REQUEST, "400", "Conta não encontrada.");
         }
         
        BigDecimal novoSaldo = cliente.getSaldo().add(deposito.getValor());
        cliente.setSaldo(novoSaldo);
        clienteRepository.save(cliente);

        movimentacaoRepository.save(new Movimentacao(cliente.getIdCliente(), LocalDate.now(), deposito.getValor()));
        return new DepositoResource(cliente);
    }
}
