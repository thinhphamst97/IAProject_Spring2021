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
<link rel="shortcut icon" type="image/x-icon" href="./assets/logo/favicon.ico" />
<title>Add Client</title>
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
<link href="assets/css/material-dashboard.css?v=2.1.2" rel="stylesheet" />
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
			<div class="content">
				<div class="container-fluid">
					<div class="col-md 12">
						<div class="card">
							<div class="card-header card-header-primary"
								style="background: #3C4858">
								<h4 class="card-title">Add Client</h4>
								<p class="card-category">Please input these information to
									add new client</p>
							</div>
							<div class="card-body">
								<form action="MainServlet" method="post" style="padding-bottom: 10px">
									<div class="row">
										<div class="col-md-2">
											<label for="request" style="color: black">MAC: </label> <input
												type="text" id="request" name="mac"
												class="form-control" placeholder="xx:xx:xx:xx:xx:xx">
										</div>
										<button type="submit" class="btn btn-sm btn-info"
											style="margin-top: 30px" name="action" value="AddClient">Add
											new client</button>
									</div>
								</form>
								<c:set var="result" value="${requestScope.result}"></c:set>
								<c:if test="${not empty result && result eq 'true'}">
									<p style="color: green">Client was added successfully</p>
								</c:if>
								<c:if test="${not empty result && result ne 'true'}">
									<p style="color: red">${result}</p>
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
