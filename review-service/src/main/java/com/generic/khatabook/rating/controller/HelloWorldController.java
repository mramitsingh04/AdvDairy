package com.generic.khatabook.rating.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Welcome to Rating Service Application";
    }


    @GetMapping(path = "/hello/to/{to}")
    public String helloWorldToPerson(final @PathVariable String to) {
        return "Welcome %s to Rating Service Application".formatted(to);
    }


}
