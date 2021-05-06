<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="./assets/logo/favicon.ico" />
<title>Deploy with MAC address</title>
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

					<!-- Navigation -->
					<nav class="nav nav-pills" style="background: #eeeeee">
						<li class="nav-item"><a class="nav-link"
							style="color: white; background: #3C4858;"
							href="MainServlet?action=Deploy&option=0">Single OS</a></li>
						<li class="nav-item"><a class="nav-link active"
							style="color: white; background: #3C4858"
							href="MainServlet?action=Deploy&option=1">Multiple OS</a></li>
						<li class="nav-item"><a class="nav-link"
							style="color: white; background: #00bcd4;"
							href="MainServlet?action=Deploy&option=2">Deploy with MAC
								address</a></li>
					</nav>

					<!-- Content -->

					<div class="col-md 12">
						<form action="MainServlet" method="post">
							<div class="card">
								<div class="card-header card-header-primary"
									style="background: #3C4858">
									<h4 class="card-title">MAC table</h4>
									<p class="card-category">Select the appropriate image for
										each client machine based on the MAC address</p>
								</div>
								<div class="card-body" style="text-align: center">
									<table class="table">
										<thead>
											<th style="font-weight: bold;">Name</th>
											<th style="font-weight: bold;">MAC</th>
											<th style="font-weight: bold;">Image</th>
										</thead>
										<tbody>
											<c:set var="macList"
												value="${applicationScope.macDeployClientList}" />
											<c:set var="imageList"
												value="${requestScope.activeImageList}" />
											<c:if test="${not empty macList}">
												<c:forEach var="x" items="${macList}" varStatus="count">
													<tr>
														<td style="witdh: 60%">${x.getName()}</td>
														<td style="witdh: 20%">${x.getMac()}</td>
														<td style="witdh: 20%"><select name="deployImageId"
															id="selectImage">
																<option value="-1">���--- Choose an Image
																	----���</option>
																<!--selected ID = -1 -->
																<c:if test="${not empty imageList}">
																	<c:forEach var="i" items="${imageList}">
																		<c:if test="${i.isActive()}">
																			<option value="${i.getId()}"
																				${i.getId() eq x.getMacDeployImage().getId() ? 'selected' : ''}>�����������${i.getName()}</option>
																		</c:if>
																	</c:forEach>
																</c:if>
														</select></td>
													</tr>
												</c:forEach>
											</c:if>

										</tbody>
									</table>

									<!-- Deploy result -->
									<c:set var="deployResult" value="${requestScope.result}"></c:set>
									<c:if test="${deployResult eq 'true'}">
										<h4 class="text-success">Deploy successfully</h4>
									</c:if>
								</div>
							</div>

							<!-- Deploy MAC Button -->
							<div style="text-align: center">
								<input type="hidden" name="option" value="2">
								<button type="submit" name="action" value="Deploy"
									class="btn btn-success">Deploy</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>