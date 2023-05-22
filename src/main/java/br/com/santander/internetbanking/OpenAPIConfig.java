package br.com.santander.internetbanking;
import java.util.List;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Links;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

  @Value("${internetbanking.openapi.dev-url}")
  private String devUrl;


  @Bean
  OpenAPI openApiConfig() {
	  
	License licenca = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
	
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Development environment");

    Contact contato = new Contact();
    contato.setEmail("juniorrcosta@live.com");
    contato.setName("Junior Costa");
    contato.setUrl("");

    Info info = new Info()
        .title("Internet Banking API")
        .version("1.0")
        .contact(contato)
        .description("This API exposes endpoints to manage tutorials.").termsOfService("")
        .license(licenca);
    
    SpringDocUtils.getConfig().addResponseTypeToIgnore(Links.class);

    return new OpenAPI().info(info).servers(List.of(devServer));
  }
}