package com.example.taskmanagement.ui;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.text;

/**
 * Sample UI test demonstrating how to use Selenide and the Page
 * Object pattern to verify the behaviour of a simple HTML page.
 * The test opens a local HTML file, fills out a form to add a
 * task and asserts that the task appears in the list.  Running in
 * headless mode ensures compatibility with CI environments such as
 * GitHub Actions.
 */
public class TaskUITest {

    @BeforeAll
    static void setup() {
        // Setup WebDriver binaries automatically
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.browserSize = "1280x720";
    }

    @AfterAll
    static void tearDown() {
        closeWebDriver();
    }

    @Test
    void shouldAddTaskToList() {
        String fileUri = Paths.get("src/test/resources/tasks.html").toAbsolutePath().toUri().toString();
        open(fileUri);
        // fill out the form
        $("#task-title").setValue("Test Task");
        $("#task-desc").setValue("Test description");
        $("#add-task").click();
        // verify that the task appears in the list
        $("#task-list").shouldHave(text("Test Task - Test description"));
    }
}