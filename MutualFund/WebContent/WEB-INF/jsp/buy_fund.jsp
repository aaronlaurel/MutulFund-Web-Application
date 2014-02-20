<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="/left.jsp" />

<!-- Main -->
<div id="main">
    <section id="top" class="one">
        <br />
		<c:forEach var="error" items="${errors}">
            <h3 style="color:red; font-size: 18px;">${error}</h3>
        </c:forEach>
        <h3 style="color:green; font-size: 18px;">${success}</h3>
        
        <div class="container">
           	<h2 style="margin-top: 0.5em;">Buy Fund</h2>
           	Cash Balance: <fmt:formatNumber pattern="#,##0.00">${user.cash * 1.0 / 100}</fmt:formatNumber> 
           	&nbsp;&nbsp;&nbsp;&nbsp;Available Cash: <fmt:formatNumber pattern="#,##0.00">${user.availableCash * 1.0 / 100}</fmt:formatNumber>
        	<form method="post" action="${pageContext.request.contextPath}/customer/buy_fund.do">		
	            <input type="hidden" name="fund_id" value="${fund.fund_id}" />
				<table border="1" id="table">
					<tr>					
						<th>Fund Ticker</th>
						<th>Fund Name</th>
						<th>Last Trading Date</th>
						<th>Latest Price</th>
					</tr>
					<tr>
						<td>${fund.symbol}</td>
						<td>${fund.name}</td>
						<td><fmt:formatDate value="${fund.price_date}" pattern="yyyy-MM-dd"/></td>
						<td><c:if test="${(!empty fund.price_date)}"><fmt:formatNumber pattern="#,##0.00">${fund.price * 1.0 / 100}</fmt:formatNumber></c:if></td>						
					</tr>
				</table>
	            <input type="text" class="text" name="amount" placeholder="The  dollar  amount  to  buy" />
	            <input type="submit" class="button submit" value="Buy Fund">
	        </form>               
        </div>
    </section>
</div>

<jsp:include page="/footer.jsp" />