package com.example.demo.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;

@RestController
public class HelloRestController {
    
    List<String> names = new ArrayList<>();

    @GetMapping("/hello")
    public String sayHello() {
    return "Hello, World!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue = "Guest") String name) {
    return "Hello, " + name;
    }

    @GetMapping("/square/{num}")
    public int square(@PathVariable int num) {
    return num * num;
    }

   /* @GetMapping("/addname")
    public String addname(@RequestParam String name) {
    names.add(name);
    return "Name added succesfully: " + name;
    }*/
    @GetMapping("/getnames")
    public List<String> getnames() {
    return names;
    }
}
