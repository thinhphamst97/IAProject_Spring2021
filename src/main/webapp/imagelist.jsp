<%-- 
    Document   : imagelist
    Created on : Mar 12, 2021, 1:09:19 AM
    Author     : ThinhPH
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="./assets/logo/favicon.ico" />
<title>Image List</title>
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
					<div class="col-md 12">
						<div class="card">
							<div class="card-header card-header-primary"
								style="background: #3C4858">
								<h4 class="card-title">Image List</h4>
								<p class="card-category">List all the image existing on
									server hard drive</p>
							</div>
							<div class="card-body">
								<c:set var="listImage" value="${requestScope.imageList}" />
								<form action="MainServlet" method="post">
									<table class="table">
										<thead>
											<th style="font-weight: bold;">Name</th>
											<th style="font-weight: bold;">Kernel</th>
											<th style="font-weight: bold;">Size</th>
											<th style="font-weight: bold;">Date</th>
											<th style="font-weight: bold;">Status</th>
											<th style="font-weight: bold;">Action</th>
										</thead>
										<tbody>
											<c:if test="${not empty listImage}">
												<c:forEach items="${listImage}" var="x" varStatus="status">
													<tr>
														<td style="width: 14%">${x.getName()}</td>
														<td style="width: 14%">${x.kernel.getName()}</td>
														<td style="width: 14%">${x.getSize()}MB</td>
														<td style="width: 14%">${x.getDateCreated()}</td>
														<td style="width: 14%"><c:if
																test="${x.isActive() eq true}">
																<h4 id="isActive_${status.index}"
																	style="color: green; font-weight: bold">Active</h4>
																<input id="hidden_id_${status.index}"
																	name="id[]" type="hidden"
																	value="${x.getId()}" />
																<input id="hidden_status_${status.index}"
																	name="imageStatus[]" type="hidden"
																	value="${x.isActive()}" />
															</c:if> <c:if test="${x.isActive() eq false}">
																<h4 id="isActive_${status.index}"
																	style="color: red; font-weight: bold">Inactive</h4>
																<input id="hidden_id_${status.index}"
																	name="id[]" type="hidden"
																	value="${x.getId()}" />
																<input id="hidden_status_${status.index}"
																	name="imageStatus[]" type="hidden"
																	value="${x.isActive()}" />
															</c:if></td>
														<td style="width: 13%"><c:if
																test="${x.isActive() eq true}">
																<button id="btnChangeStatus${x.getId()}"
																	class="btn btn-sm btn-danger" type="button"
																	onclick="changeStatus(${x.getId()})">Deactivate</button>
															</c:if> <c:if test="${x.isActive() eq false}">
																<button id="btnChangeStatus${x.getId()}"
																	class="btn btn-sm btn-success" type="button"
																	onclick="changeStatus(${x.getId()})">     Active     </button>
															</c:if>
															<form action="MainServlet" method="post"
																style="margin: 0px; padding: 0px; display: inline">
																<input type="hidden" name="id" value="${x.getId()}">
																<button class="btn btn-sm" name="action"
																	value="ImageDetails">View</button>
															</form></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
									<button class="btn btn-info pull-right" name="action"
										value="UpdateStatusImage">Update Table</button>
								</form>

								<a href="addimage.jsp"><button type="button"
										class="btn btn-info pull-right">Add new Image</button></a>
								<!-- print delete successfully / failed -->
								<c:set var="deleteImageMessage"
									value="${requestScope.deleteImageResult}"></c:set>
								<c:if
									test="${not empty deleteImageMessage && deleteImageMessage eq 'true'}">
									<p class="text-success">Delete image successfully</p>
								</c:if>
								<c:if
									test="${not empty deleteImageMessage && deleteImageMessage ne 'true'}">
									<p class="text-danger">${deleteImageMessage}</p>
								</c:if>

							</div>
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
<script>
    function changeStatus(cellIndex) { //change status Image to Active status == Deactive
        var tdCellIndex = 'isActive_' + cellIndex;
        var btnCellIndex = 'btnChangeStatus' + cellIndex;
        var statusID = 'hidden_status_' + cellIndex;
        var imageStatus = document.getElementById(statusID);
        var cell = document.getElementById(tdCellIndex);
        var btn = document.getElementById(btnCellIndex);
        if (cell.style.color === 'red') {
            cell.style.color = 'green'; //change <p> to Inactive with color red
            cell.innerText = "Active";
            btn.className = 'btn btn-sm btn-danger'; //change <button> to Active with color green
            btn.innerText = "Deactivate";
            imageStatus.value = 'true';
        } else {
            cell.style.color = 'red';
            cell.innerText = "Inactive";
            btn.className = 'btn btn-sm btn-success';
            btn.innerText = "     Active     ";
            imageStatus.value = 'false';
        }
    }
</script>
</html>
