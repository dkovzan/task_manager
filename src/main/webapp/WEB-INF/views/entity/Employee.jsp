<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 1/27/19
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.CommandEnum" %>
<%@ page import="com.kovzan.task_manager.command.ParameterNameConstant" %>
<html>
<head>
	<title>Employee</title>
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
						<input type="hidden" value="${CommandEnum.ADD_EMPLOYEE}" name="command">
						<div class="w3-container w3-center w3-green">
							<h2>ADD EMPLOYEE</h2>
						</div>
					</c:when>
					<c:otherwise>
						<input type="hidden" value="${CommandEnum.UPDATE_EMPLOYEE}" name="command">
						<div class="w3-container w3-center w3-green">
							<h2>EDIT EMPLOYEE</h2>
						</div>
						<label>ID:
							<input readonly name="${ParameterNameConstant.EMPLOYEE_ID}" value="${printed_edit_employee.id}" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
						</label><br>
					</c:otherwise>
				</c:choose>
			<label>First name:
				<input required type="text" name="${ParameterNameConstant.EMPLOYEE_FIRSTNAME}" value="${printed_edit_employee.firstName}" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
			</label><br>
			<label>Last name:
				<input required type="text" name="${ParameterNameConstant.EMPLOYEE_LASTNAME}" value="${printed_edit_employee.lastName}" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
			</label><br>
			<label>Middle name:
				<input type="text" name="${ParameterNameConstant.EMPLOYEE_MIDDLENAME}" value="${printed_edit_employee.middleName}" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
			</label><br>
			<label>Position:
				<input required type="text" name="${ParameterNameConstant.EMPLOYEE_POSITION}" value="${printed_edit_employee.position}" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%">
			</label><br>
			<button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Save</button>
			<button onclick="location.href='/controller?command=${CommandEnum.PRINT_EMPLOYEES}'" class="w3-btn w3-red w3-round-large w3-margin-bottom">Cancel</button>
		</form>
	</div>
</div>
</div>
	<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
		<button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main</button>
	</div>
</body>
</html>