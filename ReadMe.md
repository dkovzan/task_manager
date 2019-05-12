# Task manager

The application is used for managing projects, tasks and employees.

Technologies used:<br>
JSP API, Servlet API, JDBC (HSQLDB), JSTL.

Builder:<br>
Gradle

Dependencies:<br>
_jdk 1.8_

### How to start:
1. Download project from git repository.
2. Open console in root folder of the project <br>../task_manager
3. Build project:<br>
    - Execute command in console<br>**gradlew clean build**
4. Start database server:<br>
    - Execute command in console<br>**gradlew startDatabase**
5. Tables management:<br>
    - Open new console
    - Execute command in console to create tables<br>
    **gradlew createTables**
6. Start Tomcat server:<br>
    - Execute command in console<br>
    **gradlew startServer**
