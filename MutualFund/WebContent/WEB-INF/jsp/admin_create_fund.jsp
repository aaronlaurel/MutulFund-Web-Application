<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        	<h2 style="margin-top: 0.5em;">Create Fund</h2>	
            <form method="post" action="${pageContext.request.contextPath}/employee/create_fund.do">
            	<table>
					<tr>
						<td width="20%">Fund Name: </td>
						<td width="80%"><input type="text" class="text" name="name"  value="${fd.name}" placeholder=" Required" /></td>
					</tr>
					<tr>
						<td width="20%">Fund Ticker: </td>
						<td width="80%"><input type="text" class="text" name="symbol" value="${fd.symbol}" placeholder=" 1 - 5 characters" /></td>
					</tr>
				</table>
	            <div class="row" style="margin-left: 300px;">
	                <div class="6u">
	                    <input type="submit" class="button submit" value="Create Fund">
	                </div>
	            </div>
        	</form>           
        </div>
    </section>
</div>

<jsp:include page="/footer.jsp" />