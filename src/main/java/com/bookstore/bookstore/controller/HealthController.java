package com.bookstore.bookstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shivani_reddy
 */

@RestController
public class HealthController {

    @RequestMapping("/ping")
    public String healthCheck() {
        return "Ok";
    }
}
