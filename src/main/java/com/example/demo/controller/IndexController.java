package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping(value = {"", "index"})
    public String getIndex(@RequestParam(required = false, defaultValue = "undefined user") String name) {
        return "Hello, " + name;
    }

}
