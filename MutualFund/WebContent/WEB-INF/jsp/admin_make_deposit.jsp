<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" pageEncoding="UTF-8"%>
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
			<h2 style="margin-top: 0.5em;">Deposit Check</h2>
			<form method="post" action="${pageContext.request.contextPath}/employee/make_deposit.do?cid=${u.customer_id}">
				<table border="1" id="table">
					<tr>
						<th>Username</th>
						<th>Name</th>
						<th>Cash Balance</th>
						<th>Available Balance</th>
					</tr>
					<tr>
						<td>${u.username}</td>
						<td>${u.firstname} ${u.lastname}</td>
						<td><fmt:formatNumber pattern="#,##0.00">${u.cash * 1.0 / 100}</fmt:formatNumber></td>
						<td><fmt:formatNumber pattern="#,##0.00">${u.availableCash * 1.0 / 100}</fmt:formatNumber></td>
					</tr>
				</table>
				<input type="text" class="text" name="amount" placeholder="The  dollar  amount  to  deposit" />
				<input type="submit" class="button submit" value="Make Deposit">
			</form>
		</div>
	</section>
</div>

<jsp:include page="/footer.jsp" />