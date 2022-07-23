package com.microservices.rest.webservice.restfulwebservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserSocialMediaNotFoundException extends RuntimeException {
    public UserSocialMediaNotFoundException(String s) {
        super(s);
    }
}
