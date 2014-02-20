<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="/left.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Chart.js"></script>

<!-- Main -->
<div id="main">
    <section id="top" class="one">
        <br />
		<c:forEach var="error" items="${errors}">
            <h3 style="color:red; font-size: 18px;">${error}</h3>
        </c:forEach>
        <h3 style="color:green; font-size: 18px;">${success}</h3>
        
        <div class="container">
	        <div>
		     	<h2 style="margin-top: 0.5em;">${fund.name}</h2>
		        
		        <table border="1" id="table">
	                <tr>                   
	                    <th>Fund Ticker</th>
	                    <th>Fund Name</th>
	                    <th>Latest Price</th>
	                    <th>Last Trading Date</th>
	                </tr>
	                <tr>                  
	                    <td>${fund.symbol}</td>
	                    <td>${fund.name}</td>
	                    <td><c:if test="${(!empty fund.price_date)}"><fmt:formatNumber pattern="#,##0.00">${fund.price * 1.0 / 100}</fmt:formatNumber></c:if></td>
	                    <td><fmt:formatDate value="${fund.price_date}" pattern="yyyy-MM-dd"/></td>
	                </tr>
	            </table>
				<script type="text/javascript" src="https://www.google.com/jsapi"></script>
			    <script type="text/javascript">
			      google.load("visualization", "1", {packages:["corechart"]});
			      google.setOnLoadCallback(drawChart);
			      function drawChart() {
			        var data = google.visualization.arrayToDataTable([
			          ['Year', '${fund.name}']
			        <c:forEach var="priceHistory" items="${priceHistoryList}">
			          ,['${priceHistory.price_date}',  ${priceHistory.price} / 100]
			        </c:forEach>
			        ]);
			
			        var options = {
			          title: '${fund.name} - History Price',
			          pointSize : 10,
			          hAxis: {slantedText : true}
			        };
			
			        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
			        chart.draw(data, options);
			      }
			    </script>
			    <c:choose>
			    	<c:when test="${!empty priceHistoryList}">
			    		<div id="chart_div" style="margin-left: 45px;width: 900px; height: 500px;"></div> 
			    	</c:when>
			    	<c:otherwise>
			    		<div>No price history for this fund!</div> 
			    	</c:otherwise>
			    </c:choose> 
	        </div>  
	        <br /> 
	        <a href="${pageContext.request.contextPath}/customer/buy_fund_link.do?fund_id=${fund.fund_id}" ><input type="submit" class="button submit" value="Buy This Fund"></a>        
        </div>
    </section>
</div>

<jsp:include page="/footer.jsp" />