package br.com.santander.internetbanking.entity;


import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_sequence")
    @SequenceGenerator(name = "cliente_sequence", sequenceName = "CLIENTE_SEQUENCE", allocationSize = 1)
    private Long idCliente;
    private String nome;
    private Boolean planoExclusive;
    private BigDecimal saldo;
    private String numeroConta;
    private String dataNascimento;

    

    public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getIdCliente() {
		return idCliente;
	}



	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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



}
