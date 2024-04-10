package com.ecommerceCatalog.CommerceCatalog.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class TstController {


    @GetMapping
    private String helloWorld(){
        return "Hello world";
    }
}
