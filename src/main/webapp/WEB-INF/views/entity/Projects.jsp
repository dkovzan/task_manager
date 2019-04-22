<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.CommandEnum"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.ProjectParams"%>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.UtilParams"%>
<html>
<head>
<title>Projects</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
	<jsp:include page="/WEB-INF/views/common/Header.jsp"></jsp:include>
	<div class="w3-container w3-center w3-margin-bottom w3-padding">
		<div class="w3-card-4">
			<div class="w3-container w3-light-blue">
				<h2>Projects overview</h2>
			</div>
			<div class="w3-bar w3-padding-large w3-padding-24">
				<button
					class="w3-button w3-hover-grey w3-round-large w3-large w3-green"
					onclick="location.href='${pageContext.request.contextPath}controller?command=${CommandEnum.PRINT_EDIT_PROJECT}&${UtilParams.IS_ADD_FORM}=1'">Add
					project</button>
			</div>
			<c:choose>
				<c:when
					test="${requestScope.get(ProjectParams.PRINTED_PROJECTS) != null && !requestScope.get(ProjectParams.PRINTED_PROJECTS).isEmpty()}">
					<table class="w3-table w3-bordered w3-border">
						<tr>
							<th>Id</th>
							<th>Name</th>
							<th>Short Name</th>
							<th>Description</th>
							<th colspan="2">Actions</th>
						</tr>
						<c:forEach
							items="${requestScope.get(ProjectParams.PRINTED_PROJECTS)}"
							var="project">
							<tr>
								<input type="hidden" name="${ProjectParams.PROJECT_ID}"
									value="${project.id}">
								<td>${project.id}</td>
								<td>${project.name}</td>
								<td>${project.shortName}</td>
								<td>${project.description}</td>
								<td>
									<button
										onclick="location.href='${pageContext.request.contextPath}controller?command=${CommandEnum.PRINT_EDIT_PROJECT}&${ProjectParams.PROJECT_ID}=${project.id}&${UtilParams.IS_ADD_FORM}=0'"
										class="w3-button w3-indigo w3-round-large">Edit</button>
								</td>
								<td>
									<button
										onclick="location.href='${pageContext.request.contextPath}controller?command=${CommandEnum.REMOVE_PROJECT}&${ProjectParams.PROJECT_ID}=${project.id}'"
										class="w3-button w3-red w3-round-large">Delete</button>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div
						class="w3-panel w3-blue w3-display-container w3-card-4 w3-round">
						<h5>There are no projects yet!</h5>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
		<button class="w3-btn w3-round-large" onclick="location.href='${pageContext.request.contextPath}/'">Back
			to main</button>
	</div>
</body>
</html>
