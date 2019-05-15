<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.kovzan.task_manager.command.CommandEnum" %>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.EmployeeParams" %>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.UtilParams" %>
<html>
<head>
	<title>Employees</title>
	<link rel="stylesheet" href="style/style.css" type="text/css">
</head>
<body class>
<jsp:include page="/WEB-INF/views/common/Header.jsp"></jsp:include>
<c:set var="error" value="${requestScope.get(UtilParams.ERROR)}"></c:set>
<c:set var="employees" value="${requestScope.get(EmployeeParams.PRINTED_EMPLOYEES)}"></c:set>
<div class="container">
	<div class="card">
		<div class="page-header">
			<h2>Employees overview</h2>
			<button class="btn btn-add"
					onclick="location.href='${pageContext.request.contextPath}controller?command=${CommandEnum.PRINT_EDIT_EMPLOYEE}&${UtilParams.IS_ADD_FORM}=true'">
				Add
				employee
			</button>
		</div>

		<c:if test="${error != null}">
			<div class="msg err-msg-list">
				<span onclick="this.parentElement.style.display='none'" class="btn">X
				</span>
				<h5>${error}</h5>
			</div>
		</c:if>

		<c:choose>
			<c:when test="${employees != null && !employees.isEmpty()}">
				<div class="container">
					<table class="content-table">
						<tr>
								<%--<th>Id</th>--%>
							<th style="width: 20%">Last Name</th>
							<th style="width: 20%">First Name</th>
							<th style="width: 20%">Middle Name</th>
							<th style="width: 20%">Position</th>
							<th style="width: 20%" colspan="2"></th>
						</tr>
						<c:forEach items="${employees}" var="employee">
							<tr>
								<input type="hidden" name="${EmployeeParams.EMPLOYEE_ID}"
									   value="${fn:escapeXml(employee.id)}">
									<%--<td>${employee.id}</td>--%>
								<td style="width: 20%">${fn:escapeXml(employee.lastName)}</td>
								<td style="width: 20%">${fn:escapeXml(employee.firstName)}</td>
								<td style="width: 20%">${fn:escapeXml(employee.middleName)}</td>
								<td style="width: 20%">${fn:escapeXml(employee.position)}</td>
								<td>
									<button onclick="location.href='${pageContext.request.contextPath}controller?command=${CommandEnum.PRINT_EDIT_EMPLOYEE}&${EmployeeParams.EMPLOYEE_ID}=${employee.id}&${UtilParams.IS_ADD_FORM}=0'"
											class="btn btn-edit">Edit
									</button>
								</td>
								<td>
									<button onclick="location.href='${pageContext.request.contextPath}controller?command=${CommandEnum.REMOVE_EMPLOYEE}&${EmployeeParams.EMPLOYEE_ID}=${employee.id}'"
											class="btn btn-del">Delete
									</button>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<div class="msg empty-list-msg">
					<h5>There are no employees yet!</h5>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="container footer">
	<button class="btn" onclick="location.href='../../../'">Back to main</button>
</div>
</body>
</html>
