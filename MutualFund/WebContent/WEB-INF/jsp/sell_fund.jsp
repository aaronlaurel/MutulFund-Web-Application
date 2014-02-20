<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
			<h2 style="margin-top: 0.5em;">Sell Fund</h2>
			<form method="post" action="${pageContext.request.contextPath}/customer/sell_fund_Detail.do?fund_id=${userFundVo.fund_id}">
				<table border="1" id="table">
					<tr>
						<th>Fund Ticker</th>
						<th>Fund Name</th>
						<th>Last Trading Date</th>
						<th>Latest Price</th>
						<th>Shares Owned</th>
						<th>Available Shares</th>
					</tr>					
					<tr>
						<td>${userFundVo.symbol}</td>
						<td>${userFundVo.name}</td>
						<td><fmt:formatDate value="${userFundVo.price_date}" pattern="yyyy-MM-dd"/></td>
						<td style="text-align: right; padding-right: 15px;"><fmt:formatNumber pattern="#,##0.00">${userFundVo.price * 1.0 / 100}</fmt:formatNumber></td>
						<td style="text-align: right; padding-right: 15px;"><fmt:formatNumber pattern="#,##0.000">${userFundVo.shares * 1.0 / 1000}</fmt:formatNumber></td>
						<td style="text-align: right; padding-right: 15px;"><fmt:formatNumber pattern="#,##0.000">${userFundVo.availableShare * 1.0 / 1000}</fmt:formatNumber></td>
					</tr>					
				</table>
				<input type="text" class="text" name="shareNum" placeholder="The  number  of  shares  to  sell" />
		        <input type="submit" class="button submit" value="Sell Fund">
	        </form>
		</div>
	</section>
</div>

<jsp:include page="/footer.jsp" />