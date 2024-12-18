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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.example.project1.Entity.User;
import com.example.project1.Service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTests {
	@Autowired
	private UserService userService;

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
	 * Test user register
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Order(1)
	public void registerUser() throws IOException, InterruptedException
	{
		String json = "{\"username\":\"test\",\"password\": \"test\"}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/register"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		Map<String, Object> expectedResult = new LinkedHashMap<>();
		expectedResult.put("username", "test");
        expectedResult.put("role", 0);

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> actualResult = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		actualResult.remove("userID");
        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
	}

	/**
	 * Test user login
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Order(2)
	public void loginUser() throws IOException, InterruptedException
	{
		String json = "{\"username\":\"test\",\"password\": \"test\"}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/login"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		Map<String, Object> expectedResult = new LinkedHashMap<>();
		expectedResult.put("username", "test");
        expectedResult.put("role", 0);

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> actualResult = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});

		actualResult.remove("userID");
        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
	}

	/**
	 * Test update user account information
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Order(3)
	public void updateUserAccountInformation() throws IOException, InterruptedException
	{
		String json = "{\"username\":\"test\", \"email\": \"\", \"firstname\":\"testfirstname\", \"lastname\": \"\", \"address\": \"\"}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/account"))
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
	 * Test get user account information
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Order(4)
	public void getUserAccountInformation() throws IOException, InterruptedException
	{
		String json = "{\"username\":\"test\"}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/account"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		String expectedResult = "testfirstname";

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> mResult = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		String actualResult = mResult.get("firstname").toString();

        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
	}

	/**
	 * Test update user password
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Order(5)
	public void updateUserPassword() throws IOException, InterruptedException
	{
		String json = "{\"username\":\"test\", \"current\":\"test\", \"new\":\"testpassword\"}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/password"))
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
	 * Test update user role
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	@Order(6)
	public void updateUserRole() throws IOException, InterruptedException
	{
		//Login to grab the userID
		String jsonlogin = "{\"username\":\"test\",\"password\": \"testpassword\"}";
        HttpRequest postMessageRequestlogin = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/login"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(jsonlogin))
                .build();
		
		HttpResponse<String> responselogin = webClient.send(postMessageRequestlogin, HttpResponse.BodyHandlers.ofString());

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> loginResult = om.readValue(responselogin.body().toString(), new TypeReference<Map<String, Object>>() {});

		//save the result
		Integer userID = Integer.parseInt(loginResult.get("userID").toString());


		//Test the role endpoint
		String json = "{\"id\": " + userID + ", \"role\": 1}";
		System.out.println(json);
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/role"))
				.header("Content-Type", "application/json; charset=UTF-8")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .build();
		
		HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

		Map<String, Object> expectedResult = new LinkedHashMap<>();
		expectedResult.put("success", true);

        Map<String, Object> actualResult = om.readValue(response.body().toString(), new TypeReference<Map<String, Object>>() {});
		actualResult.put("success", Boolean.parseBoolean(actualResult.get("success").toString()));
       
		Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
	}

	/**
	 * For cleaning the test user
	 */
	@Order(12)
	@Test
	public void removeUser()
	{
		User user = userService.getUser("test");
		if(user != null)
		{
			//Remove the testing user if exist
			userService.deleteUser("test");
		}
	}
}
