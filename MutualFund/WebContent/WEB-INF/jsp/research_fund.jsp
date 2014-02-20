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
            <h2 style="margin-top: 0.5em;">Fund List</h2>
            <form method="post" action="${pageContext.request.contextPath}/customer/search.do?">
				<input type="text" class="text" name="value" placeholder="Search by Fund Name or Ticker" />
				<input type="submit" class="button submit" value="Search">
			</form>
            <table border="1" id="table">
                <tr>                   
                    <th>Fund Ticker</th>
                    <th>Fund Name</th>
                    <th>Latest Price</th>
                    <th>Last Trading Date</th>
                    <th>Operation</th>
                </tr>
              <c:forEach var="fund" items="${funds}">
                <tr>                  
                    <td>${fund.symbol}</td>
                    <td>${fund.name}</td>
                    <td style="text-align: right; padding-right: 30px;"><c:if test="${(!empty fund.price_date)}"><fmt:formatNumber pattern="#,##0.00">${fund.price * 1.0 / 100}</fmt:formatNumber></c:if></td>
                    <td><fmt:formatDate value="${fund.price_date}" pattern="yyyy-MM-dd"/></td>
                    <td>
                    	<a href="${pageContext.request.contextPath}/customer/buy_fund_link.do?fund_id=${fund.fund_id}" ><input type="button" value="Buy"></a> &nbsp;&nbsp;&nbsp;
                    	<a href="${pageContext.request.contextPath}/customer/fund_detail_link.do?fund_id=${fund.fund_id}" ><input type="button" value="Detail"></a>
                    </td>
                </tr>
              </c:forEach>
            </table>
        </div>
    </section>
</div>

<jsp:include page="/footer.jsp" />