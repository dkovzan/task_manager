<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.CommandEnum"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.ProjectParams"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.UtilParams"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.TaskParams"%>
<html>
<head>
<title>Edit project</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
	<div class="w3-container w3-blue-grey w3-opacity w3-left-align">
		<h1>Task Manager</h1>
	</div>
	<div class="w3-container w3-padding">
		<div class="projectBlock w3-card-4">
			<form action="controller" method="post"
				class="w3-selection w3-light-grey w3-padding">
				<c:set var="valid_error" value="${requestScope.get(UtilParams.VALIDATION_EXCEPTION)}"></c:set>
				<c:set var="runtime_tasks" value="${sessionScope.get(TaskParams.PRINTED_RUNTIME_TASKS)}"></c:set>
				<c:set var="is_add_project_form" value="${sessionScope.get(UtilParams.IS_ADD_FORM)}"></c:set>
				<c:choose>
					<c:when test="${valid_error != null}">
						<c:set var="project" value="${valid_error.getEntity()}"></c:set>
						<c:set var="invalidFields" value="${valid_error.getInvalidFields()}"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="project" value="${sessionScope.get(ProjectParams.PRINTED_EDIT_PROJECT)}"></c:set>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when
						test="${is_add_project_form}">
						<input type="hidden" value="${CommandEnum.ADD_PROJECT}"
							name="command">
						<input type="hidden" id="${ProjectParams.PROJECT_ID}"
							   name="${ProjectParams.PROJECT_ID}"
							   value="-1">
						<div class="w3-container w3-center w3-green">
							<h2>ADD PROJECT</h2>
						</div>
						<br>
					</c:when>
					<c:otherwise>
						<input type="hidden" value="${CommandEnum.UPDATE_PROJECT}"
							name="command">
						<div class="w3-container w3-center w3-green">
							<h2>EDIT PROJECT</h2>
						</div>
						<%--<label>ID:</label>--%>
						<input readonly type="hidden" id="${ProjectParams.PROJECT_ID}"
							name="${ProjectParams.PROJECT_ID}"
							value="${project.id}"
							class="w3-input w3-border w3-round-large"
							style="width: 30%">
						<br>
					</c:otherwise>
				</c:choose>
				<label>Name:* <input id="${ProjectParams.PROJECT_NAME}" placeholder="Enter name" type="text"
					name="${ProjectParams.PROJECT_NAME}"
					value="${project.name}"
					class="w3-input w3-border w3-round-large"
					style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(ProjectParams.PROJECT_NAME)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br> <label>Short name:* <input id="${ProjectParams.PROJECT_SHORTNAME}"
					placeholder="Enter short name" type="text"
					name="${ProjectParams.PROJECT_SHORTNAME}"
					value="${project.shortName}"
					class="w3-input w3-border w3-round-large"
					style="width: 30%">
				</label>
				<c:if test="${invalidFields.containsKey(ProjectParams.PROJECT_SHORTNAME)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<c:if test="${invalidFields.containsKey(ProjectParams.PROJECT_SHORTNAME_NOT_UNIQUE)}">
					<span style="color:red"><c:out value="${ProjectParams.PROJECT_SHORTNAME_NOT_UNIQUE_MESSAGE}"></c:out></span>
				</c:if>
				<br> <label>Description: <textarea id="${ProjectParams.PROJECT_DESCRIPTION}"
						placeholder="Enter description"
						name="${ProjectParams.PROJECT_DESCRIPTION}"
						class="w3-input w3-border w3-round-large"
						style="width: 30%; resize: none">${project.description}</textarea>
				</label>
				<c:if test="${invalidFields.containsKey(ProjectParams.PROJECT_DESCRIPTION)}">
					<span style="color:red"><c:out value="${valid_error.getMessage()}"></c:out></span>
				</c:if>
				<br>
				<button type="submit"
						class="w3-btn w3-green w3-round-large w3-margin-bottom">Save</button>
				<a href='${pageContext.request.contextPath}controller?command=${CommandEnum.PRINT_PROJECTS}'
				   class="w3-btn w3-red w3-round-large w3-margin-bottom">Cancel</a>
			</form>
				
			<c:choose>
				<c:when test="${requestScope.get(UtilParams.ERROR) != null}">
					<div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
						<span onclick="this.parentElement.style.display='none'"
							  class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey">X</span>
						<h5>${requestScope.get(UtilParams.ERROR)}</h5>
					</div>
				</c:when>
			</c:choose>
		</div>
	</div>
	<div class="w3-container w3-padding">
			<div class="tasksBlock w3-card-4 w3-padding">
				<a id="addTask"
				   href='${pageContext.request.contextPath}controller?command=${CommandEnum.PRINT_EDIT_RUNTIME_TASK}
				&${UtilParams.IS_ADD_FORM}=true'
				   onclick="getParams(this.id)"
				   class="w3-btn w3-yellow w3-round-large w3-margin-bottom">Add task</a>

				<c:choose>
					<c:when
							test="${runtime_tasks != null && !runtime_tasks.isEmpty()}">
						<table class="w3-table w3-bordered w3-border">
							<tr>
								<%--<th>Id</th>--%>
								<th>Name</th>
								<th>Begin Date</th>
								<th>End Date</th>
								<th>Assignee</th>
								<th colspan="2"></th>
							</tr>
							<c:forEach
									items="${runtime_tasks}"
									var="task">
								<tr>
									<%--<td>${task.id}</td>--%>
									<td>${task.name}</td>
									<td>${task.beginDate}</td>
									<td>${task.endDate}</td>
									<td>${task.employeeFullName}</td>
									<td>
										<a id="updateTask${task.id}"
										   href='${pageContext.request.contextPath}controller?command=${CommandEnum.PRINT_EDIT_RUNTIME_TASK}&${TaskParams.TASK_ID}=${task.id}&${UtilParams.IS_ADD_FORM}=false'
										   onclick="getParams(this.id)"
										   class="w3-button w3-indigo w3-round-large">Edit</a>
									</td>
									<td>
										<a id="deleteTask${task.id}"
										   href='${pageContext.request.contextPath}controller?command=${CommandEnum.REMOVE_RUNTIME_TASK}&${TaskParams.TASK_ID}=${task.id}'
										   onclick="getParams(this.id)"
										   class="w3-button w3-red w3-round-large">Delete</a>
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
					</c:when>
					<c:otherwise>
						<div
								class="w3-panel w3-blue w3-display-container w3-card-4 w3-round">
							<h5>Project has no tasks yet!</h5>
						</div>
						<br>
					</c:otherwise>
				</c:choose>
			</div>
	</div>
	<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
		<button class="w3-btn w3-round-large" onclick="location.href='../../../'">Back
			to main</button>
	</div>
<script>
function getParams(elemId) {
    var element = document.getElementById(elemId);
    var id = document.getElementById('project_id').value;
    var name = document.getElementById('project_name').value;
    var shortName = document.getElementById('project_shortName').value;
    var description = document.getElementById('project_description').value;
    var href = element.getAttribute('href');
    var params = '&project_id=' + id + '&project_name=' + name + '&project_shortName=' + shortName+'&project_description=' + description;
    element.setAttribute('href', href + params);
}
</script>
</body>
</html>
