package br.com.santander.internetbanking.resource.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.santander.internetbanking.controller.ClienteController;
import br.com.santander.internetbanking.entity.Cliente;
import br.com.santander.internetbanking.resource.ClienteResource;

import org.springframework.beans.BeanUtils;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ClienteResourceAssembler  extends RepresentationModelAssemblerSupport<Cliente, ClienteResource>  {

	 public ClienteResourceAssembler() {
	        super(ClienteController.class, ClienteResource.class);
	    }

	@Override
	public ClienteResource toModel(Cliente entity) {
		ClienteResource resource = new ClienteResource();
		BeanUtils.copyProperties(entity, resource);
		return resource;
	}
	
	public ClienteResource toResource(Cliente entity) {
		ClienteResource resource = new ClienteResource();
		BeanUtils.copyProperties(entity, resource);
		return resource;
	}
	
	public Cliente toEntity(ClienteResource resource) {
		Cliente entity = new Cliente();
		BeanUtils.copyProperties(resource, entity);
		return entity;
	}
	
	public List<Cliente> toEntities(Iterable<ClienteResource> resources) {
        return StreamSupport.stream(resources.spliterator(), false)
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
	
	 public List<Cliente> toEntities(List<ClienteResource> resources) {
	        return resources.stream()
	                .map(this::toEntity)
	                .collect(Collectors.toList());
	    }
	
	 public List<ClienteResource> toResources(Iterable<Cliente> entities) {
	        return StreamSupport.stream(entities.spliterator(), false)
	                .map(this::toModel)
	                .collect(Collectors.toList());
	    }
	
}
