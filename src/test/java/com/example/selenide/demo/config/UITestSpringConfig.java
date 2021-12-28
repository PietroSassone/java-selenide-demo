package com.example.selenide.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.selenide.demo.util.browserplatform.JsonDeserializerForPlatform;
import com.example.selenide.demo.util.browserplatform.Platform;
import com.github.javafaker.Faker;

@Configuration
@ComponentScan("com.example.selenide.demo")
public class UITestSpringConfig {

    @Bean
    public Faker testDataGenerator() {
        return new Faker();
    }

    @Bean
    public JsonDeserializerForPlatform deserializerForPlatform() {
        return new JsonDeserializerForPlatform(Platform.class);
    }
}
