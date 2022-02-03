package com.inyoucells.myproj;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MyprojApplication {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(MyprojApplication.class, args);
    }

}
