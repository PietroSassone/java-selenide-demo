package com.example.selenide.demo.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.WebDriverRunner;

public class BrowserStackTestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserStackTestConfig.class);

    private static final String BROWSERSTACK_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String BROWSERSTACK_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String BROWSERSTACK_URL = String.format("https://%s:%s@hub-cloud.browserstack.com/wd/hub", BROWSERSTACK_USERNAME, BROWSERSTACK_ACCESS_KEY);

    private static final Set<String> MOBILE_PLATFORMS = Set.of("Android", "IOS");
    private static final String BROWSER_STACK_BROWSER_NAME = "browserStackBrowserName";
    private static final String BROWSER_STACK_BROWSER_VERSION = "browserStackBrowserVersion";
    private static final String BROWSER_STACK_OS = "browserStackOS";
    private static final String BROWSER_STACK_OS_VERSION = "browserStackOSVersion";
    private static final String BROWSER_STACK_PLATFORM = "browserStackPlatform";
    private static final String BROWSER_STACK_DEVICE = "browserStackDevice";
    private static final String CHROME = "Chrome";
    private static final String LATEST = "latest";
    private static final String WINDOWS = "Windows";
    private static final String ELEVEN = "11";
    private static final String DESKTOP = "desktop";
    private static final String GOOGLE_PIXEL_6 = "Google Pixel 6";

    private RemoteWebDriver driver;

    public void createDriverForBrowserStack() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability("os_version", getOsVersion());

        if (MOBILE_PLATFORMS.contains(getBrowserStackPlatform())) {
            setUpCapabilitiesForDeviceBrowserStack(desiredCapabilities);
        } else {
            setUpCapabilitiesForDesktopBrowserStack(desiredCapabilities);
        }

        desiredCapabilities.setCapability("browserstack.debug", "true");
        desiredCapabilities.setCapability("browserstack.console", "info");

        // Needed for automatic HTTP archive capturing
        desiredCapabilities.setCapability("acceptSslCerts", "true");
        desiredCapabilities.setCapability("browserstack.networkLogs", "true");

        // Needed for the test run to appear on the Browserstack dashboard
        desiredCapabilities.setCapability("build", "BrowserStack-Selenide-build-1");

        LOGGER.info("Provided capabilities: {}", desiredCapabilities);
        try {
            driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), desiredCapabilities);
            WebDriverRunner.setWebDriver(driver);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error setting up Browserstack URL.");
        }
        LOGGER.info("Starting tests with the custom driver setup for BrowserStack. Capabilities: {}", desiredCapabilities);
    }

    public void shutDownDriver() {
        LOGGER.info("Shutting down remote driver after BrowserStack usage.");
        driver.quit();
    }

    private void setUpCapabilitiesForDesktopBrowserStack(final DesiredCapabilities desiredCapabilities) {
        desiredCapabilities.setCapability("browser", getBrowserName());
        desiredCapabilities.setCapability("browser_version", getBrowserVersion());
        desiredCapabilities.setCapability("os", getOs());
    }

    private void setUpCapabilitiesForDeviceBrowserStack(final DesiredCapabilities desiredCapabilities) {
        desiredCapabilities.setCapability("device", getDeviceName());
        desiredCapabilities.setCapability("realMobile", "true");
        desiredCapabilities.setCapability("browserName", getBrowserStackPlatform());
    }

    private String getBrowserName() {
        return System.getProperty(BROWSER_STACK_BROWSER_NAME, CHROME);
    }

    private String getBrowserVersion() {
        return System.getProperty(BROWSER_STACK_BROWSER_VERSION, LATEST);
    }

    private String getOs() {
        return System.getProperty(BROWSER_STACK_OS, WINDOWS);
    }

    private String getOsVersion() {
        return System.getProperty(BROWSER_STACK_OS_VERSION, ELEVEN);
    }

    private String getBrowserStackPlatform() {
        return System.getProperty(BROWSER_STACK_PLATFORM, DESKTOP);
    }

    private String getDeviceName() {
        return System.getProperty(BROWSER_STACK_DEVICE, GOOGLE_PIXEL_6);
    }
}
