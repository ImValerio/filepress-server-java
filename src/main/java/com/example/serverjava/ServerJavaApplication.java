package com.example.serverjava;

import com.nixxcode.jvmbrotli.common.BrotliLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ServerJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerJavaApplication.class, args);
        BrotliLoader.isBrotliAvailable();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/compress").allowedOrigins("http://localhost:8080");
            }
        };
    }
}
