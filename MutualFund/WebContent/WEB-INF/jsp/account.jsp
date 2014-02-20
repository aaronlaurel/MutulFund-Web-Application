<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<h2 style="margin-top: 0.5em;">Detailed Customer Account</h2>

		    <table border="1" id="table">
		       	<th colspan="2"">Customer Information</th>
		    	<tr>
		          <th width="30%">Username</th>
		          <td>${user.username}</td>
		       </tr>
		       <tr>
		          <th>Name</th>
		          <td>${user.firstname} ${user.lastname}</td>
		       </tr>
		       <tr>
		          <th>Address</th>
		          <td>${user.addr_line1}, ${user.addr_line2}, ${user.city}, ${user.state}, ${user.zip}</td>
		       </tr>
		       <tr>
		          <th>Last Trading Date</th>
		          <td><fmt:formatDate value="${ltd}" pattern="yyyy-MM-dd"/></td>
		       </tr>
		       <tr>
		          <th>Cash Balance</th>
		          <td><fmt:formatNumber pattern="#,##0.00">${user.cash * 1.0 / 100}</fmt:formatNumber></td>
		       </tr>
		       <tr>
		          <th>Available Cash</th>
		          <td><fmt:formatNumber pattern="#,##0.00">${user.availableCash * 1.0 / 100}</fmt:formatNumber></td>
		       </tr>
		       <tr>
		          <td colspan="2" style="text-align: left;">
		          <span>[ Note ] Available Cash = Cash Balance - Pending Purchases of Funds - Request Check + Deposit Check</br>
		          The values from Pending Sales of Funds will be included after the transition has been processed.</span></td>
		       </tr>		       
		    </table>
		    		    
		    <table border="1" id="table">  
		     	<tr>
		     		<th>Fund Ticker</th>
			 		<th>Fund Name</th>
			 		<th>Shares Owned</th>
			 		<th>Latest Price</th>
			 		<th>Value</th>
			 		<th>Operation</th>			 		
			 	</tr>
			 	<c:forEach var="userFund" items="${userFundVoList}">
			 	<tr>
			 		<td>${userFund.symbol}</td>
			 		<td>${userFund.name}</td>
			 		<td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.000">${userFund.shares * 1.0 / 1000}</fmt:formatNumber></td>
			 		<td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.00">${userFund.price * 1.0 / 100}</fmt:formatNumber></td>
			 		<td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.00">${1.0 * userFund.shares / 100000 * userFund.price}</fmt:formatNumber></td>
			 		<td>
			 			<a href="${pageContext.request.contextPath}/customer/buy_fund_link.do?fund_id=${userFund.fund_id}" ><input type="button" value="Buy"></a> &nbsp;&nbsp;&nbsp;
                    	<a href="${pageContext.request.contextPath}/customer/sell_fund_Detail_link.do?fund_id=${userFund.fund_id}" ><input type="button" value="Sell"></a>
			 		</td>
			 	</tr>
				</c:forEach>
			</table>
		</div>
	</section>
</div>

<jsp:include page="/footer.jsp" />