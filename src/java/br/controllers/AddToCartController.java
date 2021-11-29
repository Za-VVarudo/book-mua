/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.dats.Product;
import br.dats.ProductDAO;
import br.dats.ShoppingCart;
import br.dats.User;
import org.apache.log4j.Logger;

/**
 *
 * @author Death
 */
public class AddToCartController extends HttpServlet {
    
    private final String ERROR = "error.jsp";
    private final String SUCCESS = "MainController?action=Find+Item";
    private final Logger LOGGER = Logger.getLogger(AddToCartController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = ERROR;
        HttpSession session  = request.getSession();
        try {
            User user = (User) session.getAttribute("LOGIN_USER");
            if (user!=null) {
                if (user.getRoleID().equals("US")) {
                    String productID = request.getParameter("productID");
                    String searchKey = request.getParameter("searchKey");
                    int quantity = (int) Double.parseDouble(request.getParameter("quantity"));
                    ShoppingCart cart = (ShoppingCart) session.getAttribute("SHOPPING_CART");
                        if (cart ==null) {
                            cart = new ShoppingCart();
                            session.setAttribute("SHOPPING_CART", cart);
                        }
                    if (productID!=null) {
                        ProductDAO dao = new ProductDAO();
                        Product item = dao.getItem(productID);
                        if (item!=null) {
                            item.setQuantity(quantity);
                            boolean valid = cart.addItem(item);
                            if (valid) {
                                request.setAttribute("ITEM_MESSAGE", "Added successfully !");
                            } else request.setAttribute("ITEM_MESSAGE", "Added failed. Your purchase quantity is larger than the available !!!");
                            url = SUCCESS;
                        } else request.setAttribute("ITEM_MESSAGE", "ITEM NOT FOUND !!!");
                    }
                } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN WITH USER ROLE TO CONTINUE !!");
            } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN TO CONTINUE !!!");            
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nError at AddToCardController:  \n", e);
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
