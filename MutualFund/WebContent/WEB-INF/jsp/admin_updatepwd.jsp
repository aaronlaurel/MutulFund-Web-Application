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
			<h2 style="margin-top: 0.5em;">Change Password</h2>
			<form method="post" action="${pageContext.request.contextPath}/employee/change_pwd.do">
				<table>
					<tr>
						<td width="30%">Current password: </td>
						<td width="70%"><input type="password" class="text" name="currentpassword" placeholder=" Required"/></td>
					</tr>
					<tr>
						<td>New password: </td>
						<td><input type="password" class="text" name="newpassword" placeholder=" 6 - 16 characters" /></td>
					</tr>
					<tr>
						<td>Reenter new password: </td>
						<td><input type="password" class="text" name="repassword" placeholder=" 6 - 16 characters" /></td>
					</tr>
				</table>
	            <div class="row" style="margin-left: 300px;">
	                <div class="6u">
						<input type="submit" class="button submit" value="Save changes">
	                </div>
	            </div>
			</form>
		</div>
		</section>
	</div>

	<!-- Footer -->
	<div id="login-footer">
		<!-- Copyright -->
		<div class="copyright">
			<p>&copy; 2014 Team One. All rights reserved.</p>
		</div>
	</div>
</body>
</html>
