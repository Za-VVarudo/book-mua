/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.controllers;

import br.dats.Order;
import br.dats.OrderDetail;
import br.dats.Product;
import br.dats.ProductDAO;
import br.dats.ShoppingCart;
import br.dats.User;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Death
 */
public class UpdatePaymentController extends HttpServlet {
    
    private final String ERROR = "error.jsp";
    private final String SUCCESS = "MainController?action=View+Order";
    private final Logger LOGGER = Logger.getLogger(UpdatePaymentController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("LOGIN_USER");
            if (user!=null) {
                if (user.getRoleID().equals("US")) {
                    ShoppingCart cart = (ShoppingCart) session.getAttribute("SHOPPING_CART");
                    if (cart != null) {
                        String address = request.getParameter("address");
                        String phone = request.getParameter("phone");
                        String orderID = UUID.randomUUID().toString().toUpperCase();
                        String userID = user.getUserID();
                        Date date = new Date();
                        String purchaseDate = new java.sql.Timestamp(date.getTime()).toString();
                        int total = cart.getTotal();
                        boolean paymentStatus = true;
                        Order order = new Order(orderID, userID, address, phone, purchaseDate, total, paymentStatus);
                        ProductDAO dao = new ProductDAO();
                        boolean valid =dao.insertOrder(order);
                        Iterator<String> ite = cart.keySet().iterator();
                        while (valid && ite.hasNext()) {
                            String productID  = ite.next();
                            Product item = cart.get(productID);
                            int quantity = item.getQuantity();
                            OrderDetail od = new OrderDetail(orderID, productID, quantity, item.getPrice());
                            valid = dao.insertOrderDetail(od);
                            if (valid) {
                                order.getDetail().add(od);
                                int currQuantity = item.getCurrQuantity() - quantity;
                                item = dao.getItem(productID);
                                item.setCurrQuantity(currQuantity);
                                if (currQuantity==0) item.setStatus(false);
                                valid = dao.updateItem(item);
                            } else break;
                        }
                        if (valid) {
                            url = SUCCESS;
                            request.setAttribute("ORDER_VIEW", order);
                            session.removeAttribute("SHOPPING_CART");
                        } else request.setAttribute("ERROR_MESSAGE", "UNKNOWN ERROR - WE WILL FIX IT AS SOON AS POSSIBLE !!!"); 
                    } else request.setAttribute("ERROR_MESSAGE", "PLEASE BUY SOMETHING FIRST !!!"); 
                } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN TO USER TO CONTINUE !!!");
            } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN TO CONTINUE !!!");
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nERROR at UpdatePaymentController: \n", e);
        } finally {
          request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
