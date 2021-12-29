package com.example.selenide.demo.steps;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.href;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.selenide.demo.pageobject.homepage.DemoHomePage;
import com.example.selenide.demo.util.WebTrafficRecorder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DemoHomePageStepDefinitions {

    private static final String EXPECTED_FOOTER_TEXT = "© 2013-2020 TOOLSQA.COM | ALL RIGHTS RESERVED.";
    private static final String EXPECTED_HEADER_LINK = "https://demoqa.com/";
    private static final String EXPECTED_JOIN_LINK = "https://www.toolsqa.com/selenium-training/";
    private static final String HEADER = "header";

    private final WebTrafficRecorder webTrafficRecorder = new WebTrafficRecorder();

    @Autowired
    private DemoHomePage homePage;

    @After("@HomePage")
    public void afterTest(final Scenario scenario) {
        webTrafficRecorder.saveHttpArchiveToFile(scenario);
    }

    @Given("^the demo QA homepage is opened$")
    public void theDemoQaHomePageIsOpened() {
        homePage.open();
        webTrafficRecorder.setUpSelenideProxyForHarCapture();
    }

    @Then("^the (certification training|header) image should be visible$")
    public void theImageShouldBeVisible(final String imageName) {
        if (imageName.equals(HEADER)) {
            homePage.getHeaderImage().shouldBe(visible);
        } else {
            homePage.getCertificationTrainingImage().shouldBe(visible);
        }
    }

    @Then("^the (header|join now) link should be correct$")
    public void theLinkShouldBeCorrect(final String linkName) {
        if (linkName.equals(HEADER)) {
            homePage.getHeader().shouldHave(href(EXPECTED_HEADER_LINK));
        } else {
            homePage.getJoinLink().shouldHave(href(EXPECTED_JOIN_LINK));
        }
    }

    @And("^there should be (\\d+) category widgets on the page$")
    public void thereShouldBeGivenNumberOfWidgets(final int expectedNumberOfWidgets) {
        homePage.getWidgets().shouldHave(size(expectedNumberOfWidgets));
    }

    @And("^the category widgets should be the following in order:$")
    public void theWidgetsShouldBe(final DataTable dataTable) {
        homePage.getWidgets().shouldHave(texts(dataTable.asList()));
    }

    @And("^the footer should be visible and correct$")
    public void theFooterShouldBeVisibleAndCorrect() {
        homePage.getFooter().shouldBe(visible).shouldHave(text(EXPECTED_FOOTER_TEXT));
    }
}