# Task Management System Test Framework

This repository contains a demonstration project for a **Task Management System**.  It is designed to showcase a multi‑layer testing approach covering the UI, API and database tiers using modern Java tools.

## Project goals

The project implements a lightweight test framework that can be used as a starting point for testing a single‑page application (SPA).  In keeping with the curriculum described in the accompanying résumé, the framework illustrates the following:

* **UI testing** with [Selenide](https://selenide.org/) using the Page Object pattern.  A simple HTML page (`tasks.html`) is stored in `src/test/resources` and exercised by `TaskUITest`.
* **API testing** using [Rest‑Assured](https://rest-assured.io/) to call a small REST API built with [Javalin](https://javalin.io/).  The API exposes endpoints to create and retrieve tasks, and is started inside the test suite.
* **Database testing** via [Hibernate ORM](https://hibernate.org/) and an in‑memory H2 database.  The `TaskDAO` class encapsulates CRUD operations, while `TaskDbTest` verifies persistence behaviour.
* **Continuous integration** configured via GitHub Actions.  The workflow defined in `.github/workflows/ci.yml` installs Java, builds the project, runs the tests and generates an Allure report, which is published as an artifact for easy download.

## Structure

```
task-management-system/
├── build.gradle              # Gradle build configuration
├── settings.gradle           # Gradle settings (project name)
├── README.md                 # This document
├── src/
│   ├── main/java/            # Application and infrastructure code
│   │   └── com/example/taskmanagement/
│   │       ├── api/          # Simple Javalin REST API server
│   │       │   └── TaskApiServer.java
│   │       ├── dao/          # Data access layer
│   │       │   └── TaskDAO.java
│   │       ├── model/        # JPA entity classes
│   │       │   └── Task.java
│   │       └── utils/        # Utility classes
│   │           └── HibernateUtil.java
│   ├── main/resources/
│   │   └── hibernate.cfg.xml # Hibernate configuration for in‑memory H2
│   ├── test/java/
│   │   └── com/example/taskmanagement/
│   │       ├── api/          # API tests using Rest‑Assured
│   │       │   └── TaskApiTest.java
│   │       ├── db/           # Database tests
│   │       │   └── TaskDbTest.java
│   │       └── ui/           # UI tests using Selenide
│   │           └── TaskUITest.java
│   └── test/resources/
│       └── tasks.html        # Simple HTML page for UI test
└── .github/workflows/ci.yml  # GitHub Actions CI configuration
```

## Running the tests locally

1. Install JDK 17 and [Gradle](https://gradle.org/) (the project includes a wrapper script).
2. Clone the repository and navigate into the project directory.
3. Execute `./gradlew clean test` to run all tests.  The UI tests run in headless mode by default and should work on any platform.
4. After the tests complete, run `./gradlew allureReport` to generate an Allure report in `build/reports/allure-report`.  Open `index.html` in a browser to view the report.

## Notes

* The project deliberately keeps the application layer minimal—its purpose is to demonstrate testing techniques rather than to provide a production‑ready system.
* Because the API server is started within the test suite, there is no need to run a separate service; however, ports must be available on your machine (the default is 7001).
* The in‑memory H2 database is configured to persist for the duration of the JVM session (`DB_CLOSE_DELAY=-1`), so multiple tests can share state if required.
* Feel free to extend the `Task` model, add more endpoints to the API or replace the simple HTML UI with your own SPA to exercise the framework more thoroughly.