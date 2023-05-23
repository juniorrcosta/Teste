package br.com.santander.internetbankingtestesantander;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.santander.internetbanking.InternetBankingApplication;
import br.com.santander.internetbanking.config.ResourceBusinessException;
import br.com.santander.internetbanking.entity.Cliente;
import br.com.santander.internetbanking.repository.ClienteRepository;
import br.com.santander.internetbanking.resource.ClienteResource;
import br.com.santander.internetbanking.resource.assembler.ClienteResourceAssembler;
import br.com.santander.internetbanking.service.ClienteService;

@AutoConfigureMockMvc
@SpringBootTest(classes = InternetBankingApplication.class)
public class ClienteServiceTest {

    @Mock
    private ClienteResourceAssembler clienteResourceAssembler;

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;

    /*@Test
    public void testCadastro_ValidData() {
        ClienteResource resource = createValidClienteResource();
        Cliente cliente = createClienteFromResource(resource);

        when(repository.findByNumeroConta(resource.getNumeroConta())).thenReturn(null);
        when(repository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteResourceAssembler.toResource(any(Cliente.class))).thenReturn(resource);

        ClienteResource response = clienteService.cadastro(resource);

        assertEquals(resource, response);
    }*/

    @Test
    public void testCadastro_InvalidData() {
        ClienteResource resource = createInvalidClienteResource();

        ResourceBusinessException exception = new ResourceBusinessException(HttpStatus.BAD_REQUEST, "400", "Data Inválida.");

        try {
            clienteService.cadastro(resource);
        } catch (ResourceBusinessException ex) {
            assertEquals(exception.getHttpStatus(), ex.getHttpStatus());
            assertEquals(exception.getErrorCode(), ex.getErrorCode());
            assertEquals(exception.getMessage(), ex.getMessage());
        }
    }

    @Test
    public void testCadastro_ExistingConta() {
        ClienteResource resource = createValidClienteResource();
        Cliente cliente = createClienteFromResource(resource);

        when(repository.findByNumeroConta(resource.getNumeroConta())).thenReturn(cliente);

        ResourceBusinessException exception = new ResourceBusinessException(HttpStatus.BAD_REQUEST, "400", "Conta já existe");

        try {
            clienteService.cadastro(resource);
        } catch (ResourceBusinessException ex) {
            assertEquals(exception.getHttpStatus(), ex.getHttpStatus());
            assertEquals(exception.getErrorCode(), ex.getErrorCode());
            assertEquals(exception.getMessage(), ex.getMessage());
        }
    }

    @Test
    public void testListaClientes() {
        Page<Cliente> mockPage = createMockClientePage();
        Pageable pageable = Pageable.unpaged();

        when(repository.findAll(pageable)).thenReturn(mockPage);
        when(clienteResourceAssembler.toResource(any(Cliente.class))).thenReturn(createValidClienteResource());

        Page<ClienteResource> response = clienteService.listaClientes(pageable);

        assertEquals(mockPage.getTotalElements(), response.getTotalElements());
        assertEquals(mockPage.getContent().size(), response.getContent().size());
    }

    @Test
    public void testBuscaPorId_ExistingCliente() {
        ClienteResource resource = createValidClienteResource();
        Cliente cliente = createClienteFromResource(resource);

        when(repository.findById(resource.getIdCliente())).thenReturn(Optional.of(cliente));
        when(clienteResourceAssembler.toResource(cliente)).thenReturn(resource);

        ClienteResource response = clienteService.buscaPorId(resource.getIdCliente());

        assertEquals(resource, response);
    }

    @Test
    public void testBuscaPorId_NonExistingCliente() {
        Long clienteId = 1L;

        when(repository.findById(clienteId)).thenReturn(Optional.empty());

        ClienteResource response = clienteService.buscaPorId(clienteId);

        assertEquals(null, response);
    }

    @Test
    public void testBuscaPorConta_ExistingCliente() {
        ClienteResource resource = createValidClienteResource();
        Cliente cliente = createClienteFromResource(resource);

        when(repository.findByNumeroConta(resource.getNumeroConta())).thenReturn(cliente);
        when(clienteResourceAssembler.toResource(cliente)).thenReturn(resource);

        ClienteResource response = clienteService.buscaPorConta(resource.getNumeroConta());

        assertEquals(resource, response);
    }

    @Test
    public void testBuscaPorConta_NonExistingCliente() {
        String nrConta = "123456";

        when(repository.findByNumeroConta(nrConta)).thenReturn(null);

        ClienteResource response = clienteService.buscaPorConta(nrConta);

        assertEquals(null, response);
    }

    // Helper methods

    private ClienteResource createValidClienteResource() {
        ClienteResource resource = new ClienteResource();
        resource.setIdCliente(1L);
        resource.setNome("John Doe");
        resource.setDataNascimento("1990-01-01");
        resource.setNumeroConta("123456789");
        return resource;
    }

    private ClienteResource createInvalidClienteResource() {
        ClienteResource resource = new ClienteResource();
        resource.setIdCliente(1L);
        resource.setNome("John Doe");
        resource.setDataNascimento("InvalidDate");
        resource.setNumeroConta("123456789");
        return resource;
    }

    private Cliente createClienteFromResource(ClienteResource resource) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(resource.getIdCliente());
        cliente.setNome(resource.getNome());
        cliente.setDataNascimento(resource.getDataNascimento());
        cliente.setNumeroConta(resource.getNumeroConta());
        return cliente;
    }

    private Page<Cliente> createMockClientePage() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(createClienteFromResource(createValidClienteResource()));

        return new PageImpl<>(clientes);
    }
}
