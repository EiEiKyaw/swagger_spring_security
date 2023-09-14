package com.testing.swagger_spring_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/")
@Tag(name = "home")
public class HomeController {
	
	@ApiOperation(value = "getHome", tags = "home")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Welcome!")
            }
    )
	@RequestMapping(method = RequestMethod.GET)
	public String getHome() {
		return "This is the Home Page";
	}

}
