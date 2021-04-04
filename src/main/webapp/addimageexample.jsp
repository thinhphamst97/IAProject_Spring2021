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
		String result = (String)request.getAttribute("result");
		if(result != null) {
			if (result.equals("true")) {
				out.println("<label style='color:green'>Add image successfully</label>");
			} else {
				out.println("<label style='color:red'>" + result + "</label>");
			}
		} 
	%>
	<!-- Windows -->
	<form method="post" action="AddImageServlet">
		<h4>Windows</h4>
		<label>Image name: </label>
		<input type="text" name="imageName"/> <br>
		<label>Image description: </label>
		<input type="text" name="imageDescription"/> <br>
		<label>BCD path: </label>
		<input type="text" name="bcdPath" /> <br>
		<label>Boot.sdi path: </label>
		<input type="text" name="bootSdiPath" />  <br>
		<label>Boot.wim path: </label>
		<input type="text" name="bootWimPath" /> <input type="submit" value="Add Image" />
		<input type="hidden" name="type" value="windows"/>
	</form>
	<br>---------------------------------------------------------------------<br>
	<!-- Linux -->
	<form method="post" action="AddImageServlet" enctype="multipart/form-data">
		<h4>Linux</h4>
		<label>Image name: </label>
		<input type="text" name="imageName"/> <br>
		<label>Image description: </label>
		<input type="text" name="imageDescription"/> <br>
		<label>Kernel name: </label>
		<input type="text" name="kernelName"/> <br>
		<label>VMDK path: </label>
		<input type="text" name="vmdkPath" /> <input type="submit" value="Add Image" />
		<input type="hidden" name="type" value="linux"/>
	</form>
</body>
</html>