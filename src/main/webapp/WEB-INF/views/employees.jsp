<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 1/27/19
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Task Manager</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Task Manager</h1>
</div>
<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">
        <div class="w3-container w3-light-blue">
            <h2>Employees</h2>
        </div>
        <div class="w3-bar w3-padding-large w3-padding-24">
            <button class="w3-btn w3-hover-green w3-round-large w3-large" onclick="location.href='/add_employee'">Add employee</button>
        </div>
        <c:choose>
            <c:when test="${employees != null && !employees.isEmpty()}">
                <table class="w3-table w3-bordered w3-border">
                    <tr>
                        <th>Id</th>
                        <th>Last Name</th>
                        <th>First Name</th>
                        <th>Middle Name</th>
                        <th>Position</th>
                    </tr>

                    <c:forEach items="${employees}" var="employee">
                        <tr>
                            <td>${employee.id}</td>
                            <td>${employee.lastName}</td>
                            <td>${employee.firstName}</td>
                            <td>${employee.middleName}</td>
                            <td>${employee.position}</td>
                        </tr>
                    </c:forEach>

                </table>
            </c:when>
            <c:otherwise>
                <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
                    <span onclick="this.parentElement.style.display='none'"
                          class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey">
                    </span>
                    <h5>There are no employees yet!</h5>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main</button>
</div>
</body>
</html>
