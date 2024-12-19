package com.example.project1;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
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
public class TicketTests {
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
     * Test add new ticket
     * @throws IOException
     * @throws InterruptedException
     */
    @Order(18)
    @Test
    public void addNewTicket() throws IOException, InterruptedException
	{
		String json = "{\"description\":\"testdesc\", \"amount\": 123, \"typeid\": 1, \"submitterid\": 1}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/new"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		Map<String, Object> expectedResult = new LinkedHashMap<>();
		expectedResult.put("success", true);

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> actualResult = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		actualResult.put("success", Boolean.parseBoolean(actualResult.get("success").toString()));
       
		Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
	}

    /**
     * Add new ticket with invalid or missing param
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
	@Order(19)
	public void addNewTicket_param() throws IOException, InterruptedException
	{
		String json = "{\"amount\": 123, \"typeid\": 1, \"submitterid\": 1}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/new"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		String expectedResult = "error";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		String actualResult = data.keySet().iterator().next();

        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
	}

    /**
     * Add new ticket with amount 0 check
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
	@Order(20)
	public void addNewTicket_amount() throws IOException, InterruptedException
	{
		String json = "{\"description\":\"testdesc\", \"amount\": 0, \"typeid\": 1, \"submitterid\": 1}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/new"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		String expectedResult = "error";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		String actualResult = data.keySet().iterator().next();

        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
	}

    /**
     * Add new ticket with invalid type
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
	@Order(21)
	public void addNewTicket_type() throws IOException, InterruptedException
	{
		String json = "{\"description\":\"testdesc\", \"amount\": 123, \"typeid\": 9999, \"submitterid\": 1}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/new"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		String expectedResult = "error";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		String actualResult = data.keySet().iterator().next();

        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
	}

    /**
     * Get specific user tickets
     * @throws IOException
     * @throws InterruptedException
     */
    @Order(22)
    @Test
    public void getUserTickets() throws IOException, InterruptedException
    {
        String json = "{\"userid\": 1}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/user"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		String expectedResult = "tickets";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		
        String actualResult = data.keySet().iterator().next();
       
		Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
    }

    /**
     * Get user tickets with invalid param
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
	@Order(23)
	public void getUserTickets_param() throws IOException, InterruptedException
	{
		String json = "{}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/user"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		String expectedResult = "error";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		String actualResult = data.keySet().iterator().next();

        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
	}

    /**
     * Test get all tickets
     * @throws IOException
     * @throws InterruptedException
     */
    @Order(24)
    @Test
    public void getAllTickets() throws IOException, InterruptedException
    {
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/all"))
				.GET()
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		String expectedResult = "tickets";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		
        //Get only the first key
        String actualResult = data.keySet().iterator().next();
       
		Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
    }

    /**
     * Update a ticket status
     * @throws IOException
     * @throws InterruptedException
     */
    @Order(25)
    @Test
    public void updateTicketStatus() throws IOException, InterruptedException
    {
        String json = "{\"ticketid\": 1, \"status\": 2, \"typeid\": 1, \"userid\": 1}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/status"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		Map<String, Object> expectedResult = new LinkedHashMap<>();
		expectedResult.put("success", true);

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> actualResult = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		actualResult.put("success", Boolean.parseBoolean(actualResult.get("success").toString()));
       
		Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
    }

    /**
     * Update a ticket status with missing param
     * @throws IOException
     * @throws InterruptedException
     */
    @Order(26)
    @Test
    public void updateTicketStatus_param() throws IOException, InterruptedException
    {
        String json = "{\"ticketid\": 1, \"typeid\": 1, \"userid\": 1}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/status"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
        HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

        String expectedResult = "error";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
        String actualResult = data.keySet().iterator().next();

        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
    }

    /**
     * Update a ticket status with invalid ticket ID
     * @throws IOException
     * @throws InterruptedException
     */
    @Order(27)
    @Test
    public void updateTicketStatus_ticket() throws IOException, InterruptedException
    {
        String json = "{\"ticketid\": 9999999, \"status\": 2, \"typeid\": 1, \"userid\": 1}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/status"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
        HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

        String expectedResult = "error";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
        String actualResult = data.keySet().iterator().next();

        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
    }

    /**
     * Update a ticket status with invalid user ID
     * @throws IOException
     * @throws InterruptedException
     */
    @Order(28)
    @Test
    public void updateTicketStatus_user() throws IOException, InterruptedException
    {
        String json = "{\"ticketid\": 1, \"status\": 2, \"typeid\": 1, \"userid\": 9999999}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/ticket/status"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
        HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

        String expectedResult = "error";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
        String actualResult = data.keySet().iterator().next();

        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
    }
}
