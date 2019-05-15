<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.CommandEnum"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.UtilParams"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.EmployeeParams"%>
<html>
<head>
<title>Edit employee</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
	<div class="w3-container w3-blue-grey w3-opacity w3-left-align">
		<h1>Task Manager</h1>
	</div>
	<div class="w3-container w3-padding">
		<div class="w3-card-4">
			<form action="controller" method="post"
				class="w3-selection w3-light-grey w3-padding">
                <c:set var="valid_error" value="${requestScope.get(UtilParams.VALIDATION_EXCEPTION)}"></c:set>
				<c:choose>
					<c:when test="${valid_error != null}">
						<c:set var="employee" value="${valid_error.getEntity()}"></c:set>
                        <c:set var="invalidFields" value="${valid_error.getInvalidFields()}"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="employee" value="${requestScope.get(EmployeeParams.PRINTED_EDIT_EMPLOYEE)}"></c:set>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when
						test="${requestScope.get(UtilParams.IS_ADD_FORM)}">
						<input type="hidden" value="${CommandEnum.ADD_EMPLOYEE}"
							name="command">
						<div class="w3-container w3-center w3-green">
							<h2>ADD EMPLOYEE</h2>
						</div>
						<br>
					</c:when>
					<c:otherwise>
						<input type="hidden" value="${CommandEnum.UPDATE_EMPLOYEE}"
							name="command">
						<div class="w3-container w3-center w3-green">
							<h2>EDIT EMPLOYEE</h2>
						</div>
						<%--<label>ID: </label>--%>
						<input readonly type="hidden" name="${EmployeeParams.EMPLOYEE_ID}"
							value="${employee.id}" class="w3-input w3-border w3-round-large"
							style="width: 30%">
						<br>
					</c:otherwise>
				</c:choose>
				<label>First name:*
				<input placeholder="Enter first name"
					type="text"
					name="${EmployeeParams.EMPLOYEE_FIRSTNAME}"
					value="${employee.firstName}"
					class="w3-input w3-border w3-round-large"
					style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(EmployeeParams.EMPLOYEE_FIRSTNAME)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<label>Last name:*
					<input placeholder="Enter last name" type="text" name="${EmployeeParams.EMPLOYEE_LASTNAME}"
					value="${employee.lastName}" class="w3-input w3-border w3-round-large"
					style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(EmployeeParams.EMPLOYEE_LASTNAME)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<label>Middle name:
					<input placeholder="Enter middle name (optional)" type="text"
					name="${EmployeeParams.EMPLOYEE_MIDDLENAME}" value="${employee.middleName}"
					class="w3-input w3-border w3-round-large"
					style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(EmployeeParams.EMPLOYEE_MIDDLENAME)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<label>Position:*
					<input placeholder="Enter position" type="text"
					name="${EmployeeParams.EMPLOYEE_POSITION}" value="${employee.position}"
					class="w3-input w3-border w3-round-large"
					style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(EmployeeParams.EMPLOYEE_POSITION)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<button type="submit"
					class="w3-btn w3-green w3-round-large w3-margin-bottom">Save</button>
				<a href='${pageContext.request.contextPath}/controller?command=${CommandEnum.PRINT_EMPLOYEES}'
					class="w3-btn w3-red w3-round-large w3-margin-bottom">Cancel</a>
			</form>
		</div>
	</div>
	<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
		<button class="w3-btn w3-round-large" onclick="location.href='../../../'">Back
			to main</button>
	</div>
</body>
</html>