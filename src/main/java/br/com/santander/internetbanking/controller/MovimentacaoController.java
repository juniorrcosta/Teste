package br.com.santander.internetbanking.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.santander.internetbanking.model.TipoOperacao;
import br.com.santander.internetbanking.resource.DepositoResource;
import br.com.santander.internetbanking.resource.MovimentacaoResource;
import br.com.santander.internetbanking.resource.SaqueResource;
import br.com.santander.internetbanking.service.MovimentacaoService;
import br.com.santander.internetbanking.service.SaqueService;
import br.com.santander.internetbanking.service.DepositoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("movimentacoes")
public class MovimentacaoController {

    @Autowired
	private MovimentacaoService movimentacaoService;
    
    @Autowired
    private DepositoService depositoService;
    
    @Autowired
    private SaqueService saqueService;


    @Operation(summary = "Obter lista de transações")
    @GetMapping
    public ResponseEntity<Page<MovimentacaoResource>> obterLista(
            @PageableDefault(sort = {"dataMovimentacao"}) Pageable paginacao,
            @Parameter(description = "Data da movimentação", example = "2023-05-22")
            @RequestParam(required = false) String dataMovimentacao,
            @RequestParam TipoOperacao tipoOperacao) {

        Page<MovimentacaoResource> response =
        		movimentacaoService.obterLista(paginacao, dataMovimentacao, tipoOperacao);
        if(null == response){
            return new ResponseEntity<Page<MovimentacaoResource>>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/depositos")
    public ResponseEntity<DepositoResource> deposito(@RequestBody @Valid DepositoResource deposito) {
    	DepositoResource depositoResponse = depositoService.deposito(deposito);
        return ResponseEntity.ok(depositoResponse);
    }
    
    
    @PostMapping("/saques")
    public ResponseEntity<SaqueResource> sacar(@RequestBody @Valid SaqueResource saque) {
    	SaqueResource saqueResponse = saqueService.saque(saque);
        return ResponseEntity.ok(saqueResponse);
    }


    @GetMapping("/todas")
    public ResponseEntity<Page<MovimentacaoResource>> obterTransacoes() {
        return ResponseEntity.ok(movimentacaoService
                .obterListaTransacoes());
    }


}
