package br.com.santander.internetbanking.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.santander.internetbanking.entity.Movimentacao;
import br.com.santander.internetbanking.model.TipoOperacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    Page<Movimentacao> findByTipoOperacao(Pageable pageable, TipoOperacao tipoOperacao);

    Page<Movimentacao> findByDataTransacaoAndTipoOperacao(Pageable pageable, LocalDate dataTransacao, TipoOperacao tipoOperacao);
}
