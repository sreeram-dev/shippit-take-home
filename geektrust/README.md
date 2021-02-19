GeekTrust Challenge
============================

[![Java CI with Gradle](https://github.com/ShippitRecruitment/backend-challenge_sreeram-boyapati/actions/workflows/gradle.yml/badge.svg?branch=main)](https://github.com/ShippitRecruitment/backend-challenge_sreeram-boyapati/actions/workflows/gradle.yml)
![Test Coverage](https://github.com/ShippitRecruitment/backend-challenge_sreeram-boyapati/blob/main/.github/badges/jacoco.svg)

Java Version: 8 <br/>
Recommended IDE:  IntelliJ 2020.3  <br/>
Gradle Version: 6.7

## Installation

The project uses Gradle 6.7 as the build tool.

Please run `./gradlew build` to build the project for generating the jar.
The jar has all the dependencies and it is in `build/libs/geektrust.jar`


## Run Configurations
Please use the Run configurations in the `.idea` directory to test run
the application with test files from the `src/test/resources` java.


## Unit and Integration Tests
Please run  the following command to run the tests
`./gradlew test`

Tests are located in the `src/test/java/` folder and follow the layout guidelines for gradle/maven projects.

Please use the testing options specified in https://docs.gradle.org/current/userguide/java_testing.html#full_qualified_name_pattern to run individual tests.

An example to run Individual Test - <br/>
`./gradlew test --tests "com.interview.shippit.cli.CLIApplicationTest.testSimpleTestCase_PersonNotFound"`


## Project Structure
The project follows the clean architecture paradigm - <br/>
1. Domain and Usecases (Interactors) are listed in `com.interview.shippit.family` package. They implement the business rules and contain domain knowledge.
2. Adapters (Interactor Adapter) implement interfaces as defined in usecases to provide support functions for business rule execution. They are listed in `com.interview.shippit.family`.
3. HashMap based DB persists data during application run, implements the repository interface defined in the usecases. Package is `com.interview.shippit.db`
4. CLI Functionality plugs into the adapter and Application delegates calls to the controller. Controller returns data in rest, json, xml format required by the user. Package is `com.interview.shippit.cli`.
