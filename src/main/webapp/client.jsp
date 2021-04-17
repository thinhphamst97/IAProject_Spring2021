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
<title>Clients</title>
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
					<li class="nav-item"><a class="nav-link"
						href="MainServlet?action=Deploy&option=0"> <i
							class="material-icons">upgrade</i>
							<p>Deploy</p>
					</a></li>
					<li class="nav-item active"><a class="nav-link"
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
					<!-- your content here -->
					<div class="col-md 12">
						<div class="card">
							<div class="card-header card-header-primary"
								style="background: #3C4858">
								<h4 class="card-title">Client list</h4>
								<p class="card-category">List all client in our lab</p>
							</div>
							<div class="card-body" style="text-align: center">
								<c:set var="listClient" value="${requestScope.clientList}" />
								<table class="table">
									<thead>
										<th style="font-weight: bold;">Name</th>
										<th style="font-weight: bold;">IP</th>
										<th style="font-weight: bold;">MAC</th>
										<th style="font-weight: bold;">Image</th>
										<th style="font-weight: bold;">Status</th>
										<th style="font-weight: bold;">Action</th>
									</thead>
									<tbody>
										<c:if test="${not empty listClient}">
											<c:forEach items="${listClient}" var="x" varStatus="status">
												<tr>
													<td style="width: 5%">PC ${status.index+1}</td>
													<td style="width: 20%">${x.getCurrentIp()}</td>
													<td style="width: 20%">${x.getMac()}</td>
													<td style="width: 15%">${x.getCurrentImage().getName()}</td>
													<c:if test="${x.isOn() eq true}">
														<td style="width: 15%; color: green; font-weight: bold;">Online</td>
													</c:if>
													<c:if test="${x.isOn() eq false}">
														<td style="width: 15%; color: red">Offline</td>
													</c:if>
													<td style="width: 20%;">
														<div class="row">
															<form action="MainServlet" method="post">
																<input type="hidden" name="id" value="${x.getId()}">
																<input type="hidden" name="shutdownClient" value="true">
																<button class="btn btn-sm btn-danger" name="action"
																	value="ShutdownClient">Shutdown</button>
															</form>
															<form action="MainServlet" method="post">
																<input type="hidden" name="id" value="${x.getId()}">
																<input type="hidden" name="restartClient" value="true">
																<button class="btn btn-sm btn-success" name="action"
																	value="RestartClient">Restart</button>
															</form>
															<form action="MainServlet" method="post">
																<input type="hidden" name="id" value="${x.getId()}">
																<input type="hidden" name="removeClient" value="true">
																<button class="btn btn-sm btn-warning" name="action"
																	value="DeleteClient">Remove</button>
															</form>
														</div>
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
								<a href="addclient.jsp"><button type="button"
										class="btn btn-info pull-right">Add new Client</button></a>
								<form action="MainServlet" method="post">
									<input type="hidden" name="restartAll" value="true">
									<button type="button" class="btn btn-success pull-right"
										name="action" value="RestartClient">Restart All</button>
								</form>
								<form action="MainServlet" method="post">
									<input type="hidden" name="shutdownAll" value="true">
									<button type="button" class="btn btn-danger pull-right"
										name="action" value="ShutdownClient">Shutdown All</button>
								</form>


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
