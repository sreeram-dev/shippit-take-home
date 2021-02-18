GeekTrust Challenge
============================

Java Version: 8
Recommended IDE:  IntelliJ 2020.3
Gradle Version: 6.7


## Installation

The project uses Gradle 6.7 as the build tool.

Please run `./gradlew build` to build the project for generating the jar.
The jar has all the dependencies and it is in `build/libs/geektrust.jar`


## Run Configurations
Please use the `gradle run` configuration in the `.idea` directory to test run
the application with test files from the `src/test/resources` java.


## Gradle Wrapper Based Testing
For standalone testing use `./gradlew run`. <br\>
Here is an example command using test resources:
`./gradlew run --args="src/test/resources/simple_testcase_1.txt"`

## Unit and Integration Tests
Please run  the following command to run the tests
`./gradlew test`

Tests are located in the `src/test/java/` folder and follow the layout guidelines for gradle/maven projects.

Please use the testing options specified in https://docs.gradle.org/current/userguide/java_testing.html#full_qualified_name_pattern
to run individual tests

