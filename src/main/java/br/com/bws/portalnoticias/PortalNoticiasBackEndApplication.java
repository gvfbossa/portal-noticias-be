package br.com.bws.portalnoticias;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"br.com.bossawebsolutions.base_api", "br.com.bws.portalnoticias"})
public class PortalNoticiasBackEndApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env.dev")
                .load();

        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(PortalNoticiasBackEndApplication.class, args);
    }

}
