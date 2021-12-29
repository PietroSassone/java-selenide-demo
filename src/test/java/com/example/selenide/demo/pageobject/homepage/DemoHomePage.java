package com.example.selenide.demo.pageobject.homepage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.springframework.stereotype.Component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
@Component
public class DemoHomePage {

    private final SelenideElement headerImage = $("header img");

    private final SelenideElement header = $("#app > header > a");

    private final SelenideElement joinLink = $(".home-banner a");

    private final SelenideElement certificationTrainingImage = $(".home-banner img");

    private final ElementsCollection widgets = $$(".card");

    private final SelenideElement footer = $("footer");

    public void open() {
        Selenide.open("/");
    }
}
