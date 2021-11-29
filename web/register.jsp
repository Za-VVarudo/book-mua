<%-- 
    Document   : register
    Created on : Jun 15, 2021, 9:56:14 PM
    Author     : Death
--%>

<%@page import="br.com.google.constants.ReCaptchaConstants"%>
<%@page import="br.dats.UserError"%>
<%@page import="br.dats.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
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
                    <li class="nav-item">
                        <a href="MainController?action=Search" class="nav-link">Home</a>
                    </li>
                    <li class="nav-item">
                        <a href="login.jsp" class="nav-link">Login</a>
                    </li>
                </ul>
            </nav>
        </header><br>
        <%
            User user = (User) session.getAttribute("LOGIN_USER");
            if (user!=null) {
                session.invalidate();
                session = request.getSession();
                response.sendRedirect("register.jsp");
            }
            UserError error = (UserError) request.getAttribute("USER_ERROR");
            if (error==null) error = new UserError();           
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            User loginGoogle = (User) session.getAttribute("LOGIN_GOOGLE");
            String email;
            if (loginGoogle!=null) {
                email = loginGoogle.getEmail();
                session.removeAttribute("LOGIN_GOOGLE");
            } else email = request.getParameter("email");
            if (userID==null) userID = "";
            if (fullName==null) fullName = "";
            else fullName = new String(fullName.getBytes("iso-8859-1"),"UTF-8");
            if (email==null) email="";
            if (address==null) address ="";
            if (phone==null) phone="";
        %>
        <div class="container">
            <div class="row justify-content-center">
                <div class="card">
                    <div class="card-header text-center"><h2>Register</h2></div>
                    <div class="card-body">
                        <form action="MainController" method="POST">
                            Username <input type="text" name="userID" value="<%=userID %>" required=""/>
                            <br><br><% if (error.getUserID_E().length() > 0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getUserID_E()%></strong>
                            </div>
                            <%}%>
                            Full Name <input type="text" name="fullName" value="<%=fullName %>" required=""/>
                            <br><br><% if (error.getFullName_E().length() > 0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getFullName_E()%></strong>
                            </div>
                            <%}%>
                            <p>Gender :</p>
                            <input type="radio" id="male" name="gender" value="Male" required="">
                            <label for="male">Male</label><br>
                            <input type="radio" id="female" name="gender" value="Female">
                            <label for="female">Female</label><br>
                            <input type="radio" id="other" name="gender" value="Other">
                            <label for="other">Other</label><br><br>
                            Email <input type="text" name="email" value="<%=email%>" required="" size="30"/>
                            <br><br><% if (error.getEmail_E().length() > 0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getEmail_E()%></strong>
                            </div>
                            <%}%>
                            Address <input type="text" name="address" value="<%=address%>" required="" size="30"/>
                            <br><br><% if (error.getAddress_E().length() > 0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getAddress_E()%></strong>
                            </div>
                            <%}%>
                            Phone Number <input type="text" name="phone" value="<%=phone%>" required="" size="30"/>
                            <br><br><% if (error.getPhone_E().length() > 0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getPhone_E()%></strong>
                            </div>
                            <%}%>
                            Password <input type="password" name="password" value="" required=""/>
                            <br><br><% if (error.getPassword_E().length() > 0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getPassword_E()%></strong>
                            </div>
                            <%}%>
                            Confirm Password <input type="password" name="confirmPassword" value="" required=""/>
                            <br><br><% if (error.getConfirmPassword_E().length() > 0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getConfirmPassword_E()%></strong>
                            </div>
                            <%}%>
                            <div class="g-recaptcha"
                                data-sitekey=<%=ReCaptchaConstants.RECAPTCHA_SITE_KEY %>></div>
                            <br><br><% if (error.getMessage_E().length() > 0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getMessage_E()%></strong>
                            </div>
                            <%}%>
                            <input type="submit" value="Register" name="action" />
                        </form></div>
                </div>            
            </div>                
        </div>
        <script src="js/bootstrap.bundle.min.js"></script>       
    </body>
</html>
