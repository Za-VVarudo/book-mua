<%-- 
    Document   : update
    Created on : Jun 14, 2021, 6:42:54 PM
    Author     : Death
--%>
<%@page import="br.dats.UserError"%>
<%@page import="br.dats.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Page</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/my-css.css" rel="stylesheet">
    </head>
    <body class="my-bg">
        <div class="text-center bg-white" style="padding-bottom:10px; padding-top: 10px;">
            <h1>BOOK MUA</h1>            
        </div>
        <%
            User user = (User) session.getAttribute("LOGIN_USER");   
            if (user==null) {
                response.sendRedirect("login.jsp");
                return;
            } else if (!user.getRoleID().equals("AD")) {
                response.sendRedirect("SearchUserController");
                return;
            }
            UserError error = (UserError) request.getAttribute("USER_ERROR");
            if (error==null) error = new UserError();
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String roleID = request.getParameter("roleID");
            String searchKey = request.getParameter("searchKey");
            if (userID==null) {
                response.sendRedirect("error.jsp");
                session.setAttribute("ERROR_MESSAGE", "USER NOT FOUND !!!");
                return;
            }
            if (fullName!=null) fullName = new String(fullName.getBytes("iso-8859-1"),"UTF-8");
            else fullName = "";
            if (gender==null) gender = "";
            if (email==null) email = "";
            if (address==null) address = "";
            if (phone==null) phone = "";
            if (roleID==null) roleID = "";
            if (searchKey==null) searchKey = "";
        %>
        <header>
            <nav class="navbar navbar-expand-sm navbar-dark bg-secondary">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a href="MainController?action=Search" class="nav-link">Home</a>
                    </li>
                    <li class="nav-item">
                        <a href="MainController?action=Edit+Item" class="nav-link">Edit Item</a>
                    </li>                    
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item justify-content-end">
                        <a class="nav-link" style="color: #ff0000">Welcome ADMIN <%=user.getFullName() %></a>
                    </li>
                    <li class="nav-item justify-content-end">
                        <a href="MainController?action=Logout" class="nav-link">Logout</a>
                    </li>   
                </ul>    
            </nav>
        </header><br>
        <div class="container">
            <div class="row justify-content-center">
                <div class="card">
                    <div class="card-header"><h3>Update User</h3></div>
                    <div class="card-body">
                    <form action="MainController" method="POST">
                        User ID <input type="text" name="userID" value="<%=userID %>" readonly="" required=""/><br><br>
                        Full Name <input type="text" name="fullName" value="<%=fullName %>" required=""/>
                        <br><br><% if (error.getFullName_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getFullName_E()%></strong>
                            </div>
                        <%}%>
                        Gender <input type="text" name="gender" value="<%=gender %>" required="" readonly=""/>
                        <br><br><% if (error.getGender_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getGender_E()%></strong>
                            </div>
                        <%}%>
                        Email <input type="text" name="email" value="<%=email %>" required=""/>
                        <br><br><% if (error.getEmail_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getEmail_E()%></strong>
                            </div>
                        <%}%>
                        Address <input type="text" name="address" value="<%=address %>" required=""/>
                        <br><br><% if (error.getAddress_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getAddress_E()%></strong>
                            </div>
                        <%}%>
                        Phone <input type="number" name="phone" value="<%=phone%>" required=""/>
                        <br><br><% if (error.getPhone_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getPhone_E()%></strong>
                            </div>
                        <%}%>
                        Role ID <input type="text" name="roleID" value="<%=roleID %>" required=""/>
                        <br><br><% if (error.getRoleID_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getRoleID_E()%></strong>
                            </div>
                        <%}%>
                        <input type="text" name="searchKey" value="<%=searchKey %>"  hidden=""/>
                        <input type="submit" value="Confirm Update" name="action" />
                    </form></div>
                </div> 
            </div>             
        </div>
        <script src="js/bootstrap.bundle.min.js"></script>        
    </body>
</html>
