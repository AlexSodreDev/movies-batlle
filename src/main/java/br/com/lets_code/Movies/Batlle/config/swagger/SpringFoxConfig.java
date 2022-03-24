package br.com.lets_code.Movies.Batlle.config.swagger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@SpringBootApplication
@EnableSwagger2
public class SpringFoxConfig {

  @Bean
  public Docket movieBatlleSwagger() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build().apiInfo(this.metaData());
  }

  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .title("Sales API")
        .description(
            "Movies Batlle is a game wihch consumes information from imdb external api and displays movies in the form of a quiz for the player to guess.")
        .version("1.0")
        .contact(new Contact("Gabriel Mendes", "https://github.com/gabrielmendes17",
            "gabrielmendes17@gmail.com"))
        .license("Apache License Version 2.0")
        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
        .build();
  }

}