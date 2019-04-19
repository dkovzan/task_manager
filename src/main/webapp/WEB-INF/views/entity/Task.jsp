<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.CommandEnum"%>
<%@ page import="com.kovzan.task_manager.command.ParameterNameConstant"%>
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
			<form action="controller" method="post"
				class="w3-selection w3-light-grey w3-padding">
				<c:set var="valid_error" value="${requestScope.get(ParameterNameConstant.VALIDATION_EXCEPTION)}"></c:set>
				<c:choose>
					<c:when test="${valid_error != null}">
						<c:set var="task" value="${valid_error.getEntity()}"></c:set>
						<c:set var="invalidFields" value="${valid_error.getInvalidFields()}"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="task" value="${requestScope.get(ParameterNameConstant.PRINTED_EDIT_TASK)}"></c:set>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when
						test="${requestScope.get(ParameterNameConstant.IS_ADD_FORM) == 1}">
						<input type="hidden" value="${CommandEnum.ADD_TASK}"
							name="command">
						<div class="w3-container w3-center w3-green">
							<h2>ADD TASK</h2>
						</div>
					</c:when>
					<c:otherwise>
						<input type="hidden" value="${CommandEnum.UPDATE_TASK}"
							name="command">
						<div class="w3-container w3-center w3-green">
							<h2>EDIT TASK</h2>
						</div>
						<label>ID: <input readonly
							name="${ParameterNameConstant.TASK_ID}"
							value="${task.id}"
							class="w3-input w3-animate-input w3-border w3-round-large"
							style="width: 30%">
						</label>
						<br>
					</c:otherwise>
				</c:choose>

				<label>Project:</label><br> <select
					class="w3-select w3-margin-bottom"
					name="${ParameterNameConstant.TASK_PROJECT_ID}" style="width: 30%">
					<c:forEach
						items="${requestScope.get(ParameterNameConstant.PRINTED_PROJECTS)}"
						var="projectInDropdown">
						<c:choose>
							<c:when test="${task.projectId == projectInDropdown.id}">
								<option selected value="${projectInDropdown.id}">${projectInDropdown.shortName}</option>
							</c:when>
							<c:otherwise>
								<option value="${projectInDropdown.id}">${projectInDropdown.shortName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select><br> <label>Name: <input placeholder="Write name"
					type="text" name="${ParameterNameConstant.TASK_NAME}"
					value="${task.name}"
					class="w3-input w3-animate-input w3-border w3-round-large"
					style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(ParameterNameConstant.TASK_NAME)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br> <label>Work: <input
					placeholder="Write work (natural number)" type="text"
					name="${ParameterNameConstant.TASK_WORK}"
					value="${task.work}"
					class="w3-input w3-animate-input w3-border w3-round-large"
					style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(ParameterNameConstant.TASK_WORK)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br> <label>Start Date: <input type="text" placeholder="Enter start date (format: yyyy-mm-dd)"
					name="${ParameterNameConstant.TASK_BEGINDATE}"
					value="${task.beginDate}"
					class="w3-input w3-border w3-round-large" style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(ParameterNameConstant.TASK_BEGINDATE)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br> <label>End Date: <input type="text" placeholder="Enter end date (format: yyyy-mm-dd)"
					name="${ParameterNameConstant.TASK_ENDDATE}"
					value="${task.endDate}"
					class="w3-input w3-border w3-round-large" style="width: 30%">
				</label><br> <label>Assignee:</label><br> <select
					class="w3-select w3-margin-bottom"
					name="${ParameterNameConstant.TASK_EMPLOYEE_ID}" style="width: 30%">
					<c:forEach
						items="${requestScope.get(ParameterNameConstant.PRINTED_EMPLOYEES)}"
						var="employee">
						<c:choose>
							<c:when test="${task.employeeId == employee.id}">
								<option selected value="${employee.id}">${employee.firstName}
									${employee.lastName}</option>
							</c:when>
							<c:otherwise>
								<option value="${employee.id}">${employee.firstName}
									${employee.lastName}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select><br> <label>Status:</label><br> <select
					class="w3-select w3-margin-bottom"
					name="${ParameterNameConstant.TASK_STATUS}" style="width: 30%">
					<c:forEach
						items="${requestScope.get(ParameterNameConstant.PRINTED_STATUSES)}"
						var="status">
						<c:choose>
							<c:when test="${task.getStatus().equals(status)}">
								<option selected value="${status}">${status.getStatusName()}</option>
							</c:when>
							<c:otherwise>
								<option value="${status}">${status.getStatusName()}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select><br>
				<button type="submit"
					class="w3-btn w3-green w3-round-large w3-margin-bottom">Save</button>
				<a href='${pageContext.request.contextPath}controller?command=${CommandEnum.PRINT_TASKS}'
					class="w3-btn w3-red w3-round-large w3-margin-bottom">Cancel</a>
			</form>
		</div>
	</div>
	<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
		<button class="w3-btn w3-round-large" onclick="location.href='${pageContext.request.contextPath}/'">Back
			to main</button>
	</div>
</body>
</html>
