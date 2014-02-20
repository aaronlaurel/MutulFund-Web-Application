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
			<h2 style="margin-top: 0.5em;">Create Customer Account</h2>	
			<form method="post" action="${pageContext.request.contextPath}/employee/create_customer_account.do">
				<table>
					<tr>
						<td width="20%">User Name: </td>
						<td width="80%"><input type="text" class="text" name="username" value="${cmr.username}" placeholder=" Required" /></td>
					</tr>
					<tr>
						<td width="20%">Password: </td>
						<td width="80%"><input type="password" class="text" name="password" placeholder=" 6 - 16 characters" /></td>
					</tr>
					<tr>
						<td width="20%">Reenter Password: </td>
						<td width="80%"><input type="password" class="text" name="password2" placeholder=" 6 - 16 characters" /></td>
					</tr>
					<tr>
						<td width="20%">First Name: </td>
						<td width="80%"><input type="text" class="text" name="firstname" value="${cmr.firstname}" placeholder=" Required" /></td>
					</tr>
					<tr>
						<td width="20%">Last Name: </td>
						<td width="80%"><input type="text" class="text" name="lastname" value="${cmr.lastname}" placeholder=" Required" /></td>
					</tr>
					<tr>
						<td width="20%">Address Line 1: </td>
						<td width="80%"><input type="text" class="text" name="addr_line1" value="${cmr.addr_line1}"/></td>
					</tr>
					<tr>
						<td width="20%">Address Line 2: </td>
						<td width="80%"><input type="text" class="text" name="addr_line2" value="${cmr.addr_line2}"/></td>
					</tr>
					<tr>
						<td width="20%">City: </td>
						<td width="80%"><input type="text" class="text" name="city" value="${cmr.city}"/></td>
					</tr>
					<tr>
						<td width="20%">State: </td>
						<td width="80%"><input type="text" class="text" name="state" value="${cmr.state}"/></td>
					</tr>
					<tr>
						<td width="20%">Zip Code: </td>
						<td width="80%"><input type="text" class="text" name="zip" value="${cmr.zip}"/></td>
					</tr>
				</table>
				
	            <div class="row" style="margin-left: 300px;">
	                <div class="6u">
	                    <input type="submit" class="button submit" value="Confirm Create">
	                </div>
	            </div>
	       </form>       
    	</div>
	</section>
</div>

<jsp:include page="/footer.jsp" />