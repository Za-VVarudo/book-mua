/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.dats.User;
import br.dats.UserDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Death
 */
public class SearchUserController extends HttpServlet {
    
    private final String ERROR = "error.jsp";
    private final String ADMIN = "MainController?action=Admin";
    private final String USER  = "MainController?action=User";
    private final String LOGIN  = "login.jsp";
    private final Logger LOGGER = Logger.getLogger(SearchUserController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url =ERROR;
        
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("LOGIN_USER");           
            if (user!=null) {   
                String searchKey = request.getParameter("searchKey");
                switch (user.getRoleID()) {
                    case "AD" : url =ADMIN; 
                        if (searchKey != null) {
                            UserDAO dao = new UserDAO();
                            ArrayList<User> list = dao.searchUserList(searchKey);
                            if (!list.isEmpty()) {
                                request.setAttribute("SEARCH_USER", list);
                            }
                        }
                        break;
                    case "US" : url =USER;  break;
                    default: request.setAttribute("ERROR_MESSAGE", "ROLE NOT SUPPPORTED !!!");
                }                
            } else  url = LOGIN;
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nERROR at SearchUserController: \n", e);
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
