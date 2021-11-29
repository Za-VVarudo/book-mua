<%-- 
    Document   : payorder
    Created on : Jun 23, 2021, 6:18:42 PM
    Author     : Death
--%>

<%@page import="br.dats.OrderDetail"%>
<%@page import="br.dats.Order"%>
<%@page import="java.util.UUID"%>
<%@page import="br.dats.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pay Page</title>
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
            } else if (!user.getRoleID().equals("US")) {
                response.sendRedirect("MainController?action=Search");
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
                    <h2 class="text-center">Order Payment</h2>
                    <%
                        Order order = (Order) request.getAttribute("ORDER_VIEW");
                        if (order == null) {
                            session.setAttribute("ERROR_MESSAGE", " NO SUCH A PAYMENT PROCESS !!!");
                            response.sendRedirect("error.jsp");
                            return;
                        }
                    %>
                    <div class="text-justify">
                        <div class="alert alert-success text-center">
                                <strong>PAYMENT COMPLETE !! THANK FOR YOUR PATRONAGE !!!</strong>
                        </div>
                        <p>Order ID: <%=order.getOrderID()%></p>
                        <p>Customer Name: <%=user.getFullName()%></p>
                        <p>Address: <%=order.getAddress()%></p>
                        <p>Phone Number: <%=order.getPhone()%></p>
                        <p>Date of transaction: <%=order.getPurchaseDate()%></p>
                        <p>Order Total: <%=order.getTotal() + " VND"%></p>
                    </div>
                    <details>
                        <summary>Order detail</summary>
                        <table class="table-responsive-md table-bordered text-center bg-white">
                            <thead>
                                <tr>
                                    <th>Book ID</th>
                                    <th>Price (VND)</th>
                                    <th>Quantity</th>
                                    <th>Total (VND</th>
                                </tr>
                            </thead>
                            <tbody>
                                    <%
                                        for (OrderDetail od : order.getDetail()) {
                                    %>
                                <tr>
                                    <td><%=od.getProductID()%></td>
                                    <td><%=od.getPrice()%></td>
                                    <td><%=od.getQuantity()%></td>
                                    <td><%=od.getTotal()%></td>
                                </tr>
                                    <%}%>
                            </tbody>
                        </table>
                    </details>
        </div>                    
        <script src="js/bootstrap.bundle.min.js"></script>        
    </body>
</html>
