package br.com.lets_code.Movies.Batlle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Principal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.lets_code.Movies.Batlle.presenter.rest.controllers.MoviesBattleController;
import br.com.lets_code.Movies.Batlle.presenter.rest.dtos.request.LoginRequest;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class MoviesBatlleApplicationTests {

	@Autowired
	MoviesBattleController moviesBattleController;

	@Autowired
	MockMvc mockMvc;

	private final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJHQUJSSUVMIiwiaWF0IjoxNjQ3OTk5MzE1LCJleHAiOjE2NDg5OTkzMTV9.rKb9Aiev0LLaYKr617DxU5hkAYFYHQLle-T3X77P73zRnaWBPQzITQPhipGKiXhxloMzrH3_-yIx6vkG3UKDKw";

	@Test
	void contextLoads() {
		assertTrue(true);
	}

	@Test
	@Order(1)
	void shouldReturn200WithAccessTokenWhenUserIsAuthorized() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		var signIn = new LoginRequest("GABRIEL", "123456");
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson= ow.writeValueAsString(signIn);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("http://localhost:8080/api/auth/signin")
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON);
		
		var result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("Before All init() method called");
		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getResponse());
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(response.getStatus(), 200);
	}

	@Test
	@Order(2)
	void shouldCreateNewMoviesBatlleAndReturn201() throws Exception {
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("GABRIEL");
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("http://localhost:8080/api/movies_battle/begin")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
			.principal(mockPrincipal)
			.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(201, status);
	}

	@Test
	@Order(3)
	void shouldReturnAResposeWithANewFilmPair() throws Exception {
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("GABRIEL");
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("http://localhost:8080/api/movies_battle/quizz")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
			.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		System.out.println(status);
	}

}
