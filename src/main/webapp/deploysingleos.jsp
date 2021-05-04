<%-- 
    Document   : index
    Created on : Mar 6, 2021, 12:04:20 AM
    Author     : ThinhPH
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="./assets/logo/favicon.ico" />
<title>Deploy</title>
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
					<li class="nav-item"><a class="nav-link"
						href="MainServlet?action=ImageList"> <i class="material-icons">image</i>
							<p>Image List</p>
					</a></li>
					<li class="nav-item active"><a class="nav-link"
						href="MainServlet?action=Deploy&option=0"> <i
							class="material-icons">upgrade</i>
							<p>Deploy</p>
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="MainServlet?action=Client"> <i class="material-icons">devices</i>
							<p>Clients</p>
					</a></li>
				</ul>
			</div>
		</div>
		<div class="main-panel">
			<!-- Navbar -->
			<nav style="padding-left: 450px; padding-top: 10px">
				<div class="container-fluid">
					<div class="navbar-wrapper">
						<img src="./assets/logo/logo-penguins.png" class="img-fluid"
							alt="Responsive image">
					</div>
				</div>
			</nav>
			<!-- End Navbar -->
			<div class="content">
				<div class="container-fluid">
					<!-- Navigation -->
					<nav class="nav nav-pills" style="background: #eeeeee">
						<li class="nav-item"><a class="nav-link active"
							style="color: white; background: #00bcd4"
							href="MainServlet?action=Deploy&option=0">Single OS</a></li>
						<li class="nav-item"><a class="nav-link"
							style="color: white; background: #3C4858;"
							href="MainServlet?action=Deploy&option=1">Multiple OS</a></li>
						<li class="nav-item"><a class="nav-link"
							style="color: white; background: #3C4858;"
							href="MainServlet?action=Deploy&option=2">Define OS within
								Client's MAC</a></li>
					</nav>
					<!-- Content -->
					<div class="card-body">
						<c:set var="imageList" value="${requestScope.imageList}" />
						<c:set var="kernelPath" value="${requestScope.kernelPath}" />
						<c:set var="initrdFiles" value="${requestScope.initrdPathList}" />
						<c:set var="systemPath" value="${requestScope.fileSystemPath}" />
						<c:set var="selectedImage" value="${param.selectImage}" />
						<c:if test="${not empty requestScope.selectImage }">
							<c:set var="selectedImage" value="${requestScope.selectImage}" />
						</c:if>
						<form action="MainServlet" method="post" style="text-align: center">
							<h4 class="card-title ">Choose an Image:</h4>
							<select name="selectImage" id="selectImage">
								<option value="-1">   --- Choose an Image ----   </option>
								<!--selected ID = -1 -->
								<c:if test="${not empty imageList}">
									<c:forEach var="x" items="${imageList}">
										<c:if test="${x.isActive()}">
											<option value="${x.getId()}"
												${selectedImage eq x.getId() ? 'selected' : ''}>           ${x.getName()}</option>
										</c:if>
									</c:forEach>
								</c:if>
							</select> <input type="hidden" name="option" value="0">
							<button type="submit" class="btn btn-sm btn-outline-light"
								name="action" value="Deploy">Choose</button>
						</form>

						<c:if test="${not empty selectedImage && not empty imageList}">
							<c:forEach var="x" items="${imageList}">
								<c:if test="${x.getId() eq selectedImage}">
									<div id="details">
										<div class="card">
											<div class="card-header card-header-primary"
												style="background: #3C4858">
												<h4 class="card-title">${x.getName()}</h4>
												<p class="card-category">${x.getDescription()}</p>
											</div>
											<div class="card-body">
												<label for="type" style="color: black">Type:</label> <input
													type="text" id="type" class="form-control"
													value="${x.getType()}" disabled=""> <label
													for="date" style="color: black">Created date:</label> <input
													type="text" id="date" class="form-control"
													value="${x.getDateCreated()}" disabled="">
												<c:set var="deployResult" value="${requestScope.result}"></c:set>
												<c:if test="${deployResult eq 'true'}">
													<h4 class="text-success" style="padding-top: 12px">Deploy
														successfully</h4>
												</c:if>
												<c:if test="${deployResult ne 'true'}">
													<!--Kernel Path-->
													<label for="kLocation" style="color: black">Kernel
														location:</label>
													<c:if test="${not empty kernelPath}">
														<input type="text" id="kLocation" class="form-control"
															value="${kernelPath}" disabled="">
													</c:if>
													<c:if test="${empty kernelPath}">
														<input type="text" id="kLocation" class="form-control"
															value="Not found" disabled="">
													</c:if>
													<!--bcd path-->
													<label for="bcdLocation" style="color: black">BCD
														location:</label>
													<c:if test="${not empty initrdFiles}">
														<input type="text" class="form-control"
															value="${initrdFiles.get(0)}" disabled="">
													</c:if>
													<c:if test="${empty initrdFiles}">
														<input type="text" id="bcdLocation" class="form-control"
															value="Not found" disabled="">
													</c:if>
													<!--boot.sdi path-->
													<label for="bootSdiLocation" style="color: black">Boot.sdi
														location:</label>
													<c:if test="${not empty initrdFiles}">
														<input type="text" class="form-control"
															value="${initrdFiles.get(1)}" disabled="">
													</c:if>
													<c:if test="${empty initrdFiles}">
														<input type="text" id="bootSdiLocation"
															class="form-control" value="Not found" disabled="">
													</c:if>
													<!--boot.wim path-->
													<label for="bootWimLocation" style="color: black">File
														system location:</label>
													<c:if test="${not empty systemPath}">
														<input type="text" id="bootWimLocation"
															class="form-control" value="${systemPath}" disabled="">
													</c:if>
													<c:if test="${empty systemPath}">
														<input type="text" id="bootWimLocation"
															class="form-control" value="Not found" disabled="">
													</c:if>
												</c:if>
											</div>
										</div>
									</div>
									<form action="MainServlet" method="post"
										style="text-align: center">
										<input type="hidden" name="option" value="0"> <input
											type="hidden" name="idDeploy" value="${x.getId()}">
										<button type="submit" name="action" value="Deploy"
											class="btn btn-success">Deploy</button>
									</form>
								</c:if>
							</c:forEach>
						</c:if>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>
