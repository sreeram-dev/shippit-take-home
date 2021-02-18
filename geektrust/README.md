GeekTrust Challenge
============================

[![Java CI with Gradle](https://github.com/ShippitRecruitment/backend-challenge_sreeram-boyapati/actions/workflows/gradle.yml/badge.svg?branch=main)](https://github.com/ShippitRecruitment/backend-challenge_sreeram-boyapati/actions/workflows/gradle.yml)

Java Version: 8  
Recommended IDE:  IntelliJ 2020.3  
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

Please use the testing options specified in https://docs.gradle.org/current/userguide/java_testing.html#full_qualified_name_pattern
to run individual tests

