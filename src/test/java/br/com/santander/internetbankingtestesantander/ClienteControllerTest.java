package br.com.santander.internetbankingtestesantander;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.santander.internetbanking.InternetBankingApplication;
import br.com.santander.internetbanking.resource.ClienteResource;
import br.com.santander.internetbanking.service.ClienteService;

@AutoConfigureMockMvc
@SpringBootTest(classes = InternetBankingApplication.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;
    
    @Test
    public void testCadastroCliente() throws Exception {
        ClienteResource clienteResource = new ClienteResource();
        clienteResource.setNome("John Doe");
        clienteResource.setPlanoExclusive(true);
        clienteResource.setSaldo(BigDecimal.ZERO);
        clienteResource.setNumeroConta("123456789");
        clienteResource.setDataNascimento("1990-01-01");

        Mockito.when(clienteService.cadastro(clienteResource)).thenReturn(clienteResource);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n  \"nome\": \"John Doe\",\n  \"planoExclusive\": true,\n  \"saldo\": 0,\n  \"numeroConta\": \"123456789\",\n  \"dataNascimento\": \"1990-01-01\"\n}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("John Doe"));
    }

    @Test
    public void testGetAllClientes() throws Exception {
        List<ClienteResource> clientes = new ArrayList<>();
        ClienteResource cliente1 = new ClienteResource();
        cliente1.setNome("John Doe");
        clientes.add(cliente1);
        ClienteResource cliente2 = new ClienteResource();
        cliente2.setNome("Jane Smith");
        clientes.add(cliente2);

        Page<ClienteResource> clientePage = new PageImpl<>(clientes);

        when(clienteService.listaClientes(Mockito.any(Pageable.class))).thenReturn(clientePage);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nome").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].nome").value("Jane Smith"));
    }

    @Test
    public void testGetClientePorId() throws Exception {
        Long idCliente = 1L;
        ClienteResource clienteResource = new ClienteResource();
        clienteResource.setIdCliente(idCliente);
        clienteResource.setNome("John Doe");

        Mockito.when(clienteService.buscaPorId(idCliente)).thenReturn(clienteResource);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/{idCliente}", idCliente))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("John Doe"));
    }

    @Test
    public void testGetClientePorConta() throws Exception {
        String nrConta = "123456789";
        ClienteResource clienteResource = new ClienteResource();
        clienteResource.setIdCliente(1L);
        clienteResource.setNome("John Doe");

        Mockito.when(clienteService.buscaPorConta(nrConta)).thenReturn(clienteResource);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/conta/{nrConta}", nrConta))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("John Doe"));
    }
}
