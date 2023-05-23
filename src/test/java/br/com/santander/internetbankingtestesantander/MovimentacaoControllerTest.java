package br.com.santander.internetbankingtestesantander;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.santander.internetbanking.InternetBankingApplication;
import br.com.santander.internetbanking.controller.MovimentacaoController;
import br.com.santander.internetbanking.model.TipoOperacao;
import br.com.santander.internetbanking.resource.DepositoResource;
import br.com.santander.internetbanking.resource.MovimentacaoResource;
import br.com.santander.internetbanking.resource.SaqueResource;
import br.com.santander.internetbanking.service.DepositoService;
import br.com.santander.internetbanking.service.MovimentacaoService;
import br.com.santander.internetbanking.service.SaqueService;

@AutoConfigureMockMvc
@SpringBootTest(classes = InternetBankingApplication.class)
public class MovimentacaoControllerTest {

    @Mock
    private MovimentacaoService movimentacaoService;

    @Mock
    private DepositoService depositoService;

    @Mock
    private SaqueService saqueService;

    @InjectMocks
    private MovimentacaoController movimentacaoController;

    /*@Test
    public void testObterLista() {
        Page<MovimentacaoResource> mockPage = createMockMovimentacaoResourcePage();
        when(movimentacaoService.obterLista(any(Pageable.class), anyString(), any(TipoOperacao.class)))
                .thenReturn(mockPage);

        ResponseEntity<Page<MovimentacaoResource>> response =
                movimentacaoController.obterLista(null, "2023-05-22", TipoOperacao.DEPOSITO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
    }*/

    @Test
    public void testObterLista_NullResponse() {
        when(movimentacaoService.obterLista(any(Pageable.class), anyString(), any(TipoOperacao.class)))
                .thenReturn(null);

        ResponseEntity<Page<MovimentacaoResource>> response =
                movimentacaoController.obterLista(null, "2023-05-22", TipoOperacao.DEPOSITO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeposito() {
        DepositoResource mockDepositoResource = new DepositoResource(null, "1234567890");
        when(depositoService.deposito(any(DepositoResource.class))).thenReturn(mockDepositoResource);

        ResponseEntity<DepositoResource> response = movimentacaoController.deposito(mockDepositoResource);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockDepositoResource, response.getBody());
    }

    @Test
    public void testSacar() {
        SaqueResource mockSaqueResource = new SaqueResource(null, "1234567890");
        when(saqueService.saque(any(SaqueResource.class))).thenReturn(mockSaqueResource);

        ResponseEntity<SaqueResource> response = movimentacaoController.sacar(mockSaqueResource);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockSaqueResource, response.getBody());
    }

    @Test
    public void testObterTransacoes() {
        Page<MovimentacaoResource> mockPage = createMockMovimentacaoResourcePage();
        when(movimentacaoService.obterListaTransacoes()).thenReturn(mockPage);

        ResponseEntity<Page<MovimentacaoResource>> response = movimentacaoController.obterTransacoes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
    }

    private Page<MovimentacaoResource> createMockMovimentacaoResourcePage() {
        List<MovimentacaoResource> movimentacoes = new ArrayList<>();
        MovimentacaoResource movimentacao1 = new MovimentacaoResource();
        movimentacao1.setIdTransacao(1L);
        MovimentacaoResource movimentacao2 = new MovimentacaoResource();
        movimentacao2.setIdTransacao(2L);
        movimentacoes.add(movimentacao1);
        movimentacoes.add(movimentacao2);
        return new PageImpl<>(movimentacoes);
    }

}
