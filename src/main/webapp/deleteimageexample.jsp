<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Delete Image Example</title>
</head>
<body>
	<%
		String result = (String)request.getAttribute("deleteImageResult");
		if (result != null) {
			if (result.equalsIgnoreCase("true")) {
				out.println("<label style='color:green'>Delete image successfully</label>");
			} else {
				out.println("<label style='color:red'>" + result + "</label>");
			}
		} else {
			out.println("<label style='color:red'>Wrong</label>");
		}
	%>

</body>
</html>