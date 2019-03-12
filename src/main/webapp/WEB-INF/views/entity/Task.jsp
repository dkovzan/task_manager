<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 2/24/19
  Time: 10:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.CommandEnum" %>
<%@ page import="com.kovzan.task_manager.command.ParameterNameConstant" %>
<html>
<head>
	<title>Task</title>
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-left-align">
	<h1>Task Manager</h1>
</div>
<div class="w3-container w3-padding">
	<div class="w3-card-4">
		<form action="controller" method="post" class="w3-selection w3-light-grey w3-padding">
			<c:choose>
				<c:when test="${is_add_form == '1'}">
					<input type="hidden" value="${CommandEnum.ADD_TASK}" name="command">
					<div class="w3-container w3-center w3-green">
						<h2>ADD TASK</h2>
					</div>
				</c:when>
				<c:otherwise>
					<input type="hidden" value="${CommandEnum.UPDATE_TASK}" name="command">
					<div class="w3-container w3-center w3-green">
						<h2>EDIT TASK</h2>
					</div>
					<label>ID:
						<input readonly name="${ParameterNameConstant.TASK_ID}" value="${printed_edit_task.id}" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
					</label><br>
				</c:otherwise>
			</c:choose>
			<label>Project:</label><br>
			<select class="w3-select w3-margin-bottom" name="${ParameterNameConstant.TASK_PROJECT_ID}" style="width: 30%">
				<c:forEach items="${requestScope.get(ParameterNameConstant.PRINTED_PROJECTS)}" var="project">
					<c:choose>
						<c:when test="${printed_edit_task.projectId == project.id}">
							<option selected value="${project.id}">${project.shortName}</option>
						</c:when>
						<c:otherwise>
							<option value="${project.id}">${project.shortName}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select><br>

			<label>Name:
				<input required type="text" name="${ParameterNameConstant.TASK_NAME}" value="${printed_edit_task.name}" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
			</label><br>

			<label>Estimate:
				<input required type="text" name="${ParameterNameConstant.TASK_ESTIMATE}" value="${printed_edit_task.estimate}" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
			</label><br>

			<label>Start Date:
				<input required type="date" name="${ParameterNameConstant.TASK_CREATEDON}" value="${printed_edit_task.createdOn}" class="w3-input w3-border w3-round-large" style="width: 30%">
			</label><br>

			<label>Finish Date:
				<input required type="date" name="${ParameterNameConstant.TASK_FINISHEDON}" value="${printed_edit_task.finishedOn}" class="w3-input w3-border w3-round-large" style="width: 30%">
			</label><br>

			<label>Assignee:</label><br>
			<select class="w3-select w3-margin-bottom" name="${ParameterNameConstant.TASK_EMPLOYEE_ID}" style="width: 30%">
				<c:forEach items="${requestScope.get(ParameterNameConstant.PRINTED_EMPLOYEES)}" var="employee">
					<c:choose>
						<c:when test="${printed_edit_task.employeeId == employee.id}">
							<option selected value="${employee.id}">${employee.firstName} ${employee.lastName}</option>
						</c:when>
						<c:otherwise>
							<option value="${employee.id}">${employee.firstName} ${employee.lastName}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select><br>

			<label>Status:</label><br>
			<select class="w3-select w3-margin-bottom" name="${ParameterNameConstant.TASK_STATUS_ID}" style="width: 30%">
				<c:forEach items="${requestScope.get(ParameterNameConstant.PRINTED_STATUSES)}" var="status">
					<c:choose>
						<c:when test="${printed_edit_task.statusId == status.id}">
							<option selected value="${status.id}">${status.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${status.id}">${status.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select><br>

			<button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Save</button>
			<button onclick="location.href='/controller?command=${CommandEnum.PRINT_TASKS}'" class="w3-btn w3-red w3-round-large w3-margin-bottom">Cancel</button>
		</form>
	</div>
</div>
</div>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
	<button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main</button>
</div>
</body>
</html>
