package com.example.selenide.demo.util;

import static com.browserup.bup.proxy.CaptureType.REQUEST_CONTENT;
import static com.browserup.bup.proxy.CaptureType.REQUEST_HEADERS;
import static com.browserup.bup.proxy.CaptureType.RESPONSE_HEADERS;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.proxy.CaptureType;
import com.browserup.harreader.model.Har;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.Scenario;

/**
 * Utility class to capture web traffic and save them to HTTP archive files.
 */
public class WebTrafficRecorder {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebTrafficRecorder.class);
    private static final Path CAPTURED_TRAFFIC_FOLDER = Paths.get("target/webtraffic");
    private static final String WHITESPACE_REGEX = "\\s";
    private static final String UNDERSCORE = "_";
    private static final String TARGET_HTTP_ARCHIVE_FILE_NAME_TEMPLATE = "http_archive_demo_%s%s.har";

    private BrowserUpProxy selenideProxy;

    public void setUpSelenideProxyForHarCapture() {
        try {
            selenideProxy = WebDriverRunner.getSelenideProxy().getProxy();

            selenideProxy.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());

            selenideProxy.enableHarCaptureTypes(REQUEST_HEADERS, REQUEST_CONTENT, RESPONSE_HEADERS);

            selenideProxy.newHar("demo_traffic_capture");
        } catch (NullPointerException e) {
            LOGGER.warn("Selenide proxy was not set properly.");
        }
    }

    public void saveHttpArchiveToFile(final Scenario scenario) {
        try {
            final Har capturedHttpArchive = selenideProxy.getHar();

            final String targetHttpArchiveFileName = String.format(
                TARGET_HTTP_ARCHIVE_FILE_NAME_TEMPLATE,
                scenario.getName(),
                scenario.getLine()
            )
                .replaceAll(WHITESPACE_REGEX, UNDERSCORE);
            Files.createDirectories(CAPTURED_TRAFFIC_FOLDER);
            capturedHttpArchive.writeTo(CAPTURED_TRAFFIC_FOLDER.resolve(Paths.get(targetHttpArchiveFileName)).toFile());

            LOGGER.info("Saving HTTP Archive was successful: {}", targetHttpArchiveFileName);
        } catch (Exception e) {
            LOGGER.warn("Can't save HTTP archive file. Exception: {}", e.getMessage());
        }
    }
}
