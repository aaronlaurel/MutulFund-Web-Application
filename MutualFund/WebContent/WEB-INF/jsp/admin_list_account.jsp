<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/admin_left.jsp" />

<!-- Main -->
<div id="main">
	<section id="top" class="one">
		<br />
		<c:forEach var="error" items="${errors}">
            <h3 style="color:red; font-size: 18px;">${error}</h3>
        </c:forEach>
        <h3 style="color:green; font-size: 18px;">${success}</h3>
		<div class="container">
			<h2 style="margin-top: 0.5em;">Manage Customer Account</h2>
			<br/>
			<form method="post" action="${pageContext.request.contextPath}/employee/search.do?type=admin_list_account">
				<input type="text" class="text" name="username" placeholder="Search by Username" />
				<input type="submit" class="button submit" value="Search">
			</form>			
		    
		    <table border="1" id="table">  
		     	<tr>
			 		<th>Username</th>
			 		<th>Name</th>
			 		<th>Operation</th>
			 	</tr>
			 	<c:forEach var="customer" items="${customerList}">
			 	<tr>
			 		<td>${customer.username}</td>
			 		<td>${customer.firstname} ${customer.lastname}</td>			 		
			 		<td>
			 			<a href="${pageContext.request.contextPath}/employee/view_customer_account.do?cid=${customer.customer_id}" ><input type="button" value="Detail Info"></a>
			 			<a href="${pageContext.request.contextPath}/employee/reset_cust_pwd.do?cid=${customer.customer_id}" ><input type="button" value="Reset Password"></a>
			 			<a href="${pageContext.request.contextPath}/employee/view_customer_history.do?cid=${customer.customer_id}" ><input type="button" value="View History"></a>			 			
			 			<a href="${pageContext.request.contextPath}/employee/make_deposit_link.do?cid=${customer.customer_id}" ><input type="button" value="Make Deposit"></a>
			 		</td>
			 	</tr>
				</c:forEach>
			</table>
		</div>
	</section>
</div>

<jsp:include page="/footer.jsp" />