<%-- 
    Document   : updatebook
    Created on : Jun 22, 2021, 1:16:08 PM
    Author     : Death
--%>

<%@page import="br.dats.ProductError"%>
<%@page import="br.dats.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Product Page</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/my-css.css" rel="stylesheet">
    </head>
    <body class="my-bg">
        <div class="text-center bg-white" style="padding-bottom:10px; padding-top: 10px;">
            <h1>BOOK MUA</h1>            
        </div>
        <%
            User user = (User) session.getAttribute("LOGIN_USER");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            } else if (!user.getRoleID().equals("AD")) {
                response.sendRedirect("MainController?action=Search");
                return;
            }
            ProductError error = (ProductError) request.getAttribute("PRODUCT_ERROR");
            if (error == null) {
                error = new ProductError();
            }
            String searchKey = request.getParameter("searchKey");
            if (searchKey==null) searchKey = "";
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            String imageURL = request.getParameter("imageURL");
            String author = request.getParameter("author");
            String publisher = request.getParameter("publisher");
            String categoryID = request.getParameter("categoryID");
            int currQuantity = Integer.parseInt(request.getParameter("currQuantity"));
            int price = Integer.parseInt(request.getParameter("price"));
            boolean status = request.getParameter("status").equals("1");
            if (productID==null) {
                session.setAttribute("ERROR_MESSAGE", "PRODUCT NOT FOUND !!!");
                response.sendRedirect("error.jsp");
                return;
            }
        %>
        <header>
            <nav class="navbar navbar-expand-sm navbar-dark bg-secondary">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
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
                    <div class="card-header text-center"> <h3>Edit Product</h3> </div>
                    <div class="card-body"> 
                    <form method="POST" action="MainController">
                        Book ID <input type="text" name="productID" value="<%=productID %>" readonly="" required=""/><br><br>
                        Book Name <input type="text" name="productName" value="<%=productName %>" required=""/>
                        <br><br><% if (error.getProductName_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getProductName_E()%></strong>
                            </div>
                        <%}%>
                        Image URL<input type="text" name="imageURL" value="<%=imageURL %>" required="" />
                        <br><br><% if (error.getImageURL_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getImageURL_E()%></strong>
                            </div>
                        <%}%>
                        Author <input type="text" name="author" value="<%=author%>" required=""/>
                        <br><br><% if (error.getAuthor_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getAuthor_E()%></strong>
                            </div>
                        <%}%>
                        Publisher <input type="text" name="publisher" value="<%=publisher%>" required=""/>
                        <br><br><% if (error.getPublisher_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getPublisher_E()%></strong>
                            </div>
                        <%}%>
                        Category ID <input type="text" name="categoryID" value="<%=categoryID%>" required=""/>
                        <br><br><% if (error.getCategoryID_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getCategoryID_E()%></strong>
                            </div>
                        <%}%>
                        Total Quantity <input type="number" name="currQuantity" value="<%=currQuantity%>" required="" min="0"/>
                        <br><br><% if (error.getCurrQuantity_E().length()>0) {%>
                            <div class="alert alert-danger">
                                <strong><%=error.getCurrQuantity_E()%></strong>
                            </div>
                        <%}%>
                        Price <input type="number" name="price" value="<%=price%>" required="" min="0"/>
                        <br><br>
                        Status 
                        <select name="status">
                            <option value="<%=status == true ? "1" : "0"%>" selected="">   <%=status + ""%></option>
                            <option value="<%=status == true ? "0" : "1"%>">               <%=!status + ""%></option>
                        </select>
                        <br><br>
                        <input type="text" name="searchKey" value="<%=searchKey %>" hidden=""/>
                        <input type="submit" value="Update Item" name="action"/>
                    </form></div>
                </div>        
            </div>            
        </div>                
        <script src="js/bootstrap.bundle.min.js"></script>        
    </body>
</html>
