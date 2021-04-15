<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Delete Client Example</title>
</head>
<body>
	<%
		String deleteClientResult = (String)request.getAttribute("deleteClientResult");
		if(deleteClientResult != null) {
			if (deleteClientResult.equals("true")) {
				out.println("<label style='color:green'>Delete client successfully</label>");
			} else {
				out.println("<label style='color:red'>" + deleteClientResult + "</label>");
			}
		} 
	%>
	<!-- Windows -->
	<form method="post" action="DeleteClientServlet">
		<h4>Delete client</h4>
		<label>Client id: </label>
		<input type="text" name="clientId" /> <br>
		<input type="submit" value="Remove" />
	</form>
</body>
</html>