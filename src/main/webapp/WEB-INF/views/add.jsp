<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 1/27/19
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<div class="w3-container w3-padding">
    <c:if test="${employeeName != null}">
        <div class="w3-panel w3-green w3-display-container w3-card-4 w3-round\">
            <span onclick="this.parentElement.style.display='none'"
                  class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">
            </span>
            <h5>User '<c:out value="${employeeName}"></c:out>' is added!</h5>
        </div>
    </c:if>
    <%
        /*if (request.getAttribute("employeeName") != null) {
            out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                    "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                    "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                    "   <h5>User '" + request.getAttribute("employeeName") + "' added!</h5>\n" +
                    "</div>");
        }
        */
    %>
    <div class="w3-card-4">
        <div class="w3-container w3-center w3-green">
            <h2>Add employee</h2>
        </div>
        <form method="post" class="w3-selection w3-light-grey w3-padding">
            <label>First name:
                <input required type="text" name="firstName" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
            </label><br>
            <label>Last name:
                <input required type="text" name="lastName" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
            </label><br>
            <label>Middle name:
                <input type="text" name="middleName" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
            </label><br>
            <label>Position:
                <input required type="text" name="position" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
            </label><br>
            <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Add</button>
        </form>
    </div>
</div>
    <div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
        <button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main</button>
    </div>
</body>
</html>