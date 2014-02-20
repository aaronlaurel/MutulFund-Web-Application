<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
			<h2 style="margin-top: 0.5em;">Transaction History</h2>
			<table border="1" id="table">
				<tr>
					<th>Type</th>
					<th>Transaction Date</th>
					<th>Fund Name</th>
					<th>Shares</th>
					<th>Price</th>
					<th>Amount</th>
				</tr>
				<c:forEach var="historyList" items="${historyList}">
					<tr>
						<td>${historyList.transaction_type}</td>
						<td>
							<c:if test="${historyList.status=='Pending'}">
							${historyList.status}							
							</c:if>
							<fmt:formatDate value="${historyList.transactionDate}" pattern="yyyy-MM-dd" />
						</td>						
						<td>${historyList.fundName}</td>
						<c:choose>
							<c:when test="${historyList.transaction_type=='CHECK'}">
								<td style="text-align: right; padding-right: 30px;"></td>
								<td style="text-align: right; padding-right: 30px;"></td>
								<td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.00">${historyList.amount * 1.0 / 100}</fmt:formatNumber></td>
							</c:when>
							<c:when test="${historyList.transaction_type=='DEPOSIT'}">
								<td style="text-align: right; padding-right: 30px;"></td>
								<td style="text-align: right; padding-right: 30px;"></td>
								<td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.00">${historyList.amount * 1.0 / 100}</fmt:formatNumber></td>
							</c:when>
							<c:when test="${historyList.transaction_type=='BUY'}">
								<c:choose>
									<c:when test="${historyList.status=='Pending'}">
										<td style="text-align: right; padding-right: 30px;"></td>
										<td style="text-align: right; padding-right: 30px;"></td>
										<td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.00">${historyList.amount * 1.0 / 100}</fmt:formatNumber></td>
									</c:when>
									<c:when test="${historyList.status=='Completed'}">
										<td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.000">${historyList.shares * 1.0 / 1000}</fmt:formatNumber></td>
										<td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.00">${historyList.price * 1.0 / 100}</fmt:formatNumber></td>
										<td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.00">${historyList.amount * 1.0 / 100}</fmt:formatNumber></td>
									</c:when>
								</c:choose>
							</c:when>
							<c:when test="${historyList.transaction_type=='SELL'}">
                                <c:choose>
                                    <c:when test="${historyList.status=='Pending'}">
                                        <td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.000">${historyList.shares * 1.0 / 1000}</fmt:formatNumber></td>
                                        <td style="text-align: right; padding-right: 30px;"></td>
                                        <td style="text-align: right; padding-right: 30px;"></td>
                                    </c:when>
                                    <c:when test="${historyList.status=='Completed'}">
                                        <td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.000">${historyList.shares * 1.0 / 1000}</fmt:formatNumber></td>
                                        <td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.00">${historyList.price * 1.0 / 100}</fmt:formatNumber></td>
                                        <td style="text-align: right; padding-right: 30px;"><fmt:formatNumber pattern="#,##0.00">${historyList.amount * 1.0 / 100}</fmt:formatNumber></td>
                                    </c:when>
                                </c:choose>
                            </c:when>
						</c:choose>
					</tr>
				</c:forEach>
			</table>
		</div>
	</section>
</div>

<jsp:include page="/footer.jsp" />