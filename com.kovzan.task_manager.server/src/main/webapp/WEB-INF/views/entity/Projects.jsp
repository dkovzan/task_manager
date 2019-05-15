<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.kovzan.task_manager.command.CommandEnum" %>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.ProjectParams" %>
<%@ page import="com.kovzan.task_manager.command.impl.parameters.UtilParams" %>
<html>
<head>
	<title>Projects</title>
	<link rel="stylesheet" href="style/style.css" type="text/css">
</head>
<body class>
<jsp:include page="/WEB-INF/views/common/Header.jsp"></jsp:include>
<c:set var="projects" value="${requestScope.get(ProjectParams.PRINTED_PROJECTS)}"></c:set>
<div class="container">
	<div class="card">
		<div class="page-header">
			<h2>Projects overview</h2>
			<button class="btn btn-add"
					onclick="location.href='${pageContext.request.contextPath}controller?command=${CommandEnum.PRINT_EDIT_PROJECT}&${ProjectParams.PROJECT_ID}=-1&${UtilParams.IS_ADD_FORM}=1&${UtilParams.IS_CLEAN_SESSION_NEEDED}=true'">
				Add
				project
			</button>
		</div>
		<c:choose>
			<c:when test="${projects != null && !projects.isEmpty()}">
				<div class="container">
					<table class="content-table">
						<tr>
								<%--<th>Id</th>--%>
							<th style="width: 25%">Name</th>
							<th style="width: 10%">Short Name</th>
							<th style="width: 40%">Description</th>
							<th style="width: 25%" colspan="2"></th>
						</tr>
						<c:forEach
								items="${projects}"
								var="project">
							<tr>
								<input type="hidden" name="${ProjectParams.PROJECT_ID}"
									   value="${fn:escapeXml(project.id)}">
									<%--<td>${project.id}</td>--%>
								<td style="width: 25%">${fn:escapeXml(project.name)}</td>
								<td style="width: 10%">${fn:escapeXml(project.shortName)}</td>
								<td style="width: 40%">${fn:escapeXml(project.description)}</td>
								<td>
									<button
											onclick="location.href='${pageContext.request.contextPath}controller?command=${CommandEnum.PRINT_EDIT_PROJECT}&${ProjectParams.PROJECT_ID}=${project.id}&${UtilParams.IS_ADD_FORM}=0&${UtilParams.IS_CLEAN_SESSION_NEEDED}=true'"
											class="btn btn-edit">Edit
									</button>
								</td>
								<td>
									<button
											onclick="location.href='${pageContext.request.contextPath}controller?command=${CommandEnum.REMOVE_PROJECT}&${ProjectParams.PROJECT_ID}=${project.id}'"
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
					<h5>There are no projects yet!</h5>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="container footer">
	<button class="btn btn-back" onclick="location.href='../../../'">Back
		to main
	</button>
</div>
</body>
</html>
