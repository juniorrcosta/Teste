package br.com.santander.internetbanking.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.santander.internetbanking.config.ResourceBusinessException;
import br.com.santander.internetbanking.model.TipoOperacao;
import br.com.santander.internetbanking.repository.MovimentacaoRepository;
import br.com.santander.internetbanking.resource.MovimentacaoResource;

@Service
public class MovimentacaoService {

	@Autowired
    private MovimentacaoRepository extratoRepository;

	public Page<MovimentacaoResource> obterLista(Pageable paginacao, String dataMovimentacao, TipoOperacao tipoOperacao) {
        LocalDate date = null;
        if (dataMovimentacao != null && !dataMovimentacao.isBlank()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date = LocalDate.parse(dataMovimentacao, formatter);
            } catch (Exception e) {
                throw new ResourceBusinessException(HttpStatus.BAD_REQUEST, "400", "Data inválida");
            }
        }

        return extratoRepository.findByDataTransacaoAndTipoOperacao(paginacao, date, tipoOperacao)
                .map(MovimentacaoResource::new);
    }

    public Page<MovimentacaoResource> obterListaTransacoes() {
        return extratoRepository.findAll(Pageable.ofSize(10))
                .map(MovimentacaoResource::new);
    }
}
