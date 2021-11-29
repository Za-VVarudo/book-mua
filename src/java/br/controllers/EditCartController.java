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
public class EditCartController extends HttpServlet {
    
    private final String ERROR = "error.jsp";
    private final String SUCCESS = "MainController?action=View+cart";
    private final Logger LOGGER = Logger.getLogger(EditCartController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("LOGIN_USER");
            if (user!=null) {
                if (user.getRoleID().equals("US")) {
                    String productID = request.getParameter("productID");
                    int quantity = (int) Double.parseDouble(request.getParameter("quantity"));
                    ShoppingCart cart = (ShoppingCart) session.getAttribute("SHOPPING_CART");
                    if (cart!=null) {
                        if (productID!=null ) {
                            Product item = cart.get(productID);                            
                            if (item!=null) {
                                if (quantity==0){
                                    cart.remove(productID);
                                    request.setAttribute("CART_MESSAGE", "Updated ");
                                } else {
                                    ProductDAO dao  = new ProductDAO();
                                    item = dao.getItem(productID);
                                    if (item!=null) {
                                        if (quantity <= item.getCurrQuantity()) {
                                            item.setQuantity(quantity);
                                            cart.put(productID, item);
                                            request.setAttribute("CART_MESSAGE", "Updated ");
                                        } else request.setAttribute("CART_MESSAGE", "THE ITEM ['"+cart.get(productID).getProductName()+"'] IS NOT AVAILABLE OR OUR STOCK IS NOT ENOUGH !!!");
                                    } else request.setAttribute("CART_MESSAGE", "THE ITEM ['"+cart.get(productID).getProductName()+"'] IS NOT AVAILABLE OR OUR STOCK IS NOT ENOUGH !!!");
                                }
                            } else request.setAttribute("ERROR_MESSAGE", "ITEM NOT FOUND !!!");
                        } else request.setAttribute("ERROR_MESSAGE", "ITEM NOT FOUND !!!");
                    } 
                    url = SUCCESS;
                } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN WITH USER ROLE TO CONTINUE !!");
            } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN TO CONTINUE !!!");
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nERROR at EditCartController: \n", e);
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
