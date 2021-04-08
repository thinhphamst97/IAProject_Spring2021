<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Client Example</title>
</head>
<body>
	<%
		String result = (String)request.getAttribute("result");
		if(result != null) {
			if (result.equals("true")) {
				out.println("<label style='color:green'>Add client successfully</label>");
			} else {
				out.println("<label style='color:red'>" + result + "</label>");
			}
		} 
	%>
	<!-- Windows -->
	<form method="post" action="AddClientServlet">
		<h4>Add new client</h4>
		<label>MAC address: </label>
		<input type="text" name="mac" placeholder="xx:xx:xx:xx:xx:xx"/> <input type="submit" value="Add Client" />
	</form>
</body>
</html>