package com.example.selenide.demo.util;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;

@Component
public class ScreenshotUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreenshotUtil.class);

    @Attachment(type = "image/png")
    public byte[] addScreenshotToReport() {
        final File screenshot = Screenshots.getLastScreenshot();
        byte[] screenshotByte = null;

        try {
            if (Objects.nonNull(screenshot)) {
                screenshotByte = Files.toByteArray(screenshot);
            }
        } catch (IOException e) {
            LOGGER.warn("Error occurred while adding screenshot to Allure report: {}", e.getMessage());
        }

        return screenshotByte;
    }
}
