package com.example.selenide.demo.hooks;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.selenide.demo.util.browserplatform.JsonDeserializerForPlatform;
import com.example.selenide.demo.util.browserplatform.Platform;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.qameta.allure.selenide.AllureSelenide;

public class Hooks {

    private static final Set<String> SUPPORTED_DEVICE_PLATFORMS = Set.of("iPhoneX", "Nexus7");
    private static final String EDGEOPTIONS_ARGS = "edgeoptions.args";
    private static final String DESKTOP_BROWSER_SIZE = "1920x1080";
    private static final String DESKTOP_USER_AGENT = "desktop";

    @Autowired
    private JsonDeserializerForPlatform deserializerForPlatform;

    @Value("${platformToSet}")
    private String mobilePlatformToSet;

    @BeforeAll
    public static void setupTestCommonConfig() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.proxyEnabled = true;
        Configuration.pageLoadTimeout = 40000L;

        SelenideLogger.addListener(
            "AllureSelenide",
            new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
        );
    }

    @Before
    public void beforeScenario(final Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@mobile")) {
            if (SUPPORTED_DEVICE_PLATFORMS.contains(mobilePlatformToSet) && WebDriverRunner.isEdge()) {
                final Platform platform = deserializerForPlatform.readJsonFileToPlatform(mobilePlatformToSet);

                Configuration.browserSize = platform.getBrowserWindowSize();
                System.setProperty(EDGEOPTIONS_ARGS, getFormattedUserAgent(platform.getUserAgent()));
            }
        } else {
            Configuration.browserSize = DESKTOP_BROWSER_SIZE;
            System.setProperty(EDGEOPTIONS_ARGS, getFormattedUserAgent(DESKTOP_USER_AGENT));
        }
    }

    private String getFormattedUserAgent(final String userAgentToSet) {
        return "--user-agent=\"" + userAgentToSet + "\"";
    }
}
