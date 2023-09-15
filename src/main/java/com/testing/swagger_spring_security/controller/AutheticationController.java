package com.testing.swagger_spring_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testing.swagger_spring_security.dto.UserDTO;
import com.testing.swagger_spring_security.service.JwtService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/token")
@Tag(name = "generate-token")
public class AutheticationController {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@ApiOperation(value = "getToken", tags = "generate-token", response = Iterable.class)
	@RequestMapping(method = RequestMethod.POST, value = "/generate")
    public String authenticateAndGetToken(@RequestBody UserDTO userDto) throws BadCredentialsException {
    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userDto.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }

}
