<%-- 
    Document   : login
    Created on : Jun 15, 2021, 8:53:30 PM
    Author     : Death
--%>

<%@page import="br.com.google.constants.LoginGoogleConstants"%>
<%@page import="br.com.google.constants.ReCaptchaConstants"%>
<%@page import="br.dats.UserError"%>
<%@page import="br.dats.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title> 
        <script src='https://www.google.com/recaptcha/api.js'></script>
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
                    <li class="nav-item active">
                        <a href="MainController?action=Search" class="nav-link">Home</a>
                    </li>
                    <li class="nav-item">
                        <a href="register.jsp" class="nav-link">Register</a>
                    </li>
                </ul>
            </nav>
        </header><br>
        <div class="container">
        <%
            User user = (User) session.getAttribute("LOGIN_USER");
            if (user!=null) {
                response.sendRedirect("MainController?action=Search&searchKey=");
                return;
            }
            String error = (String) request.getAttribute("ERROR_MESSAGE");
            String userID = request.getParameter("userID");
            if (userID==null) userID="";
        %>        
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <% if (error!=null) {%>
                        <div class="alert alert-danger">
                            <strong><%=error%></strong>
                        </div>
                    <%}%>
                    <div class="card">
                        <div class="card-header text-center"> <h2>Login</h2> </div>
                        <div class="card-body">
                        <form action="MainController" method="POST">
                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <label for="userID" >Username: </label>
                                    <input type="text" id="userID" class="form-control" name="userID" value="<%=userID %>" required=""/> 
                                    <label for="password" >Password: </label>
                                    <input type="password" id="password" class="form-control" name="password" value="" required=""/><br>

                                <%--GG RECAPTCHA must add javascript first <script src='https://www.google.com/recaptcha/api.js'></script>--%>
                                    <div class="g-recaptcha" data-sitekey=<%=ReCaptchaConstants.RECAPTCHA_SITE_KEY %>></div><br>
                                <%--End GG RECAPT CHA--%>
                                    <input type="submit" class="btn btn-primary" value="Login" name="action" />
                                    <input type="reset" class="btn btn-primary" value="Reset" />  <br><br>  
                                    <a href="register.jsp">Don't have account ?</a>
                                </div>
                                <div class="col-md-6">
                                    <div class="text-center">Or</div>
                                    <a href=<%=LoginGoogleConstants.LOGIN_GOOGLE_LINK%>>
                                        <img src="images/login-google.png" alt="Login With Google" class="img-fluid"/>
                                    </a> 
                                </div>
                            </div>        
                        </form></div>
                    </div>
                    
                </div>
            </div>
        </div>                            
        <script src="js/bootstrap.bundle.min.js"></script>
    </body>
</html>
