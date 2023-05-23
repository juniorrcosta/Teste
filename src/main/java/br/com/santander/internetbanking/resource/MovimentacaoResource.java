package br.com.santander.internetbanking.resource;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import br.com.santander.internetbanking.controller.ClienteController;
import br.com.santander.internetbanking.entity.Movimentacao;
import br.com.santander.internetbanking.model.TTaxa;
import br.com.santander.internetbanking.model.TipoOperacao;

public class MovimentacaoResource  extends RepresentationModel<MovimentacaoResource> {
    private Long idTransacao;
    Long idCliente;
    private TipoOperacao tipoTransacao;
    private LocalDate dataTransacao;
    private Boolean cobradoTaxa;
    private BigDecimal valorOperacao;
    public MovimentacaoResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MovimentacaoResource(Iterable<Link> initialLinks) {
		super(initialLinks);
		// TODO Auto-generated constructor stub
	}

	public MovimentacaoResource(Link initialLink) {
		super(initialLink);
		// TODO Auto-generated constructor stub
	}

	private TTaxa regraAplicada;


    public MovimentacaoResource(Movimentacao transacao) {
    	BeanUtils.copyProperties(transacao, this);  

    	 Link clienteLink = WebMvcLinkBuilder.linkTo(
                 WebMvcLinkBuilder.methodOn(ClienteController.class).getClientePorId(idCliente)
         ).withRel("clientes");
         add(clienteLink);
         
    }

    // Getters e Setters

    public Long getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Long idTransacao) {
        this.idTransacao = idTransacao;
    }

    public TipoOperacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoOperacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public LocalDate getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDate dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public Boolean getCobradoTaxa() {
        return cobradoTaxa;
    }

    public void setCobradoTaxa(Boolean cobradoTaxa) {
        this.cobradoTaxa = cobradoTaxa;
    }

    public BigDecimal getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(BigDecimal valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

    public TTaxa getRegraAplicada() {
        return regraAplicada;
    }

    public void setRegraAplicada(TTaxa regraAplicada) {
        this.regraAplicada = regraAplicada;
    }

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	
}