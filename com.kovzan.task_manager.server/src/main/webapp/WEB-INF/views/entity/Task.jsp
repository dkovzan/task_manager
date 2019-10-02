<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.Commands"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.EmployeeParams"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.ProjectParams"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.TaskParams"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.UtilParams"%>
<html>
<head>
	<title>Edit task</title>
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" href="style/style.css" type="text/css">
</head>
<body class="w3-light-grey">
	<div class="w3-container w3-blue-grey w3-opacity w3-left-align">
		<h1>Task Manager</h1>
	</div>
	<div class="w3-container w3-padding">
		<div class="w3-card-4">
			<form action="controller" method="post" class="w3-selection w3-light-grey w3-padding">
				<c:set var="valid_error" value="${requestScope.get(UtilParams.VALIDATION_EXCEPTION)}"></c:set>
				<c:choose>
					<c:when test="${valid_error != null}">
						<c:set var="task" value="${valid_error.getEntity()}"></c:set>
						<c:set var="invalidFields" value="${valid_error.getInvalidFields()}"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="task" value="${requestScope.get(TaskParams.PRINTED_EDIT_TASK)}"></c:set>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${requestScope.get(UtilParams.IS_ADD_FORM)}">
						<input type="hidden" value="${Commands.ADD_TASK}" name="command">
						<div class="w3-container w3-center w3-green">
							<h2>ADD TASK</h2>
						</div>
						<br>
					</c:when>
					<c:otherwise>
						<input type="hidden" value="${Commands.UPDATE_TASK}"
							name="command">
						<div class="w3-container w3-center w3-green">
							<h2>EDIT TASK</h2>
						</div>
						<%--<label>ID:</label>--%>
						<input readonly type="hidden" name="${TaskParams.TASK_ID}"
							   value="${fn:escapeXml(task.id)}" class="w3-input w3-border w3-round-large"
							   style="width: 30%">
						<br>
					</c:otherwise>
				</c:choose>

				<label>Project:<span class="required-field-mark">*</span>
					<br>
					<select class="w3-select w3-margin-bottom" name="${TaskParams.TASK_PROJECT_ID}"
							style="width: 30%">
						<option hidden disabled selected value style="color: #a9a9a9">Select project</option>
						<c:forEach items="${requestScope.get(ProjectParams.PRINTED_PROJECTS)}"
							var="projectInDropdown">
							<c:choose>
								<c:when test="${task.projectId == projectInDropdown.id}">
									<option selected value="${projectInDropdown.id}">${fn:escapeXml(projectInDropdown.shortName)}</option>
								</c:when>
								<c:otherwise>
									<option value="${projectInDropdown.id}">${fn:escapeXml(projectInDropdown.shortName)}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</label>
				<c:if test="${invalidFields.containsKey(TaskParams.TASK_PROJECT_ID)}">
					<span style="color:red; display: block"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<label>Name:<span class="required-field-mark">*</span>
					<input placeholder="Enter name" type="text" name="${TaskParams.TASK_NAME}"
						   value="${fn:escapeXml(task.name)}" class="w3-input w3-border w3-round-large"
						   style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(TaskParams.TASK_NAME)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<label>Work:<span class="required-field-mark">*</span>
					<input placeholder="Enter work (natural number)" type="text"
					name="${TaskParams.TASK_WORK}" value="${fn:escapeXml(task.work)}"
					class="w3-input w3-border w3-round-large" style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(TaskParams.TASK_WORK)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<label>Begin Date:<span class="required-field-mark">*</span>
					<input type="text" placeholder="Enter begin date (format: yyyy-mm-dd)"
						   name="${TaskParams.TASK_BEGINDATE}" value="${fn:escapeXml(task.beginDate)}"
						   class="w3-input w3-border w3-round-large" style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(TaskParams.TASK_BEGINDATE)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<c:if test="${invalidFields.containsKey(TaskParams.TASK_INVALID_DATE_RANGE)}">
					<span style="color:red"><c:out value="${TaskParams.VALUE_INVALID_DATE_RANGE}"></c:out></span>
				</c:if>
				<br>
				<label>End Date:<span class="required-field-mark">*</span>
					<input type="text" placeholder="Enter end date (format: yyyy-mm-dd)"
						   name="${TaskParams.TASK_ENDDATE}" value="${fn:escapeXml(task.endDate)}"
						   class="w3-input w3-border w3-round-large" style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(TaskParams.TASK_ENDDATE)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<label>Assignee:<span class="required-field-mark">*</span>
				<br>
					<select class="w3-select w3-margin-bottom" name="${TaskParams.TASK_EMPLOYEE_ID}"
							style="width: 30%">
						<option hidden disabled selected value style="color: #a9a9a9">Select assignee</option>
						<c:forEach items="${requestScope.get(EmployeeParams.PRINTED_EMPLOYEES)}"
							var="employee">
							<c:choose>
								<c:when test="${task.employeeId == employee.id}">
									<option selected value="${employee.id}">${fn:escapeXml(employee.firstName)} ${fn:escapeXml(employee.lastName)}</option>
								</c:when>
								<c:otherwise>
									<option value="${employee.id}">${fn:escapeXml(employee.firstName)} ${fn:escapeXml(employee.lastName)}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</label>
				<c:if test="${invalidFields.containsKey(TaskParams.TASK_EMPLOYEE_ID)}">
					<span style="color:red; display: block"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<label>Status:<span class="required-field-mark">*</span>
				<br>
					<select class="w3-select w3-margin-bottom" name="${TaskParams.TASK_STATUS}"
							style="width: 30%">
						<option hidden disabled selected value style="color: #a9a9a9">Select status</option>
						<c:forEach items="${requestScope.get(TaskParams.PRINTED_STATUSES)}" var="status">
							<c:choose>
								<c:when test="${task.getStatus().equals(status)}">
									<option selected value="${status}">${status.getStatusName()}</option>
								</c:when>
								<c:otherwise>
									<option value="${status}">${status.getStatusName()}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</label>
				<c:if test="${invalidFields.containsKey(TaskParams.TASK_STATUS)}">
					<span style="color:red; display: block"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<button type="submit"
					class="w3-btn w3-green w3-round-large w3-margin-bottom">Save</button>
				<a href='${pageContext.request.contextPath}controller?command=${Commands.PRINT_TASKS}'
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
