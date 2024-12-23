package org.hidevelop.coffeecats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CoffeeCatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeCatsApplication.class, args);
    }

}
