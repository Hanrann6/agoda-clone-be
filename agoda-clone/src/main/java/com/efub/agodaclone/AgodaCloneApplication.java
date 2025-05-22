package com.efub.agodaclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AgodaCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgodaCloneApplication.class, args);
    }

}
