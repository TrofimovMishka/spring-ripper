package com.example.demo;

import com.example.demo.bob.Quoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HandlerApplication implements CommandLineRunner {

    @Autowired
    private Quoter quoter;

    public static void main(String[] args) {
        SpringApplication.run(HandlerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        while (true) {
//            Thread.sleep(200);
//            quoter.sayQuote();
//        }
    }
}
