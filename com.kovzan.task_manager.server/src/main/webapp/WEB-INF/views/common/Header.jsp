<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.kovzan.task_manager.command.Commands"%>
<html>
<head>
<title>Task Manager</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
	<!-- header -->
	<div class="w3-container w3-blue-grey w3-opacity w3-left-align">
		<h1>Task Manager</h1>
	</div>
	<div class="w3-bar w3-border w3-light-grey w3-xlarge">
		<button
			onclick="location.href='${pageContext.request.contextPath}controller?command=${Commands.PRINT_PROJECTS}'"
			class="w3-bar-item w3-button w3-hover-teal">Projects</button>
		<button
			onclick="location.href='${pageContext.request.contextPath}controller?command=${Commands.PRINT_TASKS}'"
			class="w3-bar-item w3-button w3-hover-teal">Tasks</button>
		<button
			onclick="location.href='${pageContext.request.contextPath}controller?command=${Commands.PRINT_EMPLOYEES}'"
			class="w3-bar-item w3-button w3-hover-teal">Employees</button>
	</div>
</body>
</html>
