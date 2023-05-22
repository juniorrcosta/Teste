package br.com.santander.internetbanking.resource;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.santander.internetbanking.controller.ClienteController;
import br.com.santander.internetbanking.entity.Cliente;

public class ClienteResource extends RepresentationModel<ClienteResource> {
	
	@JsonIgnore
	private Long idCliente;
	
	@NotBlank(message = "Preencha o nome")
	private String nome;

	@NotNull(message = "Informe o tipo de cliente")
	private Boolean planoExclusive;

	@NotNull(message = "Informe um saldo")
	private BigDecimal saldo;

	@NotBlank(message = "Informe o número da conta")
	private String numeroConta;

	@NotBlank(message = "Informe a data de nascimento")
	private String dataNascimento;

	public ClienteResource(Cliente cliente) {
		this.idCliente = cliente.getIdCliente();
	    this.nome = cliente.getNome();
	    this.planoExclusive = cliente.getPlanoExclusive();
	    this.saldo = cliente.getSaldo();
	    this.numeroConta = cliente.getNumeroConta();
	    this.dataNascimento = cliente.getDataNascimento();
	    
	    Link clienteLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ClienteController.class).getClientePorId(idCliente)
        ).withRel("clientes");
        add(clienteLink);
	}
	
	public ClienteResource() {
		super();
		
		Link clienteLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ClienteController.class).getClientePorId(idCliente)
        ).withRel("clientes");
        add(clienteLink);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getPlanoExclusive() {
		return planoExclusive;
	}

	public void setPlanoExclusive(Boolean planoExclusive) {
		this.planoExclusive = planoExclusive;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	

}
