<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/skel.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/init.js"></script>
<link rel="stylesheet" type="text/css"
    href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-wide.css" />
<title>Error Page</title>
</head>
<body>
	<!-- Main -->
	<div id="login">
		<section id="contact" class="one">
		<div class="container">
			<h2>Invalid &nbsp;Request &nbsp;or &nbsp;Concurrency &nbsp;Problem</h2>
			<br/>
			<a href="${pageContext.request.contextPath}/index.jsp">[ Back to Home Page ]</a>
			<br/>
			<br/>
			<img src="${pageContext.request.contextPath}/img/failure.jpg" width="648px" height="488px">
		</div>
		</section>
	</div>
<!-- Footer -->
	<div id="login-footer">
		<!-- Copyright -->
		<div class="copyright">
			<p>&copy; 2014 Team One. All rights reserved.</p>
		</div>
	</div>
</body>
</html>