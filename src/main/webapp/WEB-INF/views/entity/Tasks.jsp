<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 2/24/19
  Time: 10:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.CommandEnum" %>
<%@ page import="com.kovzan.task_manager.command.ParameterNameConstant" %>
<html>
<head>
    <title>Tasks</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<jsp:include page="/WEB-INF/views/common/Header.jsp"></jsp:include>
<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">
        <div class="w3-container w3-light-blue">
            <h2>Tasks overview</h2>
        </div>
        <div class="w3-bar w3-padding-large w3-padding-24">
            <button class="w3-button w3-hover-grey w3-round-large w3-large w3-green" onclick="location.href='controller?command=${CommandEnum.PRINT_EDIT_TASK}&task_id=-1'">Add task</button>
        </div>
        <c:choose>
            <c:when test="${requestScope.get(ParameterNameConstant.PRINTED_TASKS) != null && !requestScope.get(ParameterNameConstant.PRINTED_TASKS).isEmpty()}">
                <table class="w3-table w3-bordered w3-border">
                    <tr>
                        <th>Id</th>
                        <th>Project</th>
                        <th>Name</th>
                        <th>Start Date</th>
                        <th>Finish Date</th>
                        <th>Assignee</th>
                        <th colspan="2">Actions</th>
                    </tr>
                    <c:forEach items="${requestScope.get(ParameterNameConstant.PRINTED_TASKS)}" var="task">
                        <tr>
                            <input type="hidden" name="${ParameterNameConstant.TASK_ID}" value="${task.id}">
                            <td>${task.id}</td>
                            <td>${task.projectShortName}</td>
                            <td>${task.name}</td>
                            <td>${task.createdOn}</td>
                            <td>${task.finishedOn}</td>
                            <td>${task.employeeFullName}</td>
                            <td>
                                <button onclick="location.href='controller?command=${CommandEnum.PRINT_EDIT_TASK}&task_id=${task.id}&is_add_form=0'" class="w3-button w3-indigo w3-round-large">Edit</button>
                            </td>
                            <td>
                                <button onclick="location.href='controller?command=${CommandEnum.REMOVE_TASK}&task_id=${task.id}'" class="w3-button w3-red w3-round-large">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
                    <span onclick="this.parentElement.style.display='none'"
                          class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey">X
                    </span>
                    <h5>There are no tasks yet!</h5>
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
