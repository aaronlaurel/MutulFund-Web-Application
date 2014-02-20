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
				<h1 id="title">Welcome, &nbsp;${user.firstname} ${user.lastname}</h1>
			</div>
			<!-- Nav -->
			<nav>
			<ul>				
				<li><a href="${pageContext.request.contextPath}/customer/account.do" id="portfolio-link"
					class="skel-panels-ignoreHref"><span class="fa fa-folder-open-o">View Account</span></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/customer/change_pwd_link.do" id="top-link" class="skel-panels-ignoreHref"><span
						class="fa fa-user">Change Password</span></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/customer/transaction_history.do" id="contact-link"
					class="skel-panels-ignoreHref"><span class="fa fa-calendar">Transaction History</span></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/customer/research_fund.do" id="contact-link"
					class="skel-panels-ignoreHref"><span class="fa fa-search">Research Fund</span></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/customer/research_fund.do" id="about-link"
					class="skel-panels-ignoreHref"><span class="fa fa-suitcase">Buy Fund</span></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/customer/sell_fund_List_link.do" id="contact-link"
					class="skel-panels-ignoreHref"><span class="fa fa-laptop">Sell Fund</span></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/customer/request_check_link.do" id="contact-link"
					class="skel-panels-ignoreHref"><span class="fa fa-dollar">Request Check</span></a>
				</li>				
				<li><a href="${pageContext.request.contextPath}/customer/logout.do"
					class="skel-panels-ignoreHref"><span class="fa fa-exchange">Logout</span></a>
				</li>
			</ul>
			</nav>
		</div>
	</div>
	<!-- Main -->