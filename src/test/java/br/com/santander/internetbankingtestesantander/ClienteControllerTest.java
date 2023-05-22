package br.com.santander.internetbankingtestesantander;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.santander.internetbanking.controller.ClienteController;
import br.com.santander.internetbanking.resource.ClienteResource;
import br.com.santander.internetbanking.service.ClienteService;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void testCadastroCliente() throws Exception {
        ClienteResource clienteResource = new ClienteResource();
        clienteResource.setNome("John Doe");
        // Defina outros atributos do cliente

        when(clienteService.cadastro(clienteResource)).thenReturn(clienteResource);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"JuniorCosta\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("JuniorCosta"));
    }

    @Test
    public void testGetAllClientes() throws Exception {
        // Defina uma lista de clientes simulada
        List<ClienteResource> clientes = new ArrayList<>();
        // Adicione clientes à lista

        when(clienteService.listaClientes(null)).thenReturn((Page<ClienteResource>) clientes);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(clientes.size()));
    }

    @Test
    public void testGetClientePorId() throws Exception {
        Long idCliente = 1L;
        ClienteResource cliente = new ClienteResource();
        cliente.setIdCliente(idCliente);
        cliente.setNome("John Doe");
        // Defina outros atributos do cliente

        when(clienteService.buscaPorId(idCliente)).thenReturn(cliente);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/{id}", idCliente))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCliente").value(idCliente))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("John Doe"));
    }

    @Test
    public void testGetClientePorConta() throws Exception {
        String nrConta = "123456789";
        ClienteResource cliente = new ClienteResource();
        cliente.setIdCliente(1L);
        cliente.setNome("John Doe");
        // Defina outros atributos do cliente

        when(clienteService.buscaPorConta(nrConta)).thenReturn(cliente);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/conta/{nrConta}", nrConta))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCliente").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("John Doe"));
    }
}
