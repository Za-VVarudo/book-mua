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
import br.dats.UserError;
import br.utils.ReCaptchaUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Death
 */
public class RegisterController extends HttpServlet {
    
    private final String ERROR = "register.jsp";
    private final String SUCCESS = "MainController?action=Search";
    private final Logger LOGGER = Logger.getLogger(RegisterController.class);
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
            UserError error = new UserError();
            String reCaptchaCode = request.getParameter("g-recaptcha-response");
            
            if (reCaptchaCode!=null && ReCaptchaUtils.isCaptchaValid(reCaptchaCode)) {
                String userID = request.getParameter("userID");
                String fullName = new String(request.getParameter("fullName").getBytes("iso-8859-1"),"UTF-8");
                String email = request.getParameter("email");
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                char gender = request.getParameter("gender").charAt(0);
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");
                String emailFormat = "^\\w+[.]{0,1}\\w+@\\w+\\.\\w+[.]{0,1}\\w+$";
                UserDAO dao = new UserDAO();
                boolean check = true;
                if (userID.length()<2 || userID.length()>30) {
                    error.setUserID_E("User ID must be in [2,30] characters ");
                    check = false;
                }
                if (fullName.length()<2 || fullName.length()>50) {
                    error.setFullName_E("Full Name must be in [2,50] characters ");
                    check = false;
                }
                if (!password.equals(confirmPassword)) {
                    error.setConfirmPassword_E("Those passwords did not match");
                    check = false;
                }
                /*
                PASSWORD CHECK
                */
                if (!email.matches(emailFormat)) {
                    error.setEmail_E("Wrong format. Only letters (a-z,A-Z), numbers (0-9), and periods (.) are allowed in email username");
                    check=false;
                }
                if (dao.loginGoogle(email)!=null) {
                    error.setEmail_E("The email is registered ");
                    check = false;
                }
                if (phone.length()!=10) {
                    error.setPhone_E("Phone number must be 10 digits");
                    check = false;
                }
                if (gender!='F' && gender!='M' && gender!='O') {
                    error.setGender_E("Your gender must be either Female, Male, or Other");
                    check = false;
                }
                if (session.getAttribute("LOGIN_USER")!=null) {
                    request.getRequestDispatcher("MainController?action=Logout").include(request, response);
                }
                if (check) {
                    User user = new User(userID, fullName, password, email, address, phone, gender, "US", true);
                    boolean valid = dao.registerUser(user);
                    if (valid) {
                        url = SUCCESS;
                        user.setPassword("****");
                        session.setAttribute("LOGIN_USER", user);
                        session.removeAttribute("email");
                    } else error.setUserID_E("User ID existed !!!");
                }
            } else error.setMessage_E("WRONG CAPTCHA !!!");
            request.setAttribute("USER_ERROR", error);
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nERROR at RegisterController: \n", e);
        } finally {
            if (url.equals(SUCCESS)) response.sendRedirect(url);
            else request.getRequestDispatcher(url).forward(request, response);
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
