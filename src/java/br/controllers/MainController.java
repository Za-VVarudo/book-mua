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
import org.apache.log4j.Logger;

/**
 *
 * @author Death
 */
public class MainController extends HttpServlet {
    
    private final String ERROR              = "error.jsp";
    private final String LOGIN              = "LoginController";
    private final String LOGIN_GOOGLE       = "LoginWithGoogleController";
    private final String LOGOUT             = "LogoutController";
    private final String REGISTER           = "RegisterController";
    private final String SEARCH_USER        = "SearchUserController";
    private final String USER               = "user/user.jsp";
    private final String ADMIN              = "user/admin.jsp";
    private final String DELETE             = "DeleteController";
    private final String UPDATE_PAGE        = "user/update.jsp";
    private final String UPDATE_CONFIRM     = "UpdateController";
    private final String SEARCH_ITEM        = "SearchItemController";
    private final String EDIT_ITEM          = "product/editProduct.jsp";
    private final String UPDATE_ITEM        = "UpdateProductController";
    private final String DELETE_ITEM        = "DeleteItemController";
    private final String UPDATE_ITEM_PAGE   = "product/updateBook.jsp";
    private final String ADD_TO_CART        = "AddToCartController";
    private final String VIEW_CART          = "product/yourCart.jsp";
    private final String REMOVE_FROM_CART   = "RemoveFromCartController";
    private final String EDIT_CART          = "EditCartController";
    private final String CONFIRM_ORDER      = "ConfirmOrderController";
    private final String SEND_VERIFICATION_M= "SendVerificationCodeController";
    private final String UPDATE_PAYMENT     = "UpdatePaymentController";
    private final String VIEW_ORDER         = "product/payOrder.jsp";
    private final Logger LOGGER = Logger.getLogger(MainController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            if (action==null)  action = "";
            switch(action) {
                case "Login":                   url = LOGIN;                     break;
                case "LoginWithGoogle":         url = LOGIN_GOOGLE;              break;
                case "Logout":                  url = LOGOUT;                    break;
                case "Register":                url = REGISTER;                  break;
                case "Search":                  url = SEARCH_USER;               break;
                case "Admin":                   url = ADMIN;                     break;
                case "User":                    url = USER;                      break;
                case "Delete":                  url = DELETE;                    break;
                case "Update":                  url = UPDATE_PAGE;               break;
                case "Confirm Update":          url = UPDATE_CONFIRM;            break;
                case "Find Item":               url = SEARCH_ITEM;               break;
                case "Go to":                   url = SEARCH_ITEM;               break;
                case "Edit Item":               url = EDIT_ITEM;                 break;
                case "Update Item":             url = UPDATE_ITEM;               break;
                case "Delete Item":             url = DELETE_ITEM;               break;
                case "Update Item Page":        url = UPDATE_ITEM_PAGE;          break;
                case "Add to cart":             url = ADD_TO_CART;               break;
                case "Remove from cart":        url = REMOVE_FROM_CART;          break;
                case "View cart":               url = VIEW_CART;                 break;
                case "Edit quantity":           url = EDIT_CART;                 break;
                case "Confirm Order":           url = CONFIRM_ORDER;             break;
                case "Get Email Verification":  url = SEND_VERIFICATION_M;       break;   
                case "Update Payment":          url = UPDATE_PAYMENT;            break;
                case "View Order":              url = VIEW_ORDER;                break;
                default: request.setAttribute("ERROR_MESSAGE", "NOT ERROR 404 BUT FUNCTION NOT FOUND!!!!");
            }
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nERROR at MainController: \n", e);
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
