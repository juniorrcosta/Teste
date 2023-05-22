package br.com.santander.internetbanking.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.santander.internetbanking.config.ResourceBusinessException;
import br.com.santander.internetbanking.entity.Cliente;
import br.com.santander.internetbanking.repository.ClienteRepository;
import br.com.santander.internetbanking.resource.ClienteResource;
import br.com.santander.internetbanking.resource.assembler.ClienteResourceAssembler;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {
	
	@Autowired
    private ClienteResourceAssembler clienteResourceAssembler;

    @Autowired
    private ClienteRepository repository;

    @Transactional
    public ClienteResource cadastro(ClienteResource resource){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(resource.getDataNascimento(), formatter);
        }catch(Exception ex){
            throw new ResourceBusinessException(HttpStatus.BAD_REQUEST, "400", "Data Inválida.");
        }

        Cliente existeConta = repository.findByNumeroConta(resource.getNumeroConta());
        
        if(existeConta != null){
        	throw new ResourceBusinessException(HttpStatus.BAD_REQUEST, "400", "Conta já existe");
        }
        
        Cliente save = repository.save(clienteResourceAssembler.toEntity(resource));
        
        return clienteResourceAssembler.toResource(save);
    }

    public Page<ClienteResource> listaClientes(Pageable paginacao) {
        Page<Cliente> clientesPage = repository.findAll(paginacao);
        List<ClienteResource> clienteResources = clientesPage.getContent().stream()
                .map(ClienteResource::new)
                .collect(Collectors.toList());
        return new PageImpl<>(clienteResources, paginacao, clientesPage.getTotalElements());
    }

	public ClienteResource buscaPorId(Long clienteId) {
		Optional<Cliente> cliente = repository.findById(clienteId);
		if (!cliente.isPresent()){
			return null;
		}
		return clienteResourceAssembler.toResource(cliente.get());
	}
	
	public ClienteResource buscaPorConta(String nrConta) {
		Cliente cliente = repository.findByNumeroConta(nrConta);
		if (cliente == null){
			return null;
		}
		return clienteResourceAssembler.toResource(cliente);
	}
}
