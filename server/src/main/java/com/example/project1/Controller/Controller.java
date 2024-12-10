package com.example.project1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller
{

    @GetMapping("/")
    @ResponseBody
	public String index()
    {
		return "Greetings from Spring Boot!";
	}
}
