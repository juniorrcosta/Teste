
package br.com.santander.internetbanking.resource;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import br.com.santander.internetbanking.entity.Cliente;

public class OperacaoResource {
	@NotNull(message = "Preencha o valor")
	private BigDecimal valor;

	@NotBlank(message = "Preencha o número da conta")
	private String numeroConta;


	public OperacaoResource(BigDecimal valor, String numeroConta) {
		this.valor = valor;
		this.numeroConta = numeroConta;
	}

	public OperacaoResource(Cliente cliente) {
		BeanUtils.copyProperties(cliente, this);
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
}