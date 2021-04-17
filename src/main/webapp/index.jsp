<%-- 
    Document   : index
    Created on : Mar 6, 2021, 12:04:20 AM
    Author     : ThinhPH
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
    	<link rel="shortcut icon" type="image/x-icon" href="./assets/logo/favicon.ico" />
        <title>Dashboard</title>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        
        <!--     Fonts and icons     -->
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
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
                    <img src="./assets/logo/fptu.png" class="img-fluid" alt="Responsive image">
                </div>
                <div class="sidebar-wrapper">
                    <ul class="nav">
                        <li class="nav-item active">
                            <a class="nav-link" href="MainServlet?action=Dashboard">
                                <i class="material-icons">dashboard</i>
                                <p>Dashboard</p>
                            </a>
                        </li>
                        <!-- your sidebar here -->
                        <li class="nav-item">
                            <a class="nav-link" href="MainServlet?action=ImageList">
                                <i class="material-icons">image</i>
                                <p>Image List</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="MainServlet?action=Deploy&option=0">
                                <i class="material-icons">upgrade</i>
                                <p>Deploy</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="MainServlet?action=Client">
                                <i class="material-icons">devices</i>
                                <p>Clients</p>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="main-panel">
                <!-- Navbar -->
                <nav style="padding-left:450px; padding-top: 10px"> 
                    <div class="container-fluid">
                        <div class="navbar-wrapper">
                           <img src="./assets/logo/logo-penguins.png" class="img-fluid" alt="Responsive image">  
                        </div>
                    </div>
                </nav>
                <!-- End Navbar -->
                <div class="content">
                    <div class="container-fluid">
                        <!-- your content here -->
                        <div class="embed-responsive embed-responsive-16by9" style="background-color: #fafafa;">
                        	<iframe class="embed-responsive-item" src="http://localhost:3001/"></iframe>
                        </div>
                        <form action="MainServlet" method="post">
							<div class="row">
								<div class="col-md-2">
									<label for="request" style="color: black">Number of
										request: </label> <input type="text" id="request" name="numberOfRequests"
										placeholder="default: 4" class="form-control">
								</div>
								<button type="submit" class="btn btn-sm btn-info"
									style="height: 50%; margin-top: 35px" name="action"
									value="ChangNOR">Ping!</button>
							</div>
						</form>
                    </div>
                </div>
                <footer class="footer">
                    <div class="container-fluid">
                        <nav class="float-left">
                            <ul>
                                <li>
                                    <a href="https://www.creative-tim.com">
                                        ThinhPH
                                    </a>
                                </li>
                            </ul>
                        </nav>
                        <div class="copyright float-right">
                            &copy;
                            <script>
                                document.write(new Date().getFullYear())
                            </script>, made with <i class="material-icons">favorite</i> by GPS21IA01.
                        </div>
                        <!-- your footer here -->
                    </div>
                </footer>
            </div>
        </div>
    </body>
</html>
