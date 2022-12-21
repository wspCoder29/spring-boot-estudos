package io.github.leandro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication {


    @GetMapping("/hello")
    public String hello(){
        return "HelloWorld!";
    }

    public static void main(String[] args) {
        //run para rodar a applicação rececebe a classe e os args
        SpringApplication.run(VendasApplication.class, args);



    }
}
