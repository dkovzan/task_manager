<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>Error</title>
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<jsp:include page="/WEB-INF/views/common/Header.jsp"></jsp:include>
<div class="w3-container w3-center w3-margin-bottom w3-padding">
	<div class="w3-container w3-light-red">
		<h2>Error page</h2>
	</div>
	<div class="w3-panel w3-pale-red">
		<p>${error}</p>
		<p><xmp>${pageContext.exception}</xmp></p>
	</div>
</div>
</body>
</html>
