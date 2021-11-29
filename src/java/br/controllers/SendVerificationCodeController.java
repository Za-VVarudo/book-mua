/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.controllers;

import br.dats.ShoppingCart;
import br.dats.User;
import br.dats.UserError;
import br.utils.EmailVerifyService;
import java.io.IOException;
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
public class SendVerificationCodeController extends HttpServlet {
    
    private final String ERROR = "error.jsp";
    private final String SUCCESS = "MainController?action=View+cart";
    private final Logger LOGGER = Logger.getLogger(SendVerificationCodeController.class);

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
                    if (cart!=null) {
                        boolean valid = true;
                        String address = request.getParameter("address");
                        String phone = request.getParameter("phone");
                        UserError error = new UserError();
                        if (address.length()==0) {
                            valid = false;
                            error.setAddress_E(" Invalid Address");
                        }
                        if (!phone.matches("[0-9]{10}")) {
                            valid = false;
                            error.setPhone_E(" Invalid Phone Number");
                        }
                        if ( valid) {
                            String email = user.getEmail();
                            String verification = UUID.randomUUID().toString().toUpperCase().substring(0, 7);
                            EmailVerifyService.sendVerificationEmail(verification, user);
                            request.setAttribute("VERIFICATION", verification);
                            request.setAttribute("CART_MESSAGE", "Email Verification code send to "+user.getEmail()+" !!!");
                        }
                        url = SUCCESS;
                        request.setAttribute("USER_ERROR", error);
                    } else request.setAttribute("ERROR_MESSAGE", "PLEASE BUY SOMETHIGN FIRST !!!");
                } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN TO USER TO CONTINUE !!!");
            } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN TO CONTINUE !!!");
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nERROR at SendVerificationCodeController: \n", e);
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
