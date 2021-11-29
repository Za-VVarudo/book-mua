<%-- 
    Document   : yourcart
    Created on : Jun 13, 2021, 7:43:39 PM
    Author     : Death
--%>

<%@page import="br.dats.UserError"%>
<%@page import="br.dats.Product"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.dats.ShoppingCart"%>
<%@page import="br.dats.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Cart</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/my-css.css">
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
            } else if (!user.getRoleID().equals("US")) {
                response.sendRedirect("MainController?action=Search");
                return;
            }
            UserError error = (UserError) request.getAttribute("USER_ERROR");
            if (error == null) error = new UserError();
            String searchKey = request.getParameter("searchKey");
            if (searchKey==null) searchKey="";
            String message = (String) request.getAttribute("CART_MESSAGE");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            if (phone ==null) phone = user.getPhone();
            if (address == null) address = user.getAddress();
        %>
        <header>
            <nav class="navbar navbar-expand-sm navbar-dark bg-secondary">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a href="MainController?action=Search" class="nav-link">Home</a>
                    </li>
                    <li class="nav-item active">
                        <a href="MainController?action=View+cart" class="nav-link">Your Cart</a>
                    </li>                    
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item justify-content-end">
                        <a class="nav-link" style="color: #00ff00">Welcome USER <%=user.getFullName() %></a>
                    </li>
                    <li class="nav-item justify-content-end">
                        <a href="MainController?action=Logout" class="nav-link">Logout</a>
                    </li>   
                </ul>    
            </nav>
        </header><br>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <h2 class="text-center">Your Cart</h2>
                    <%
                        ShoppingCart cart = (ShoppingCart) session.getAttribute("SHOPPING_CART");
                        if (cart!=null && cart.size()>0) {                
                            String verification = (String) request.getAttribute("VERIFICATION");
                            Iterator<String> ite = cart.keySet().iterator();
                            int count=0;
                            int total=0;
                    %>
                        <% if (message!=null) {%>
                            <div class="alert alert-info text-center">
                                <strong><%=message%></strong>
                            </div>
                        <%}%>
                    <table class="table-responsive-md table-hover text-center">
                        <thead class="border-top border-bottom">
                            <tr>
                                <th>No</th>
                                <th>Book Name</th>
                                <th>Image</th>
                                <th>Author</th>
                                <th>Publisher</th>
                                <th>Category</th>
                                <th>Quantity</th>
                                <th>Price(VND)</th>
                                <th> Action </th>
                            </tr>
                        </thead>
                        <tbody>
                            <%while (ite.hasNext()) {
                                Product item = cart.get(ite.next());
                                count++;                    
                                total = total + item.getTotal();
                            %>
                            <tr class="border-top border-bottom">
                                <td><%=count %></td>
                                <td><%=item.getProductName()%></td>
                                <td><a href="<%=item.getImageURL()%>" target="_blank"><img src="<%=item.getImageURL()%>" alt="<%=item.getProductName()%>" class="rounded img-fluid"/></a></td>
                                <td><%=item.getAuthor()%></td>
                                <td><%=item.getPublisher()%></td>
                                <td><%=item.getCategoryName()%></td>
                                    <form action="MainController" method="POST" id="addToCartForm">
                                        <td> <input type="number" name="quantity" min="0" max="<%=item.getCurrQuantity()%>" value="<%=item.getQuantity() %>" class="form-control w-100" required=""/></td>
                                <td><%=item.getTotal()%></td>
                                <td>
                                        <input type="text" name="productID" value="<%=item.getProductID() %>" hidden="" required=""/>
                                        <input type="text" name="searchKey" value="<%=searchKey %>" hidden=""/>
                                        <input type="submit" value="Edit quantity" name="action" />
                                    </form>
                                    <form action="MainController" method="POST">
                                        <input type="text" name="productID" value="<%=item.getProductID() %>" hidden="" required=""/>
                                        <input type="text" name="searchKey" value="<%=searchKey %>" hidden=""/>
                                        <br>
                                        <button type="submit" value="Remove from cart" name="action" class="border-0">
                                            <i class="bi bi-trash"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table><br>
                </div> 
                <div class="col-lg-4">
                        <div class="card w-auto">
                            <div class="card-header text-center"> <h5>Payment Infomation</h5></div>
                            <div class="card-body">
                            <form action="MainController" method="POST">
                                <div class="">Total cost: <%=total %> VND</div><br>
                                Address <input type="text" name="address" id="address" value="<%=address%>" required=""/>
                                <br><br><% if (error.getAddress_E().length() > 0) {%>
                                <div class="alert alert-danger">
                                    <strong><%=error.getAddress_E()%></strong>
                                </div>
                                <%}%>
                                Phone Number <input type="text" name="phone" value="<%=phone%>" required=""/>
                                <br><br><% if (error.getPhone_E().length() > 0) {%>
                                <div class="alert alert-danger">
                                    <strong><%=error.getPhone_E()%></strong>
                                </div>
                                <%}%>
                                <%if (verification == null ) {%>
                                <input type="submit" value="Get Email Verification" name="action"/>
                                <%} else {
                                    cart.setVerification(verification);
                                %>
                                Verification Code <input type="text" name="verification" value="" required=""/>
                                <br><br><% if (error.getMessage_E().length() > 0) {%>
                                        <div class="alert alert-danger">
                                            <strong><%=error.getMessage_E()%></strong>
                                        </div>
                                        <%}%>
                                <a href="MainController?action=Get+Email+Verification">Resend code ?</a><br><br>        
                                <input type="submit" value="Confirm Order" name="action"/>
                                <%}%>
                            </form></div>
                        </div>
                </div>            
                            <%} else {%>
                            <div class="alert alert-warning text-center">
                                <strong>Nothing found. Please add something first !!!</strong>
                            </div>
                            <%}%>
                            <%if (cart!=null && cart.isEmpty()) session.removeAttribute("SHOPPING_CART"); %>                       
            </div>                        
        </div>            
        <script src="js/bootstrap.bundle.min.js"></script>      
    </body>
</html>
