package com.example.selenide.demo;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.example.selenide.demo.config.UITestSpringConfig;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.example.selenide.demo",
    plugin = "pretty"
)
@CucumberContextConfiguration
@ContextConfiguration(classes = UITestSpringConfig.class)
public class RunCucumberTestsIT {

}
