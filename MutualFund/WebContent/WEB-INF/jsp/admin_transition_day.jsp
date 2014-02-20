<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <h2 style="margin-top: 0.5em;">Transition Day</h2>
          	<form method="post" action="${pageContext.request.contextPath}/employee/transition_day.do">
	            <!-- input the transition date -->
	            Last Trading Date: &nbsp;&nbsp;&nbsp;<fmt:formatDate value="${ltd}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            
	            Transition Date: &nbsp;&nbsp;&nbsp;<input type="date" class="text" name="date" placeholder="yyyy-MM-dd" 
	            min="<fmt:formatDate value="${startDay}" pattern="yyyy-MM-dd"/>" style="width: 250px;"/>
	            
	            <c:if test="${empty funds}">
	            	<br /><br />
	            	<p style="color: red;">No Fund in System now</p>
	            </c:if>
	            
	            <c:if test="${not empty funds}">
		            <table border="1" id="table">
		                <tr>
		                	<th>Fund Ticker</th>
		                    <th>Fund Name</th>	                    
		                    <th>Price</th>
		                </tr>
		              <c:forEach var="fund" items="${funds}">
		              	<input type="hidden" name="fund_id" value="${fund.fund_id}">
		              	<input type="hidden" name="fund_name" value="${fund.name}">
		                <tr>	                    
		                    <td>${fund.symbol}</td>
		                    <td>${fund.name}</td>
		                    <td>
		                    	<input type="text" class="text" style="text-align: right;" name="price" value="<c:if test="${(!empty fund.price_date)}"><fmt:formatNumber pattern="#0.00">${fund.price * 1.0 / 100}</fmt:formatNumber></c:if>">
		                    </td>
		                </tr>
		              </c:forEach>
		            </table>
		            <input type="submit" class="button submit" value="Submit">
	            </c:if>
            </form>
        </div>
    </section>
</div>

<jsp:include page="/footer.jsp" />