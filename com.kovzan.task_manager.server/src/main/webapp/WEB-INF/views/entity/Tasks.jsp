<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.kovzan.task_manager.command.Commands" %>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.TaskParams" %>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.UtilParams" %>
<html>
<head>
	<title>Tasks</title>
	<link rel="stylesheet" href="style/style.css" type="text/css">
</head>
<body class>
<jsp:include page="/WEB-INF/views/common/Header.jsp"></jsp:include>
<c:set var="tasks" value="${requestScope.get(TaskParams.PRINTED_TASKS)}"></c:set>
<c:set var="error" value="${requestScope.get(UtilParams.ERROR)}"></c:set>
<div class="container">
	<div class="card">
		<div class="page-header">
			<h2>Tasks overview</h2>
			<button class="btn btn-add"
					onclick="location.href='${pageContext.request.contextPath}controller?command=${Commands.PRINT_EDIT_TASK}&${UtilParams.IS_ADD_FORM}=true'">
				Add
				task
			</button>
		</div>

		<c:if test="${error != null}">
			<div class="msg err-msg-list">
				<h5>${error}<span onclick="this.parentElement.style.display='none'" class="btn">X</span></h5>
			</div>
		</c:if>

		<c:choose>
			<c:when test="${tasks != null && !tasks.isEmpty()}">
				<div class="container">
					<table class="content-table">
						<tr>
								<%--<th>Id</th>--%>
							<th style="width:10%">Project</th>
							<th style="width:20%">Name</th>
							<th style="width:15%">Begin Date</th>
							<th style="width:15%">End Date</th>
							<th style="width:20%">Assignee</th>
							<th style="width:20%" colspan="2"></th>
						</tr>
						<c:forEach items="${tasks}" var="task">
							<tr>
								<input type="hidden" name="${TaskParams.TASK_ID}"
									   value="${task.id}">
									<%--<td>${task.id}</td>--%>
								<td style="width:10%">${fn:escapeXml(task.projectShortName)}</td>
								<td style="width:20%">${fn:escapeXml(task.name)}</td>
								<td style="width:15%">${fn:escapeXml(task.beginDate)}</td>
								<td style="width:15%">${fn:escapeXml(task.endDate)}</td>
								<td style="width:20%">${fn:escapeXml(task.employeeFullName)}</td>
								<td>
									<button onclick="location.href='${pageContext.request.contextPath}controller?command=${Commands.PRINT_EDIT_TASK}&${TaskParams.TASK_ID}=${task.id}&${UtilParams.IS_ADD_FORM}=false'"
											class="btn btn-edit">Edit
									</button>
								</td>
								<td>
									<button onclick="location.href='${pageContext.request.contextPath}controller?command=${Commands.REMOVE_TASK}&${TaskParams.TASK_ID}=${task.id}'"
											class="btn btn-del">Delete
									</button>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<div
						class="msg empty-list-msg">
					<h5>There are no tasks yet!</h5>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="container footer">
	<button class="btn btn-back" onclick="location.href='../../../'">Back to main</button>
</div>
</body>
</html>
