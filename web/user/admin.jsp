<%-- 
    Document   : admin
    Created on : Jun 12, 2021, 8:33:07 PM
    Author     : Death
--%>

<%@page import="br.dats.UserError"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.dats.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
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
                response.sendRedirect("MainController?action=Search");
                return;
            }
            String searchKey = request.getParameter("searchKey");
            if (searchKey==null) searchKey ="";
            String editMessage = (String) request.getAttribute("EDIT_MESSAGE");
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
                        <a class="nav-link" style="color: #ff0000;">Welcome ADMIN <%=user.getFullName() %></a>
                    </li>
                    <li class="nav-item justify-content-end">
                        <a href="MainController?action=Logout" class="nav-link">Logout</a>
                    </li>   
                </ul>    
            </nav>
        </header><br>
        <div class="container">
            <h2 class="text-center">User Search</h2><br>           
            <div class="row justify-content-sm-center">
                <div class="text-center">
                        <div class="form-group">
                            <form action="MainController">
                                <input type="text" name="searchKey" value="<%=searchKey%>" placeholder="Search User" />
                                <input type="submit" value="Search" name="action" />
                            </form>
                        </div>
                        <%
                            ArrayList<User> list = (ArrayList<User>) request.getAttribute("SEARCH_USER");
                            String message=" Nothing found";
                            if (list != null) {
                                int count = 1; 
                                message = "There are "+list.size()+" result(s).";
                        %>
                        <% if (editMessage!=null) {%>
                            <div class="alert alert-info">
                                <strong><%=editMessage%></strong>
                            </div>
                        <%}%> 
                        <table class="table-responsive-sm table-bordered table-hover bg-light">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>User ID</th>
                                    <th>Full Name</th>
                                    <th>Password</th>
                                    <th>Gender</th>
                                    <th>Email</th>
                                    <th>Address</th>
                                    <th>Phone</th>
                                    <th>RoleID</th>
                                    <th>Update</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%for (User u : list) {%>
                                <tr>
                                    <td><%=count %></td>
                                    <form action="MainController" method="POST">
                                    <td><%=u.getUserID() %></td>
                                    <input type="text" name="userID" value="<%=u.getUserID()%>" required="" hidden=""/></td>
                                    <td><input type="text" name="fullName" value="<%=u.getFullName() %>" required="" class="form-control w-auto"/></td>
                                    <td><%=u.getPassword()%></td>
                                    <td><%=u.getGender()%></td>
                                    <input type="text" name="gender" value="<%=u.getGender()%>" readonly="" hidden=""/>
                                    <td><input type="text" name="email" value="<%=u.getEmail() %>" required=""/></td>
                                    <td><input type="text" name="address" value="<%=u.getAddress() %>" required=""/></td>
                                    <td><input type="number" name="phone" value="<%=u.getPhone()%>" required=""/></td>
                                    <td><input type="text" name="roleID" value="<%=u.getRoleID() %>" required="" class="form-control"/></td>
                                    <td>                                                   
                                    <input type="text" name="searchKey" value="<%=searchKey %>" hidden=""/>
                                    <input type="submit" value="Confirm Update" name="action" />
                                    </form>
                                    </td>
                                    <td>
                                        <% if (!u.getRoleID().equals("AD")) {%>
                                        <form action="MainController" method="POST">
                                            <input type="text" name="userID" value="<%=u.getUserID() %>" hidden=""/>
                                            <input type="text" name="searchKey" value="<%=searchKey %>" hidden=""/>
                                            <input type="submit" value="Delete" name="action" class="w-100"/>
                                        </form>
                                            <%} else %> Restricted
                                    </td>                    
                                </tr>
                                <%count++; }%>
                            </tbody>
                        </table><br>
                        
                            <%} else message = "";%>
                            <%=message %>
                </div>            
            </div>                
        </div>                    
        <script src="js/bootstrap.bundle.min.js"></script>      
    </body>
</html>
