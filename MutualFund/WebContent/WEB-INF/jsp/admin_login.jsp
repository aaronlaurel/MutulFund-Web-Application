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
			<h2><b>CFS &nbsp;system - Employee &nbsp;Login</b></h2>
			</header>
			<p>This Web is a Mutual Fund Web Application for customers to
				self-manage their accounts</p>
			<form method="post" action="${pageContext.request.contextPath}/employee/login.do">
				<div class="row half">
					<div class="6u">
						<input type="text" class="text2" name="username"  value="${e.username}" placeholder="Username" />
					</div>
				</div>
				<div class="row half">
					<div class="6u">
						<input type="password" class="text2" name="password" placeholder="Password" />
					</div>
				</div>
				<div class="row" style="margin-left: 405px;">
					<div class="6u">
						<input class="button submit" value="Login">
						</br>
						</br>
						Go to <a href="${pageContext.request.contextPath}/customer/customer.do">[ Customer Login ]</a>
					</div>
				</div>
			</form>
			<p style="color:red; font-size: 18px; height: 5px;"> ${errorInfo} </p>
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