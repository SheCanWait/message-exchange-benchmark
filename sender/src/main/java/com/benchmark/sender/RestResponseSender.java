package com.benchmark.sender;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestResponseSender {

    @GetMapping("/exampleAddress")
    public String exampleAddress(@RequestParam(value = "name", defaultValue = "-1") String name) {
        return "response for request " + name;
    }

}
