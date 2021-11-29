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
import org.apache.log4j.Logger;

/**
 *
 * @author Death
 */
public class UpdateController extends HttpServlet {
    
    private final String ERROR = "error.jsp";
    private final String SUCCESS = "MainController?action=Search";
    private final String FAIL = "MainController?action=Update";
    private final String LOGOUT = "MainController?action=Logout";
    private final Logger LOGGER = Logger.getLogger(UpdateController.class);
    
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("LOGIN_USER");
            if (user!=null) {
                if (user.getRoleID().equals("AD")) {
                    String userID = request.getParameter("userID");
                    String fullName = new String(request.getParameter("fullName").getBytes("iso-8859-1"),"UTF-8");
                    char gender = request.getParameter("gender").charAt(0);
                    String email = request.getParameter("email");
                    String address = request.getParameter("address");
                    String phone = request.getParameter("phone");
                    String roleID = request.getParameter("roleID");
                    String emailFormat = "^\\w+[.]{0,1}\\w+@\\w+\\.\\w+[.]{0,1}\\w+$";
                    UserError error = new UserError();
                    UserDAO dao = new UserDAO();
                    User updateUser;
                    boolean check = true;
                    
                    if (userID!=null) {
                        if (fullName.length()<2 || fullName.length()>50) {
                            error.setFullName_E("Full Name must be in [2,50] characters");
                            check = false;
                        }
                        if (gender!='F' && gender!='M' && gender!='O') {
                            error.setGender_E("Your gender must be either Female, Male, or Other");
                            check = false;
                        }
                        if (!roleID.equals("AD") && !roleID.equals("US")) {
                            error.setRoleID_E("Role ID must be either 'AD' or 'US'");
                            check = false;
                        }
                        if (!email.matches(emailFormat)) {
                            error.setEmail_E("Wrong fotmat. Only letters (a-z,A-Z), numbers (0-9), and periods (.) are allowed in email username");
                            check = false;
                        }
                        if ((updateUser =dao.loginGoogle(email))!=null && !updateUser.getUserID().equals(userID)) {
                            error.setEmail_E("The email is registered ");
                            check = false;
                        }
                        if (phone.length()!=10) {
                            error.setPhone_E("Your phone number must be 10 digits");
                            check = false;
                        }
                        if (check) {           
                            updateUser = new User(userID, fullName, "****", email, address, phone, gender, roleID, true);
                            boolean valid = dao.updateUser(updateUser);
                            if (valid) {
                                url = SUCCESS;
                                if (updateUser.getUserID().equals(user.getUserID())) {
                                    url = LOGOUT;
                                }
                                request.setAttribute("EDIT_MESSAGE", " Updated ");
                            } else request.setAttribute("ERROR_MESSAGE", "UPDATE PROCESS FAILED !!!");
                        } else url = FAIL;
                        request.setAttribute("USER_ERROR", error);
                    } else request.setAttribute("ERROR_MESSAGE", "USER NOT FOUND !!!");
                    
                } else request.setAttribute("ERROR_MESSAGE", "YOU DONT HAVE PERMISSION !!!");
            } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN TO CONTINUE !!!");
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nERROR at UpdateController: \n", e);
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
