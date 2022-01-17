package com.example.selenide.demo.hooks;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.selenide.demo.config.BrowserStackTestConfig;
import com.example.selenide.demo.util.ScreenshotUtil;
import com.example.selenide.demo.util.browserplatform.JsonDeserializerForPlatform;
import com.example.selenide.demo.util.browserplatform.Platform;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.qameta.allure.selenide.AllureSelenide;

public class Hooks {

    private static final Set<String> SUPPORTED_DEVICE_PLATFORMS = Set.of("iPhoneX", "nexus7");
    private static final String EDGEOPTIONS_ARGS = "edgeoptions.args";
    private static final String DESKTOP_BROWSER_SIZE = "1920x1080";
    private static final String BROWSER_WINDOW_SIZE = "browserWindowSize";
    private static final String PAGE_LOAD_TIMEOUT = "pageLoadTimeOut";
    private static final String DEFAULT_PAGE_LOAD_TIMEOUT = "20000";
    private static final String BROWSERSTACK_ENABLED = "browserStackEnabled";
    private static final String PROXY_ENABLED = "proxyEnabled";
    private static final String FALSE = "false";
    private static final String TRUE = "true";

    @Autowired
    private JsonDeserializerForPlatform deserializerForPlatform;

    @Autowired
    private ScreenshotUtil screenshotUtil;

    @Value("${platformToSet}")
    private String mobilePlatformToSet;

    private BrowserStackTestConfig browserStackTestConfig;

    @BeforeAll
    public static void setupTestCommonConfig() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadTimeout = getPageLoadTimeout();
        Configuration.proxyEnabled = getIsProxyEnabled();
        Configuration.browserSize = getWindowSize();

        SelenideLogger.addListener(
            "AllureSelenide",
            new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
        );
    }

    @Before
    public void browserStackTest() {
        if (getIsBrowserStackEnabled()) {
            browserStackTestConfig = new BrowserStackTestConfig();
            browserStackTestConfig.createDriverForBrowserStack();
        }
    }

    @Before
    public void beforeMobileEdgeScenario() {
        if (SUPPORTED_DEVICE_PLATFORMS.contains(mobilePlatformToSet) && WebDriverRunner.isEdge()) {
            final Platform platform = deserializerForPlatform.readJsonFileToPlatform(mobilePlatformToSet);

            Configuration.browserSize = platform.getBrowserWindowSize();
            System.setProperty(EDGEOPTIONS_ARGS, getFormattedUserAgent(platform.getUserAgent()));
        }
    }

    @After
    public void tearDown(final Scenario scenario) {
        if (scenario.isFailed()) {
            screenshotUtil.addScreenshotToReport();
        }

        if (getIsBrowserStackEnabled()) {
            browserStackTestConfig.shutDownDriver();
        }
    }

    private static String getWindowSize() {
        return System.getProperty(BROWSER_WINDOW_SIZE, DESKTOP_BROWSER_SIZE);
    }

    private static Boolean getIsBrowserStackEnabled() {
        return Boolean.valueOf(System.getProperty(BROWSERSTACK_ENABLED, FALSE));
    }

    private static Boolean getIsProxyEnabled() {
        return Boolean.valueOf(System.getProperty(PROXY_ENABLED, FALSE));
    }

    private static Long getPageLoadTimeout() {
        return Long.valueOf(System.getProperty(PAGE_LOAD_TIMEOUT, DEFAULT_PAGE_LOAD_TIMEOUT));
    }

    private String getFormattedUserAgent(final String userAgentToSet) {
        return "--user-agent=\"" + userAgentToSet + "\"";
    }
}
