/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.controllers;

import br.dats.Product;
import br.dats.ProductDAO;
import br.dats.ProductError;
import br.dats.User;
import java.io.IOException;
import java.util.ArrayList;
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
public class UpdateProductController extends HttpServlet {
    
    private final String ERROR = "error.jsp";
    private final String FAIL = "MainController?action=Update+Item+Page";
    private final String SUCCESS = "MainController?action=Find+Item";
    private final Logger LOGGER = Logger.getLogger(UpdateProductController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("LOGIN_USER");
            if (user!=null) {
                if (user.getRoleID().equals("AD")) {
                    ProductError error = new ProductError();
                    String productID = request.getParameter("productID");
                    String productName = request.getParameter("productName");
                    String imageURL = request.getParameter("imageURL");
                    String author = request.getParameter("author");
                    String publisher = request.getParameter("publisher");
                    String categoryID = request.getParameter("categoryID");
                    int currQuantity = (int) Double.parseDouble(request.getParameter("currQuantity"));
                    int price = (int) Double.parseDouble(request.getParameter("price"));
                    boolean status = request.getParameter("status").equals("1") ;
                    if (productID!=null) {
                        url = FAIL;
                        ProductDAO dao = new ProductDAO();
                        String imgURL_Format = "images/\\w+[.]{1}\\w+";
                        ArrayList<String> categoryList = dao.getCategory(categoryID);
                        boolean check = true;
                        if (!imageURL.matches(imgURL_Format)) {
                            error.setImageURL_E(" Image must be in images/ folder. Eg: images/something.jpg");
                            check = false;
                        }
                        if (categoryList!=null) {
                            error.setCategoryID_E("CategoryID must be in: "+categoryList.toString());
                            check = false;
                        }
                        if (check) {
                            Product item = new Product(productID, productName, imageURL, author, publisher, categoryID, categoryID, 0, currQuantity, price, status);
                            if (currQuantity==0) item.setStatus(false);
                            else if (status == false) item.setCurrQuantity(0);
                            boolean valid = dao.updateItem(item);
                            if (valid) {
                                url = SUCCESS;
                                request.setAttribute("EDIT_MESSAGE", " Updated ");
                            } else error.setMessage_E("Update failed !!");
                        }
                        request.setAttribute("PRODUCT_ERROR", error);
                    } else request.setAttribute("ERROR_MESSAGE", "PRODUCT NOT FOUND !!!");
                } else request.setAttribute("ERROR_MESSAGE", "YOU DONT HAVE PERMISSION !!!");
            } else request.setAttribute("ERROR_MESSAGE", "PLEASE LOGIN TO CONTINUE !!!");
        } catch (Exception e) {
            LOGGER.info("\n\n---------------------------------------------------------------------------\n");
            LOGGER.error("\nERROR at UpdateProductController: \n", e);
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
