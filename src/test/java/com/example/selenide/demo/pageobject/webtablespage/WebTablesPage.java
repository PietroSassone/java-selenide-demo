package com.example.selenide.demo.pageobject.webtablespage;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
@Component
public class WebTablesPage {

    private static final String WEB_TABLES_PAGE_PATH = "/webtables";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String AGE = "age";
    private static final String SALARY = "salary";
    private static final String DEPARTMENT = "department";

    private final SelenideElement addNewRecordButton = $("#addNewRecordButton");

    private final SelenideElement firstNameInput = $("#firstName");

    private final SelenideElement lastNameInput = $("#lastName");

    private final SelenideElement emailInput = $("#userEmail");

    private final SelenideElement ageInput = $("#age");

    private final SelenideElement salaryInput = $("#salary");

    private final SelenideElement departmentInput = $("#department");

    private final SelenideElement submitButton = $("#submit");

    private final ElementsCollection filledTableRows = $$(byXpath("//div[@class='rt-tr-group']/div[div[1][text()]]"));

    private final ElementsCollection deleteButtons = $$(byXpath("//div[@class='action-buttons']/span[2]"));

    private final SelenideElement paginationBar = $(".pagination-bottom");

    private final SelenideElement noDataTextElement = $(".rt-noData");

    private final Map<String, SelenideElement> inputFieldsMap = Map.of(
        FIRST_NAME, firstNameInput,
        LAST_NAME, lastNameInput,
        EMAIL, emailInput,
        AGE, ageInput,
        SALARY, salaryInput,
        DEPARTMENT, departmentInput
    );

    public void open() {
        Selenide.open(WEB_TABLES_PAGE_PATH);
    }

    public SelenideElement getInputFieldByName(final String fieldName) {
        return inputFieldsMap.get(fieldName);
    }

    private final Pagination pagination = page(Pagination.class);

    public SelenideElement getPaginationResultsPerPageDropdown() {
        return pagination.resultsPerPageDropdown;
    }

    public SelenideElement getPaginationNumberOfTotalPages() {
        return pagination.totalNumberOfTablePages;
    }

    public SelenideElement getPaginationPageJumpField() {
        return pagination.pageJumpField;
    }

    public SelenideElement getPaginationPreviousButton() {
        return pagination.previousPageButton;
    }

    public SelenideElement getPaginationNextButton() {
        return pagination.nextPageButton;
    }

    static class Pagination {
        private final SelenideElement paginationRootElement = $(".pagination-bottom");

        private final SelenideElement resultsPerPageDropdown = paginationRootElement.$(".-pageSizeOptions select");

        private final SelenideElement totalNumberOfTablePages = paginationRootElement.$(".-totalPages");

        private final SelenideElement pageJumpField = paginationRootElement.$(".-pageJump input");

        private final SelenideElement previousPageButton = paginationRootElement.$(".-previous button");

        private final SelenideElement nextPageButton = paginationRootElement.$(".-next button");
    }
}
