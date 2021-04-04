<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		out.println("Example for Thinh <br>");
		String kernelMd5 = (String)request.getAttribute("kernelMd5");
		String fileSystemMd5 = (String)request.getAttribute("fileSystemMd5");
		ArrayList<String> initrdMd5List = (ArrayList<String>)request.getAttribute("initrdMd5List");
		out.println("kernelMd5: " + kernelMd5 + "<br>");
		out.println("fileSystemMd5: " + fileSystemMd5 + "<br>");
		for (int i = 0; i < initrdMd5List.size(); i++) {
			out.println("initrd " + i + ": " + initrdMd5List.get(i) + "<br>");
		}
	%>
</body>
</html>