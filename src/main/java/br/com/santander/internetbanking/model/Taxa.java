package br.com.santander.internetbanking.model;

import java.math.BigDecimal;

public class Taxa {
    private BigDecimal valor;
    private BigDecimal valorTaxa;
    private TTaxa regra;
    private Boolean cobradoTaxa;
    
    public Taxa(BigDecimal valorInicial, BigDecimal valorTaxa, TTaxa regra, Boolean cobradoTaxa) {
        this.valor = valorInicial;
        this.valorTaxa = valorTaxa;
        this.regra= regra;
        this.cobradoTaxa = cobradoTaxa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorTaxa() {
        return valorTaxa;
    }

    public void setValorTaxa(BigDecimal valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    public TTaxa getRegra() {
        return regra;
    }

    public void setRegraAplicada(TTaxa regra) {
        this.regra = regra;
    }

    public Boolean getCobradoTaxa() {
        return cobradoTaxa;
    }

    public void setCobradoTaxa(Boolean cobradoTaxa) {
        this.cobradoTaxa = cobradoTaxa;
    }
}