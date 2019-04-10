<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.CommandEnum" %>
<%@ page import="com.kovzan.task_manager.command.ParameterNameConstant" %>
<html>
<head>
	<title>Employees</title>
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<jsp:include page="/WEB-INF/views/common/Header.jsp"></jsp:include>
<div class="w3-container w3-center w3-margin-bottom w3-padding">
	<div class="w3-card-4">
		<div class="w3-container w3-light-blue">
			<h2>Employees overview</h2>
		</div>
		<div class="w3-bar w3-padding-large w3-padding-24">
			<button class="w3-button w3-hover-grey w3-round-large w3-large w3-green" onclick="location.href='controller?command=${CommandEnum.PRINT_EDIT_EMPLOYEE}&${ParameterNameConstant.IS_ADD_FORM}=1'">Add employee</button>
		</div>

		<c:choose>
			<c:when test="${requestScope.get(ParameterNameConstant.ERROR) != null}">
				<div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
							<span onclick="this.parentElement.style.display='none'"
								  class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey">X
							</span>
					<h5>${requestScope.get(ParameterNameConstant.ERROR)}</h5>
				</div>
				</br>
			</c:when>
		</c:choose>

		<c:choose>
			<c:when test="${requestScope.get(ParameterNameConstant.PRINTED_EMPLOYEES) != null && !requestScope.get(ParameterNameConstant.PRINTED_EMPLOYEES).isEmpty()}">
				<table class="w3-table w3-bordered w3-border">
					<tr>
						<th>Id</th>
						<th>Last Name</th>
						<th>First Name</th>
						<th>Middle Name</th>
						<th>Position</th>
						<th colspan="2">Actions</th>
					</tr>
					<c:forEach items="${requestScope.get(ParameterNameConstant.PRINTED_EMPLOYEES)}" var="employee">
						<tr>
							<input type="hidden" name="${ParameterNameConstant.EMPLOYEE_ID}" value="${employee.id}">
							<td>${employee.id}</td>
							<td>${employee.lastName}</td>
							<td>${employee.firstName}</td>
							<td>${employee.middleName}</td>
							<td>${employee.position}</td>
							<td>
								<button onclick="location.href='controller?command=${CommandEnum.PRINT_EDIT_EMPLOYEE}&employee_id=${employee.id}&${ParameterNameConstant.IS_ADD_FORM}=0'" class="w3-button w3-indigo w3-round-large">Edit</button>
							</td>
							<td>
								<button onclick="location.href='controller?command=${CommandEnum.REMOVE_EMPLOYEE}&employee_id=${employee.id}'" class="w3-button w3-red w3-round-large">Delete</button>
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
					<h5>There are no employees yet!</h5>
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
