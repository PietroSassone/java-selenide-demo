# Demo project to demonstrate test automation framework development for UI testing with Selenide and Java

**Test automation UI framework demo project.**

UI test automation in Java 11, demonstrating how to implement a scalable, flexible framework for running UI tests in parallel.
Supporting different browsers.
Plus saving useful visual test reports and web HTTP traffic records.

*Note:* The test cases implemented are not extensive. Just a selection of all possible scenarios.
For a small demo.

This is project is to demonstrate differences between framework implementation using plain Selenium vs Selenide.
I have a plain Selenium [project](https://github.com/PietroSassone/selenium-ta-demo) in Java. 
This one contains similar features plus the same tests.
Using Selenide.

**1. Technologies used**
- Selenide 6 for UI tests
- Cucumber 7 for Behaviour Specification and Data Driven Testing
- Junit 5 with setting the tests to retry failed ones
- Maven Failsafe plugin to run the test suite with parallelization
- Logback for logging
- Spring Core for dependency injection
- Lombok to eliminate a lot of code
- Java Faker for test data generation
- Maven Checkstyle for enforcing coding conventions
- Allure plugin for visualization of test reports
- BrowserUp with Selenide proxy plugin for capturing web traffic
- Jackson for deserializing JSON configuration into a Java class

**2. Design patterns used:**
- Behaviour Specification
- Builder
- Page Object Model
- Composition of Page Elements, changed for Selenide page objects

**3. Some aspects of UI testing being demonstrated:**
- Using Selenium Data tables
- Checking for the visibility of web elements
- Checking the correctness of links
- Checking the enabled/disabled state of web elements
- Adding data to a web table
- Deleting data from a web table
- Interacting with pagination
- Taking and saving screenshots
- Capturing HTTP web traffic
- Emulating mobile browsers for testing

**4. Reporting and logging**
- The framework saves reports and logs in the target folder after a test run finishes.
1. Logs are saved in target/logs
1. Detailed Allure reports are generated as well. Saved in target/allure-results.
1. HTTP Archive is also saved in target/webtraffic for each scenario.
   
The reports create a visualized overview of the test results. Can be viewed in a browser.
In case of failed scenarios a screenshot of the browser is saved.
The screenshot is added to the Allure test reporting.

Command to start the Allure reports after a finished test run:  
    ```
    allure:serve
    ```  
The report opens in a new browser tab by itself.

**5. Pre-requirements for running the tests**  
- Have Maven installed.
- Have Java installed, at lest version 11.
- Have the latest version of the browser installed that you want to run the tests with.

**6. Launching the tests**  
Open a terminal and type:  
    ```
    mvn clean verify
    ```
Supported arguments:  
All configuration options supported by [Selenide](https://selenide.org/javadoc/current/com/codeborne/selenide/Configuration.html).  
Plus:
| argument name                 | supported values             | default value      | description                                                |
| ----------------------------- | ---------------------------- | ------------------ | ---------------------------------------------------------- |
| rerun.tests.count             | any positive integer         | 1                  | sets how many times to try rerunning each failed test case |
| platformToSet (only for Edge) | iPhoneX, nexus7              | none, runs desktop | sets the platform/device to be emulated by the webDriver   |

The framework supports the browsers supported by Selenide for testing.
Default: Firefox

*Notes about the mobile device emulation:*  
- Only on Chrome: tests can be run with the mobile emulation with Selenide's build in mobile emulation. Both in headless and standard mode.
- For Edge, you can use platformToSet as described in the table above. It was implemented by me. Both in headless and standard mode.
- Currently, the framework only runs tests in other browsers, like Firefox on Desktop platform.
- At the moment, desktop, iPhone X and Nexus 7 tablet views are added to the framework for demo purposes.
- New platforms for Edge tests can be added by creating a JSON config file in the "test/resources/browserplatform" directory.
- The format of a new config added must conform to the same format as the 2 JSON files already present.
- Settings for devices can be copied from: [Chromium devtools devices](https://chromium.googlesource.com/chromium/src/+/167a7f5e03f8b9bd297d2663ec35affa0edd5076/third_party/WebKit/Source/devtools/front_end/emulated_devices/module.json)
- After adding a config for a new device, the tests can be immediately run with this platform.
- When supplying the '-DplatformToSet' param, the value must be the exact same as the JSON file's name.

Setting which tests should be run based on cucumber tags can be done via the ```-Dcucumber.filter.tags``` option.  

Example command to run the tests with default browser settings (chrome) or only the Web Table page:  
    ```
    mvn clean verify -Dcucumber.filter.tags=@WebTablesPage
    ```

Example command to run the tests with MS Edge Driver in headless mode:  
    ```
    mvn clean verify -Dselenide.browser=edge -Dselenide.headless=true
    ```

Example command to run the tests with MS Edge Driver while emulating the Nexus 7 tablet browser:  
    ```
    mvn clean verify -Dselenide.browser=edge -DplatformToSet=nexus7
    ```