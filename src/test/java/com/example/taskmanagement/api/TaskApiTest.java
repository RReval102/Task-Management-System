package com.example.taskmanagement.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

/**
 * Example API test using Rest‑Assured and Allure integration.  The
 * test starts the embedded Javalin server defined in
 * {@link TaskApiServer}, sends a POST request to create a new
 * task, then verifies via a GET request that the task exists in
 * the collection.  All HTTP calls are wrapped with
 * {@link AllureRestAssured} to capture requests and responses for
 * reporting.
 */
public class TaskApiTest {
    private static TaskApiServer server;

    @BeforeAll
    static void startServer() {
        // Start the API server on a fixed port
        server = new TaskApiServer(7001);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7001;
    }

    @AfterAll
    static void stopServer() {
        if (server != null) {
            server.stop();
        }
    }

    @Test
    void shouldCreateAndGetTask() {
        String json = "{\"title\": \"API Task\", \"description\": \"API desc\", \"status\": \"NEW\"}";

        // Create a new task
        given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .body(json)
        .when()
                .post("/tasks")
        .then()
                .statusCode(200)
                .body("title", equalTo("API Task"))
                .body("description", equalTo("API desc"));

        // Verify the task appears in the list
        given()
                .filter(new AllureRestAssured())
        .when()
                .get("/tasks")
        .then()
                .statusCode(200)
                .body("title", hasItem("API Task"));
    }
}