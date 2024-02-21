package com.lppduy.bank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "LppD Bank App",
                description = "Backend APIs for bank",
                version = "v1.0",
                contact = @Contact(
                        name = "lppduy", email = "lppduy@gmail.com",
                         url = "https://github.com/lppduy/bank-app"
                ),
                license = @License(
                        name = "lppduy",
                        url = "https://github.com/lppduy/bank-app"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "LppD Bank App Documentation",
                url = "https://github.com/lppduy/bank-app"
        )
)
public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

}

// http://localhost:9192/swagger-ui/index.html#/