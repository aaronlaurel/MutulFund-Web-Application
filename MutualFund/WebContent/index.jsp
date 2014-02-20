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
<title>Carnegie Financial Services Website</title>
</head>
<body>
	<!-- Main -->
	<div id="login">
		<section id="contact" class="one">
		<div class="container">
			<header>
			<h2><b>Welcome &nbsp;to &nbsp;CFS &nbsp;system</b></h2>
			</header>
			<p>This Web is a Mutual Fund Web Application for customers to
				self-manage their accounts</p>
							
			<div class="row" style="margin-left: 405px;">
				<div class="6u">
					<a href="${pageContext.request.contextPath}/customer/customer.do"><input class="button" value="Customer"></a>
				</div>
			</div>
			<br />
			<div class="row" style="margin-left: 405px;">
				<div class="6u">
					<a href="${pageContext.request.contextPath}/employee/admin.do"><input class="button" value="Employee"></a>
				</div>
			</div>
			<br />
			<br />
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