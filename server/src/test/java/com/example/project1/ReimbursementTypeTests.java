package com.example.project1;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReimbursementTypeTests {
    ApplicationContext app;
    HttpClient webClient;
    ObjectMapper objectMapper;

	@BeforeEach
    public void setUp() throws InterruptedException {
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        String[] args = new String[] {};
        app = SpringApplication.run(Project1Application.class, args);
        Thread.sleep(500);
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
    	Thread.sleep(500);
    	SpringApplication.exit(app);
    }

    /**
     * Test to see if able to get all reimbursement types
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    @Order(20)
    public void getReimbursementTypes() throws IOException, InterruptedException
	{
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/reimbursement/types"))
				.GET()
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		String expectedResult = "types";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		
        //Get only the first key
        String actualResult = data.keySet().iterator().next();
       
		Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
    }
}
