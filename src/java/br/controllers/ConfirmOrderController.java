/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.controllers;

import br.dats.Product;
import br.dats.ProductDAO;
import br.dats.ShoppingCart;
import br.dats.User;
import br.dats.UserError;
import java.io.IOException;
import java.util.Iterator;
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
public class ConfirmOrderController extends HttpServlet {
    
    private final String ERROR = "error.jsp";
    private final String FAIL = "MainController?action=View+cart";
    private final String SUCCESS = "MainController?action=Update+Payment";
    private final Logger LOGGER = Logger.getLogger(ConfirmOrderController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
             User user = (User) session.getAttribute("LOGIN_USER");
             if (user!=null) {
                if (user.getRoleID().equals("US")) {
                    url = FAIL;
                    String address = request.getParameter("address");
                    String phone = request.getParameter("phone");
                    String verification = request.getParameter("verification");
                    ShoppingCart cart = (ShoppingCart) session.getAttribute("SHOPPING_CART");
                    ProductDAO dao = new ProductDAO();
                    UserError error = new UserError();
                    boolean valid = true;
                    if (cart!=null) {
                        Iterator<String> ite = cart.keySet().iterator();
                        String productID;
                        request.setAttribute("VERIFICATION", cart.getVerification());
                        if (address.length()==0) {
                            valid = false;
                            error.setAddress_E(" Invalid Address");
                        }
                        if (!phone.matches("[0-9]{10}")) {
                            valid = false;
                            error.setPhone_E(" Invalid Phone Number");
                        }
                        if (!verification.equals(cart.getVerification())) {
                            valid = false;
                            error.setMessage_E("Invalid Verification Code");
                        } else {
                            while (ite.hasNext()) {
                                productID = ite.next(); 
                                Product item = cart.get(productID);
                                int quantity = item.getQuantity();
                                int price = item.getPrice();
                                item = dao.getItem(productID);
                                if (item!=null) {
                                    item.setPrice(price);
                                    item.setQuantity(quantity);
                                    item.setTotal();
                                    if (item.getQuantity() <= item.getCurrQuantity()) {
                                        cart.put(productID, item);
                                    } else {
                                        request.setAttribute("CART_MESSAGE", "THE ITEM ['"+cart.get(productID).getProductName()+"'] IS NOT AVAILABLE OR OUR STOCK IS NOT ENOUGH !!!");
                                        valid = false;
                                        break;
                                    }
                                } else {
                                    request.setAttribute("CART_MESSAGE", "THE ITEM ['"+cart.get(productID).getProductName()+"'] IS NOT AVAILABLE OR OUR STOCK IS NOT ENOUGH !!!");
                                    valid = false;
                                    break;
                                }
                            }
                            if (valid) {
                                url = SUCCESS;
                            }
                        }    
                    }
                    request.setAttribute("USER_ERROR", error);
                } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN TO USER TO CONTINUE !!!");
             } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN TO CONTINUE !!!");
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nERROR at ConfirmOrderController:\n", e);
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
