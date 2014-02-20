<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/skel.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/init.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-wide.css" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Carnegie Financial Services Website</title>
</head>
<body>
	<!-- Header -->
	<div id="header" class="skel-panels-fixed">
		<div class="top">
			<!-- Logo -->
			<div id="logo">
				<h1 id="title">Welcome, &nbsp;${admin.firstname} ${admin.lastname}</h1>
			</div>
			<!-- Nav -->
			<nav>
			<ul>				
				<li><a href="${pageContext.request.contextPath}/employee/list_customer_account.do" id="top-link" class="skel-panels-ignoreHref">
					<span class="fa fa-edit">Manage Customer Account</span></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/employee/create_customer_account_link.do" id="contact-link"
					class="skel-panels-ignoreHref"><span class="fa fa-user">Create Customer Account</span></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/employee/create_employee_account_link.do" id="contact-link"
					class="skel-panels-ignoreHref"><span class="fa fa-user">Create Employee Account</span></a>
				</li>								
				<li><a href="${pageContext.request.contextPath}/employee/create_fund_link.do" id="contact-link"
					class="skel-panels-ignoreHref"><span class="fa fa-dollar">Create Fund</span></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/employee/transition_day_link.do" id="about-link"
					class="skel-panels-ignoreHref"><span class="fa fa-calendar">Transition Day</span></a>
				</li>				
				<li><a href="${pageContext.request.contextPath}/employee/change_pwd_link.do" id="top-link" class="skel-panels-ignoreHref">
					<span class="fa fa-user">Change Password</span></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/employee/logout.do"
					class="skel-panels-ignoreHref"><span class="fa fa-exchange">Logout</span></a>
				</li>
			</ul>
			</nav>
		</div>
	</div>
	<!-- Main -->