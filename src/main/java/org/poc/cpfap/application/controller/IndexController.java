package org.poc.cpfap.application.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String sayHello() {
        return "######Welcome to the DigiBank Transaction application######:version-4.";
    }
}
