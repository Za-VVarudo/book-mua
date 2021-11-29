<%-- 
    Document   : usersearch
    Created on : Jun 12, 2021, 8:03:46 PM
    Author     : Death
--%>

<%@page import="br.dats.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.dats.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
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
            } else if (!user.getRoleID().equals("US")) {
                response.sendRedirect("MainController?action=Search");
                return;
            }
            String searchKey = request.getParameter("searchKey");
            if (searchKey==null) searchKey = "";
            String paging = request.getParameter("paging");
            if (paging==null) paging = "off";
            String categorySearch = request.getParameter("categorySearch");
            if (categorySearch==null) categorySearch = "";
            String pageNum = request.getParameter("pageNum");
            if (pageNum==null) pageNum="1";
            else if (!pageNum.matches("[0-9]+")) pageNum = "1";
            int maxPage = 1;
            if (request.getAttribute("MAX_PAGE")!=null) maxPage = (Integer) request.getAttribute("MAX_PAGE");
            String itemMessage = (String) request.getAttribute("ITEM_MESSAGE");
        %>
        <header>
            <nav class="navbar navbar-expand-sm navbar-dark bg-secondary">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
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
            <h2 class="text-center">Item Search</h2>
            <div class="row justify-content-center">
                <div class="col-sm-8 text-center">
                    <div class="justify-content-center">
                            <form action="MainController">                
                                    <label for="categories">Category : </label>
                                    <select id="categories" name="categorySearch" class="form-control-sm">
                                        <option value=""            <% if (categorySearch.equals("")) {%> selected <%}%> >All</option>
                                        <option value="1"          <% if (categorySearch.equals("1")) {%> selected <%}%> >Fantasy</option>
                                        <option value="2"          <% if (categorySearch.equals("2")) {%> selected <%}%> >Adventure</option>
                                        <option value="3"          <% if (categorySearch.equals("3")) {%> selected <%}%> >Romance</option>
                                        <option value="4"          <% if (categorySearch.equals("4")) {%> selected <%}%> >Horror</option>
                                        <option value="5"          <% if (categorySearch.equals("5")) {%> selected <%}%> >Science</option>
                                        <option value="6"          <% if (categorySearch.equals("6")) {%> selected <%}%> >Fiction</option>
                                        <option value="7"          <% if (categorySearch.equals("7")) {%> selected <%}%> >Non-Fiction</option>
                                    </select>
                                    <input type="text" name="searchKey" value="<%=searchKey %>" placeholder="Search"/> 
                                    <input type="text" name="paging" value="<%=paging %>" hidden=""/>
                                    <%if (paging.equalsIgnoreCase("on")) {%> <input type="number" name="pageNum" value="1" hidden=""/> <%}%>
                                    <input type="submit" value="Find Item" name="action"/> 
                            </form>
                    </div>
                    <div class="my-link">
                        <% if (paging.equalsIgnoreCase("on")) {%>
                        Paging Mode : <a href="MainController?action=Find+Item&searchKey=<%=searchKey%>&categorySearch=<%=categorySearch%>&paging=off"> ON</a>
                        <%} else if (paging.equalsIgnoreCase("off")) { %>
                        Paging Mode : <a href="MainController?action=Find+Item&searchKey=<%=searchKey%>&categorySearch=<%=categorySearch%>&pageNum=<%=pageNum%>&paging=on"> OFF</a>
                        <%}%>
                    </div><br>                
                            <% if (itemMessage!=null) {%>
                                <div class="alert alert-info">
                                    <strong><%=itemMessage%></strong>
                                </div>
                            <%}%>
                        <%
                            ArrayList<Product> list = (ArrayList<Product>) request.getAttribute("SEARCH_ITEM");
                            if (list != null) {
                        %><br>
                            <div>
                                <%if (paging.equalsIgnoreCase("on")) {  
                                    int pagePos = Integer.parseInt(pageNum);
                                    String url = "MainController?action=Find+Item&searchKey="+searchKey+"&categorySearch="+ categorySearch +"&paging=on&pageNum=" ;
                                %>
                                <nav aria-label="Page navigation example">
                                    <ul class="pagination justify-content-center">
                                        <li class="page-item"><a class="page-link" href="<%=url+"1"%>">First</a></li>
                                        <% if (pagePos > 1) {%>
                                        <li class="page-item"><a class="page-link" href="<%=url + (pagePos-1) %>">Previous</a></li>
                                        <li class="page-item"><a class="page-link" href="<%=url + (pagePos-1) %>"> <%=pagePos-1%> </a></li>
                                        <% }%>
                                        <li class="page-item active"><a class="page-link" href="<%=url + pagePos%>"> <%=pagePos%></a></li>
                                        <% if (pagePos < maxPage) {%>
                                        <li class="page-item"><a class="page-link" href="<%=url + (pagePos+1) %>"> <%=pagePos+1%></a></li>
                                        <li class="page-item"><a class="page-link" href="<%=url + (pagePos+1) %>">Next</a></li>
                                        <% }%>
                                        <li class="page-item"><a class="page-link" href="<%=url + maxPage%>">Last</a></li>
                                    </ul>
                                </nav>
                                <%}%>
                            </div>
                            <table class="table-responsive-sm table-hover">
                                <thead class="border-top border-bottom">
                                    <tr>
                                        <th>Book Name</th>
                                        <th>Image</th>
                                        <th>Author</th>
                                        <th>Publisher</th>
                                        <th>Category</th>
                                        <th>Total Quantity</th>
                                        <th>Quantity</th>
                                        <th>Price(VND)</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%for (Product item : list) {%>
                                    <tr class="border-top border-bottom">                    
                                        <td><%=item.getProductName()%></td>
                                        <td><a href="<%=item.getImageURL()%>" target="_blank"><img src="<%=item.getImageURL()%>" title="<%=item.getProductName()%>" class="rounded img-fluid"/></a></td>
                                        <td><%=item.getAuthor()%></td>
                                        <td><%=item.getPublisher()%></td>
                                        <td><%=item.getCategoryName()%></td>
                                        <td><%=item.getCurrQuantity()%></td>  
                                            <form method="POST" action="MainController">
                                                <td> <input type="number"  min="1" max="<%=item.getCurrQuantity()%>" name="quantity" value="1" class="form-control w-100" required=""/> </td>
                                        <td><%=item.getPrice()%></td>                  
                                        <td>
                                                <input type="text" name="productID" value="<%=item.getProductID() %>" hidden="" required=""/>               
                                                <input type="text" name="searchKey" value="<%=searchKey %>" hidden=""/>
                                                <input type="text" name="categorySearch" value="<%=categorySearch %>" hidden=""/>
                                                <input type="text" name="paging" value="<%=paging%>" hidden=""/>
                                                <input type="number" name="pageNum" value="<%=pageNum %>" hidden=""/>
                                                <%if (item.isStatus()&& item.getCurrQuantity()>0) {%>
                                                <input type="submit" value="Add to cart" name="action"/>
                                                <%} else if (item.getCurrQuantity()==0) {%>
                                                            Out of stock 
                                                <%}%>
                                            </form>
                                        </td>
                                    </tr>
                                    <%}%>
                                </tbody>
                            </table><br>
                            <div>
                                <%if (paging.equalsIgnoreCase("on")) {%> <p><%="Page "+ pageNum + " of " + maxPage %></p> <%}%>
                            </div>    
                        <%}%>
            </div>                   
        </div>                        
        <script src="js/bootstrap.bundle.min.js"></script>    
    </body>
</html>
