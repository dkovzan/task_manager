<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.CommandEnum"%>
<%@ page import="com.kovzan.task_manager.command.ParameterNameConstant"%>
<html>
<head>
<title>Project</title>
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
				<c:choose>
					<c:when
						test="${requestScope.get(ParameterNameConstant.IS_ADD_FORM) == 1}">
						<input type="hidden" value="${CommandEnum.ADD_PROJECT}"
							name="command">
						<div class="w3-container w3-center w3-green">
							<h2>ADD PROJECT</h2>
						</div>
					</c:when>
					<c:otherwise>
						<input type="hidden" value="${CommandEnum.UPDATE_PROJECT}"
							name="command">
						<div class="w3-container w3-center w3-green">
							<h2>EDIT PROJECT</h2>
						</div>
						<label>ID: <input readonly
							name="${ParameterNameConstant.PROJECT_ID}"
							value="${printed_edit_project.id}"
							class="w3-input w3-animate-input w3-border w3-round-large"
							style="width: 30%">
						</label>
						<br>
					</c:otherwise>
				</c:choose>
				<label>Name: <input placeholder="Write name" required
					type="text" name="${ParameterNameConstant.PROJECT_NAME}"
					value="${printed_edit_project.name}"
					class="w3-input w3-animate-input w3-border w3-round-large"
					style="width: 30%">
				</label><br> <label>Short name: <input
					placeholder="Write short name" required type="text"
					name="${ParameterNameConstant.PROJECT_SHORTNAME}"
					value="${printed_edit_project.shortName}"
					class="w3-input w3-animate-input w3-border w3-round-large"
					style="width: 30%">
				</label><br> <label>Description: <textarea
						placeholder="Write description"
						name="${ParameterNameConstant.PROJECT_DESCRIPTION}"
						class="w3-input w3-animate-input w3-border w3-round-large"
						style="width: 30%; resize: none">${printed_edit_project.description}</textarea>
				</label><br>
				
				<c:choose>
					<c:when
						test="${requestScope.get(ParameterNameConstant.ERROR) != null}">
						<div
							class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
							<span onclick="this.parentElement.style.display='none'"
								class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey">X
							</span>
							<h5>${requestScope.get(ParameterNameConstant.ERROR)}</h5>
						</div>
					</c:when>
				</c:choose>
				
				<button type="submit"
					class="w3-btn w3-green w3-round-large w3-margin-bottom">Save</button>
				<button
					onclick="location.href='${pageContext.request.contextPath}/controller?command=${CommandEnum.PRINT_PROJECTS}'"
					class="w3-btn w3-red w3-round-large w3-margin-bottom">Cancel</button>
			</form>
		</div>
	</div>
	<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
		<button class="w3-btn w3-round-large" onclick="location.href='${pageContext.request.contextPath}/'">Back
			to main</button>
	</div>
</body>
</html>
