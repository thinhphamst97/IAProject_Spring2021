<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String dashboardSupportResult = (String)request.getAttribute("dashboardSupportResult");
	Boolean error = (Boolean)request.getAttribute("error");
	if(dashboardSupportResult != null) {
		if (!error) {
			out.print(dashboardSupportResult);
		}
		else
			out.print("<label style='color:red'>" + dashboardSupportResult + "</label>");
	} else {
		response.sendRedirect("MainServlet?action=DashboardSupport");
	}	
%>