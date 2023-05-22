package br.com.santander.internetbanking.entity;


import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.santander.internetbanking.model.TTaxa;
import br.com.santander.internetbanking.model.Taxa;
import br.com.santander.internetbanking.model.TipoOperacao;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimentacao_sequence")
    @SequenceGenerator(name = "movimentacao_sequence", sequenceName = "MOVIMENTACAO_SEQUENCE", allocationSize = 1)
    Long idTransacao;
    Long idCliente;
    @Enumerated(EnumType.STRING)
    TipoOperacao tipoOperacao;
    LocalDate dataTransacao;
    Boolean cobradoTaxa;
    BigDecimal valorOperacao;
    @Enumerated(EnumType.STRING)
    TTaxa regraAplicada;

    public Movimentacao() {
    }

    public Movimentacao(Long idCliente,
                     LocalDate dataTransacao,
                     Taxa taxa) {

        this.idCliente = idCliente;
        this.tipoOperacao = TipoOperacao.SAQUE;
        this.dataTransacao = dataTransacao;
        this.cobradoTaxa = taxa.getCobradoTaxa();
        this.valorOperacao = taxa.getValorTaxa();
        this.regraAplicada = taxa.getRegra();
    }

    public Movimentacao(Long idCliente,
                     LocalDate dataTransacao,
                     BigDecimal valorOperacao) {

        this.idCliente = idCliente;
        this.tipoOperacao = TipoOperacao.DEPOSITO;
        this.dataTransacao = dataTransacao;
        this.cobradoTaxa = false;
        this.valorOperacao = valorOperacao;
        this.regraAplicada = TTaxa.NENHUM;
    }


    public Long getIdCliente() {
        return idCliente;
    }

    public Long getIdTransacao() {
        return idTransacao;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public TTaxa getRegraAplicada() {
        return regraAplicada;
    }

    public LocalDate getDataTransacao() {
        return dataTransacao;
    }

    public Boolean getCobradoTaxa() {
        return cobradoTaxa;
    }

    public BigDecimal getValorOperacao() {
        return valorOperacao;
    }

}
