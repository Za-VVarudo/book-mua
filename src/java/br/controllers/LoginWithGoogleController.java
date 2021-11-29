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
import br.dats.User;
import br.dats.UserDAO;
import br.utils.LoginWithGoogleUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Death
 */
public class LoginWithGoogleController extends HttpServlet {
    
    private final String ERROR = "error.jsp";
    private final String EXISTED = "MainController?action=Search";
    private final String NEW = "register.jsp";
    private final String LOGIN = "login.jsp";
    private final Logger LOGGER = Logger.getLogger(LoginWithGoogleController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
            String code = request.getParameter("code");
            if (code != null && !code.isEmpty()) {
                String accessToken = LoginWithGoogleUtils.getToken(code);
                User googleUser = LoginWithGoogleUtils.getLoginGoogle(accessToken);
                UserDAO dao = new UserDAO();
                User user = dao.loginGoogle(googleUser.getEmail());
                if (user== null) {
                    session.setAttribute("LOGIN_GOOGLE", googleUser);
                    url = NEW;
                } else {
                    if (user.getStatus()) {
                        session.setAttribute("LOGIN_USER", user);
                        url = EXISTED;
                    } else {
                        request.setAttribute("ERROR_MESSAGE", "Account not exist or You have been banned !!!");
                    }
                }
            } else request.setAttribute("ERROR_MESSAGE", "Error in Login with GOOGLE !!!");
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nERROR at LoginWithGoogleController: \n", e);
        } finally {
            if (url.equals(ERROR)) request.getRequestDispatcher(url).forward(request, response);
            else response.sendRedirect(url);
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
