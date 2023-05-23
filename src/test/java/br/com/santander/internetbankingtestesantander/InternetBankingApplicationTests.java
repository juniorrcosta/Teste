package br.com.santander.internetbankingtestesantander;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.santander.internetbanking.InternetBankingApplication;

@AutoConfigureMockMvc
@SpringBootTest(classes = InternetBankingApplication.class)
class InternetBankingApplicationTests {

	@Test
	void contextLoads() {
	}

}
