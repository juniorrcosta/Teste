package br.com.santander.internetbanking.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.santander.internetbanking.resource.ClienteResource;
import br.com.santander.internetbanking.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("clientes")
@Tag(name = "Clientes", description = "API de gerenciamento de clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar um cliente")
    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do cliente", required = true, content = @Content(schema = @Schema(implementation = ClienteResource.class, example = "{\n  \"nome\": \"string\",\n  \"planoExclusive\": true,\n  \"saldo\": 0,\n  \"numeroConta\": \"string\",\n  \"dataNascimento\": \"string\"\n}")))
	public ClienteResource cadastro(@RequestBody @Valid ClienteResource cliente,
			UriComponentsBuilder uriBuilder) {
		return clienteService.cadastro(cliente);
	}

	@GetMapping
	public ResponseEntity<Page<ClienteResource>> lista(
			@PageableDefault(sort = { "nome" }) Pageable paginacao) {
		return ResponseEntity.ok(clienteService.listaClientes(paginacao));
	}

	@GetMapping(value = "/{idCliente}")
	public ResponseEntity<ClienteResource> getClientePorId(@PathVariable Long idCliente) {
	    return ResponseEntity.ok(clienteService.buscaPorId(idCliente));
	}
	
	@GetMapping(value = "/conta/{nrConta}")
	public ResponseEntity<ClienteResource> getClientePorConta(@PathVariable String nrConta) {
	    return ResponseEntity.ok(clienteService.buscaPorConta(nrConta));
	}
}