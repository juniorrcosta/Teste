
package br.com.santander.internetbanking.resource;

import java.math.BigDecimal;

import br.com.santander.internetbanking.entity.Cliente;

public class SaqueResource extends OperacaoResource {

	public SaqueResource(BigDecimal valor, String numeroConta) {
		super(valor, numeroConta);
	}

	public SaqueResource(Cliente cliente) {
		super(cliente);
	}
	
}