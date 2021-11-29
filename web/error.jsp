<%-- 
    Document   : error
    Created on : Jun 12, 2021, 7:19:36 PM
    Author     : Death
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/my-css.css" rel="stylesheet">
    </head>
    <body class="my-bg">
        <div class="text-center bg-white" style="padding-bottom:10px; padding-top: 10px;">
            <h1>BOOK MUA</h1>            
        </div>        
        <header>
            <nav class="navbar navbar-expand-sm navbar-dark bg-secondary">
                <ul class="navbar-nav">
                    <%if (session.getAttribute("LOGIN_USER")==null) {%>
                        <li class="nav-item">
                            <a href="login.jsp" class="nav-link">Home</a>
                        </li>
                        <li class="nav-item">
                            <a href="login.jsp" class="nav-link">Login</a>
                        </li>
                    <%} else {%>
                        <li class="nav-item">
                            <a href="MainController?action=Search&searchKey=" class="nav-link">Home</a>
                        </li>
                        <li class="nav-item">
                            <a href="MainController?action=Logout" class="nav-link">Logout</a>
                        </li>
                    <%}%>            
                </ul>
            </nav>
        </header><br>
        <%
            String message = (String) request.getAttribute("ERROR_MESSAGE");
            if (message==null) {
                message = (String) session.getAttribute("ERROR_MESSAGE");
                session.removeAttribute("ERROR_MESSAGE");
            }
        %>
            <div class="container">
                <% if (message!=null) {%>
                    <div class="alert alert-danger text-center">
                        <strong>
                            <%="Error: " +message%>
                        </strong>
                    </div>
                <%}%>
            </div>
        <script src="js/bootstrap.bundle.min.js"></script>
    </body>
</html>
