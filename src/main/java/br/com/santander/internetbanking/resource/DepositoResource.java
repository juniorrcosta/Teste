package br.com.santander.internetbanking.resource;

import java.math.BigDecimal;

import br.com.santander.internetbanking.entity.Cliente;

public class DepositoResource extends OperacaoResource {

	public DepositoResource(Cliente cliente) {
		super(cliente);
	}
	
	public DepositoResource(BigDecimal valor, String numeroConta) {
		super(valor, numeroConta);
	}


}