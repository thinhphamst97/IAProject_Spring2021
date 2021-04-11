<%@page import="dao.ClientDAO"%>
<%@page import="dto.ClientDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Client Example</title>
</head>
<body>
	<%
// 		ArrayList<ClientDTO> clientList = (ArrayList<ClientDTO>)request.getAttribute("clientList");
// 		if (clientList != null) {
// 			for (ClientDTO x : clientList) {
// 				out.println(x.getId() + " - " + x.getMac() + " - " + x.isOn() + " - " + x.getCurrentIp() + " - " + x.getCurrentImageName() + "<br>");
// 			}
// 		} else {
// 			response.sendRedirect("MainServlet?action=Client&numOfRequests=8");
// 		}
	%>
</body>
</html>