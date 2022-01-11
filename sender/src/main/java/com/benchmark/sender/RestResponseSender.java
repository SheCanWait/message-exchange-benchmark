package com.benchmark.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RestResponseSender {

    private static String responseMessage;

    static {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 39000; i++) {
            str.append("AAAAAaaaaaAAAAAaaaaaAAA25");
        }
        responseMessage = str.toString();
    }

    @GetMapping("/exampleAddress")
    public String exampleAddress(@RequestParam(value = "name", defaultValue = "-1") String name) {
        log.info("Response for request nr " + name + " sent");
        return responseMessage + "response for request " + name;
    }

}
