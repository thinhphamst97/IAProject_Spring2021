<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- Windows -->
	<form method="post" action="AddImageServlet" enctype="multipart/form-data">
		<h4>Windows</h4>
		<label>Image name: </label>
		<input type="text" name="imageName"/> <br>
		<label>Wimboot: </label>
		<input type="file" name="wimboot" /> <br>
		<label>BCD: </label>
		<input type="file" name="bcd" /> <br>
		<label>Boot.sdi: </label>
		<input type="file" name="bootSdi" />  <br>
		<label>Boot.wim: </label>
		<input type="file" name="bootWim" /> <input type="submit" value="Upload" />
		<input type="hidden" name="type" value="windows"/>
	</form>
	<br>---------------------------------------------------------------------<br>
	<!-- Linux -->
	<form method="post" action="AddImageServlet" enctype="multipart/form-data">
		<h4>Linux</h4>
		<label>Image name: </label>
		<input type="text" name="imageName"/> <br>
		<label>VMDK: </label>
		<input type="file" name="vmdk" /> <input type="submit" value="Upload" />
		<input type="hidden" name="type" value="linux"/>
	</form>
</body>
</html>