<%-- 
    Document   : windows10
    Created on : Mar 6, 2021, 2:16:06 PM
    Author     : ThinhPH
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="./assets/logo/favicon.ico" />
<title>Add Image</title>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<!--     Fonts and icons     -->
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
<!-- Material Kit CSS -->
<link href="assets/css/material-dashboard.css" rel="stylesheet" />
</head>
<body>
	<div class="wrapper ">
		<div class="sidebar" data-color="azure" data-background-color="black">
			<!--
                Tip 1: You can change the color of the sidebar using: data-color="purple | azure | green | orange | danger"
          
                Tip 2: you can also add an image using data-image tag
                -->
			<div class="logo" ata-background-color="black">
				<img src="./assets/logo/fptu.png" class="img-fluid"
					alt="Responsive image">
			</div>
			<div class="sidebar-wrapper">
				<ul class="nav">
					<li class="nav-item"><a class="nav-link"
						href="MainServlet?action=Dashboard"> <i class="material-icons">dashboard</i>
							<p>Dashboard</p>
					</a></li>
					<!-- your sidebar here -->
					<li class="nav-item active"><a class="nav-link"
						href="MainServlet?action=ImageList"> <i class="material-icons">image</i>
							<p>Image List</p>
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="MainServlet?action=Deploy&option=0"> <i
							class="material-icons">upgrade</i>
							<p>Deploy</p>
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="MainServlet?action=Client"> <i class="material-icons">devices</i>
							<p>Clients</p>
					</a></li>
			</div>
		</div>
		<div class="main-panel">
		<nav style="padding-left: 450px; padding-top: 10px">
				<div class="container-fluid">
					<div class="navbar-wrapper">
						<img src="./assets/logo/logo-penguins.png" class="img-fluid"
							alt="Responsive image">
					</div>
				</div>
			</nav>
			<div class="content">
				<div class="container-fluid">
					<div class="card">
						<c:set var="selectedImage" value="${param.selectedImage}"></c:set>
						<div class="card-header card-header-primary" style="background: #3C4858">
							<form action="addimage.jsp" method="post">
								<h4 class="card-title ">Choose type of image:</h4>
								<select name="selectedImage" id="selectedImage">
									<c:if test="${not empty selectedImage}">
										<option value="">   --- Choose type of image ----   </option>
										<option value="windows"
											${selectedImage eq 'windows' ? 'selected' : ''}>Windows</option>
										<option value="linux"
											${selectedImage eq 'linux' ? 'selected' : ''}>Linux</option>
									</c:if>
									<c:if test="${empty selectedImage}">
										<option value="">   --- Choose type of image ----   </option>
										<option value="windows">Windows</option>
										<option value="linux">Linux</option>
									</c:if>
								</select>
								<button type="submit" class="btn btn-sm btn-outline-light"
									value="submit">OK</button>
							</form>
						</div>
						<div class="card-body">
							<c:set var="selectedImage" value="${param.selectedImage}" />
							<c:set var="result" value="${requestScope.result}" />
							<c:if test="${not empty selectedImage}">
								<c:if test="${selectedImage eq 'windows'}">
									<form action="MainServlet" method="post"
										class="justify-content-center" style="text-align: center">
										<div style="padding-left: 400px; padding-right: 400px">
											<!--Windows name-->
											<div>
												<label for="imageName" style="color: black">Image
													Name:</label> <input type="text" id="imageName" name="imageName"
													class="form-control">
											</div>
											<!--Descriptions-->
											<div>
												<label for="imageDescription" style="color: black">Description:
												</label>
												<textarea id="imageDescription" name="imageDescription"
													rows="4" cols="30" class="form-control"></textarea>
											</div>
											<!--BCD file-->
											<div style="padding-bottom: 10px">
												<label for="bcdPath" style="color: black">BCD File:</label>
												<input type="text" id="bcdPath" name="bcdPath"
													class="form-control">
											</div>
											<!--Boot.sdi file-->
											<div style="padding-bottom: 10px">
												<label for="bootSdiPath" style="color: black">Boot.sdi
													File:</label> <input type="text" id="bootSdiPath"
													name="bootSdiPath" class="form-control">
											</div>
											<!--Boot.wim file-->
											<div style="padding-bottom: 10px">
												<label for="bootWimPath" style="color: black">Boot.wim
													File:</label> <input type="text" id="bootWimPath"
													name="bootWimPath" class="form-control">
											</div>
											<!--Forward all text fields to Mainservlet with aciton = Image-->
											<input type="hidden" name="type" value="windows" /> <input
												type="hidden" name="selectedImage" value="${selectedImage}">
											<button type="submit" name="action" value="AddImage"
												class="btn btn-primary" style="background: #00bcd4">Add Image</button>
										</div>
									</form>
								</c:if>
								<c:if test="${selectedImage eq 'linux'}">
									<form action="MainServlet" method="post"
										class="justify-content-center" style="text-align: center">
										<div style="padding-left: 400px; padding-right: 400px">
											<!--Linux name-->
											<div>
												<label for="imageName" style="color: black">OS Name:</label>
												<input type="text" id="imageName" name="imageName"
													class="form-control">
											</div>
											<!--Descriptions-->
											<div>
												<label for="imageDescription" style="color: black">Description:
												</label>
												<textarea id="imageDescription" name="imageDescription"
													rows="4" cols="30" class="form-control"></textarea>
											</div>
											<!--VMDK file-->
											<div style="padding-bottom: 10px">
												<label for="kernelFile" style="color: black"
													class="form-label">Kernel name:</label> <input type="text"
													id="kernelFile" name="kernelName" class="form-control">
											</div>
											<!--VMDK file-->
											<div style="padding-bottom: 10px">
												<label for="vmdkPath" style="color: black"
													class="form-label">VMDK File:</label> <input type="text"
													id="vmdkPath" name="vmdkPath" class="form-control">
											</div>
											<!--Forward all text fields to Mainservlet with aciton = Image-->
											<input type="hidden" name="type" value="linux" /> <input
												type="hidden" name="selectedImage" value="${selectedImage}">
											<button type="submit" name="action" value="AddImage"
												class="btn btn-primary" style="background: #00bcd4">Add Image</button>
										</div>
									</form>
								</c:if>
							</c:if>
							<c:if test="${not empty result && result eq 'true'}">
								<p class="text-success">Add image successfully</p>
							</c:if>
							<c:if test="${not empty result && result ne 'true'}">
								<p class="text-danger">${result}</p>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		<footer class="footer">
			<div class="container-fluid">
				<nav class="float-left">
					<ul>
						<li><a href="https://www.creative-tim.com"> ThinhPH </a></li>
					</ul>
				</nav>
				<div class="copyright float-right">
					&copy;
					<script>
						document.write(new Date().getFullYear())
					</script>
					, made with <i class="material-icons">favorite</i> by GPS21IA01.
				</div>
				<!-- your footer here -->
			</div>
		</footer>
	</div>
	</div>
</body>
</html>
