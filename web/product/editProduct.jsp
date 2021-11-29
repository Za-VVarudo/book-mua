<%-- 
    Document   : edititem
    Created on : Jun 14, 2021, 7:44:46 PM
    Author     : Death
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="br.dats.Product"%>
<%@page import="br.dats.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Item</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/my-css.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
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
                    <li class="nav-item">
                        <a href="MainController?action=Search" class="nav-link">Home</a>
                    </li>
                    <li class="nav-item active">
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
            <h2 class="text-center">Item Edit</h2><br>
            <div class="row justify-content-sm-center">
                <div class=" text-center">
                    <div class="form-group">
                    <form action="MainController">
                        <input type="text" name="searchKey" value="<%=searchKey %>"  placeholder="Search Item" />
                        <input type="submit" value="Find Item" name="action"/>
                    </form>
                    </div><br>
                    <%
                        ArrayList<Product> list = (ArrayList<Product>) request.getAttribute("SEARCH_ITEM");
                        String message;
                        if (list == null) message = "";
                        else {
                            int count = 1; 
                            message = "There are "+list.size()+" result(s).";
                    %>
                    <% if (editMessage != null) {%>
                        <div class="alert alert-info text-center">
                            <strong><%=editMessage%></strong>
                        </div>
                    <%}%>
                    <table class="table-responsive-sm table-hover table-bordered bg-white">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Book ID</th>
                                <th>Book Name</th>
                                <th>Image</th>
                                <th>Author</th>
                                <th>Publisher</th>
                                <th>Category ID</th>
                                <th>Total Quantity</th>
                                <th>Price(VND)</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%for (Product item : list) {%>
                            <tr>
                                <td><%=count %></td>
                                <td><%=item.getProductID()%></td>
                                <form method="POST" action="MainController">
                                <td><input type="text" name="productName" value="<%=item.getProductName()%>" required=""/></td>
                                <td><input type="text" name="imageURL" value="<%=item.getImageURL()%>" required="" /></td>
                                <td><input type="text" name="author" value="<%=item.getAuthor()%>" required=""/></td>
                                <td><input type="text" name="publisher" value="<%=item.getPublisher()%>" required=""/></td>
                                <td><input type="text" name="categoryID" value="<%=item.getCategoryID()%>" required="" class="form-control"/></td>
                                <td><input type="number" name="currQuantity" value="<%=item.getCurrQuantity()%>" required="" min="0" class="form-control"/></td>                   
                                <td><input type="number" name="price" value="<%=item.getPrice()%>" required="" min="0" class="form-control  "/></td>
                                <td>                                 
                                    <select name="status" class="form-control w-auto">
                                        <option value="<%=item.isStatus()==true ? "1":"0"%>" selected="">   <%=item.isStatus()+""%></option>
                                        <option value="<%=item.isStatus()==true ? "0":"1"%>">   <%=!item.isStatus()+""%></option>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="productID" value="<%=item.getProductID() %>" hidden="" readonly="" required=""/>
                                    <input type="text" name="searchKey" value="<%=searchKey %>" hidden=""/>
                                    <input type="submit" value="Update Item" name="action"/>
                                </form>
                                </td>
                                <td>
                                    <form method="POST" action="MainController">
                                        <input type="text" name="productID" value="<%=item.getProductID() %>" hidden="" required="" />
                                        <input type="text" name="searchKey" value="<%=searchKey %>" hidden=""/>
                                        <button type="submit" value="Delete Item" name="action" class="border-0">
                                            <i class="bi bi-trash"></i>
                                        </button>
                                    </form>
                                </td>                    
                            </tr>
                            <%count++; }%>
                        </tbody>
                    </table><br>
                        <%}%>
                        <%=message %>
                </div>        
            </div>            
        </div>                
        <script src="js/bootstrap.bundle.min.js"></script>                      
    </body>
</html>
