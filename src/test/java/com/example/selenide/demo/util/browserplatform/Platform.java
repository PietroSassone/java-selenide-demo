package com.example.selenide.demo.util.browserplatform;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@JsonDeserialize(using = JsonDeserializerForPlatform.class)
public class Platform {

    private String platformName;
    private Screen screenSettings;
    private String userAgent;

    public String getBrowserWindowSize() {
        return new StringBuilder().append(screenSettings.getScreenWidth())
            .append("x")
            .append(screenSettings.getScreenHeight()).toString();
    }
}
