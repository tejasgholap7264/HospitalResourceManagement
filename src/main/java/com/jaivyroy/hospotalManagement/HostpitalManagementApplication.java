package com.jaivyroy.hospotalManagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class HostpitalManagementApplication {

	public static void main(String[] args) {

        SpringApplication.run(HostpitalManagementApplication.class, args);
        System.out.println("it's my world ");
        log.info("TEST LOG: Application Started");
        System.out.println("TEST PRINT: App started");
	}

}