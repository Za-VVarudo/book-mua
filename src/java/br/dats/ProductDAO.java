/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dats;

import br.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Death
 */
public class ProductDAO {
    
    public Product getItem(String productID) throws SQLException {
        Product item = null;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                String searchQ = " SELECT productName, imageURL, author, publisher, p.categoryID, c.categoryName, currQuantity, price, status "
                        + " FROM tblProduct p join tblCategory c on p.categoryID = c.categoryID "
                        + " WHERE productID =? and status = 1";
                pstm = con.prepareStatement(searchQ);
                pstm.setString(1, productID);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    String productName = rs.getString("productName");
                    String imageURL = rs.getString("imageURL");
                    String author = rs.getString("author");
                    String publisher = rs.getString("publisher");
                    String categoryID = rs.getString("categoryID");
                    String categoryName = rs.getString("categoryName");
                    int quantity = 0;
                    int currQuantity = rs.getInt("currQuantity");
                    int price = rs.getInt("price");
                    boolean status = rs.getBoolean("status");
                    item = new Product(productID, productName, imageURL, author, publisher, categoryID, categoryName, quantity, currQuantity, price, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) rs.close();
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return item;
    }
    
    public ArrayList<Product> getItemList (String searchKey, String category) throws SQLException {
        ArrayList<Product> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                String searchQ = " SELECT productID, productName, imageURL, author, publisher, p.categoryID, c.categoryName, currQuantity, price, status "
                        + " FROM tblProduct p join tblCategory c on p.categoryID = c.categoryID "
                        + " WHERE (productID =? OR productName like ? OR author like ? OR publisher like ? ) and p.categoryID like ?";
                pstm = con.prepareStatement(searchQ);
                pstm.setString(1, searchKey);
                pstm.setString(2, "%"+searchKey+"%");
                pstm.setString(3, "%"+searchKey+"%");
                pstm.setString(4, "%"+searchKey+"%");
                pstm.setString(5, "%"+category+"%");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String imageURL = rs.getString("imageURL");
                    String author = rs.getString("author");
                    String publisher = rs.getString("publisher");
                    String categoryID = rs.getString("categoryID");
                    String categoryName = rs.getString("categoryName");
                    int quantity = 0;
                    int currQuantity = rs.getInt("currQuantity");
                    int price = rs.getInt("price");
                    boolean status = rs.getBoolean("status");
                    list.add(new Product(productID, productName, imageURL, author, publisher, categoryID,categoryName, quantity, currQuantity, price, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) rs.close();
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return list;
    }
    
    public ArrayList<Product> getNItemList (String searchKey, String category, int pageNum ) throws SQLException {
        ArrayList<Product> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                String searchQ = " SELECT productID, productName, imageURL, author, publisher, p.categoryID, c.categoryName, currQuantity, price, status "
                        + " FROM ( SELECT ROW_NUMBER() OVER ( ORDER BY productID) rowNum, productID, productName, imageURL, author, publisher, categoryID, currQuantity, price, status "
                        + "        FROM tblProduct "
                        + "        WHERE (productID =? OR productName like ? OR author like ? OR publisher like ? ) and categoryID like ? "
                        + "       ) p join tblCategory c on p.categoryID = c.categoryID "
                        + " WHERE rowNum BETWEEN ? AND ? ";
                pstm = con.prepareStatement(searchQ);
                pstm.setString(1, searchKey);
                pstm.setString(2, "%"+searchKey+"%");
                pstm.setString(3, "%"+searchKey+"%");
                pstm.setString(4, "%"+searchKey+"%");
                pstm.setString(5, "%"+category+"%");
                pstm.setInt(6, pageNum);
                pstm.setInt(7, pageNum + 4);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String imageURL = rs.getString("imageURL");
                    String author = rs.getString("author");
                    String publisher = rs.getString("publisher");
                    String categoryID = rs.getString("categoryID");
                    String categoryName = rs.getString("categoryName");
                    int quantity = 0;
                    int currQuantity = rs.getInt("currQuantity");
                    int price = rs.getInt("price");
                    boolean status = rs.getBoolean("status");
                    list.add(new Product(productID, productName, imageURL, author, publisher, categoryID,categoryName, quantity, currQuantity, price, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) rs.close();
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return list;
    }
    
    public int getMaxPageItem (String searchKey, String category) throws SQLException {
        int max = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                String searchQ = " SELECT COUNT(productID) as maxRow "
                        + " FROM tblProduct "
                        + " WHERE (productID =? OR productName like ? OR author like ? OR publisher like ? ) and categoryID like ?";
                pstm = con.prepareStatement(searchQ);
                pstm.setString(1, searchKey);
                pstm.setString(2, "%"+searchKey+"%");
                pstm.setString(3, "%"+searchKey+"%");
                pstm.setString(4, "%"+searchKey+"%");
                pstm.setString(5, "%"+category+"%");
                rs = pstm.executeQuery();
                if (rs.next()) max = rs.getInt("maxRow");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) rs.close();
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return max;
    }
    
    public ArrayList<String> getCategory( String category) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                String categoryQ = " SELECT categoryID, categoryName "
                        + " FROM tblCategory ";
                pstm = con.prepareStatement(categoryQ);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String categoryID = rs.getString("categoryID");
                    if (category.equals(categoryID)) return null;
                    String categoryName = rs.getString("categoryName");
                    list.add(categoryID + " - " + categoryName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) rs.close();
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return list;
    }
    
    public boolean updateItem (Product item) throws SQLException {
        boolean valid = false;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                con.setAutoCommit(false);
                String updateProductQ = " UPDATE tblProduct "
                        + " SET productName =?, imageURL =?, author =?, publisher =?, categoryID =?, currQuantity =?, price=?, status =? "
                        + " WHERE productID =? ";
                pstm = con.prepareStatement(updateProductQ);
                pstm.setString(1, item.getProductName());
                pstm.setString(2, item.getImageURL());
                pstm.setString(3, item.getAuthor());
                pstm.setString(4, item.getPublisher());
                pstm.setString(5, item.getCategoryID());
                pstm.setInt(6, item.getCurrQuantity());
                pstm.setInt(7, item.getPrice());
                pstm.setBoolean(8, item.isStatus());
                pstm.setString(9, item.getProductID());
                
                if (pstm.executeUpdate() == 1) valid = true;
                con.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (con!=null) con.rollback();
        } finally {
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return valid;
    }
    
    public boolean deleteItem( String productID) throws SQLException {
        boolean valid = false;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                con.setAutoCommit(false);
                String deleteItemQ = " UPDATE tblProduct "
                        + " SET currQuantity =?, status = ? "
                        + " WHERE productID =? ";
                pstm = con.prepareStatement(deleteItemQ);
                pstm.setInt(1, 0);
                pstm.setBoolean(2, false);
                pstm.setString(3, productID);
                
                if (pstm.executeUpdate() == 1) valid = true;
                con.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (con!=null) con.rollback();
        } finally {
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return valid;
    }
    
    public boolean insertOrder (Order order) throws SQLException {
        boolean valid = false;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                con.setAutoCommit(false);
                
                String insertOrderQ = " INSERT INTO tblOrder ( orderID, userID, address, phone, purchaseDate, total, paymentStatus ) "
                        + " VALUES (?,?,?,?,?,?,?) ";
                pstm = con.prepareStatement(insertOrderQ);
                pstm.setString(1, order.getOrderID());
                pstm.setString(2, order.getUserID());
                pstm.setString(3, order.getAddress());
                pstm.setString(4, order.getPhone());
                pstm.setString(5, order.getPurchaseDate());
                pstm.setInt(6, order.getTotal());
                pstm.setBoolean(7, order.isPaymentStatus());
                
                if (pstm.executeUpdate() == 1 ) valid = true;
                con.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (con!=null) con.rollback();
        } finally {
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return valid;
    }
    
    public boolean insertOrderDetail ( OrderDetail od ) throws SQLException {
        boolean valid = false;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                con.setAutoCommit(false);
                
                String insertOrderQ = " INSERT INTO tblOrderDetail ( orderID, productID, quantity, price, total ) "
                        + " VALUES (?,?,?,?,?) ";
                pstm = con.prepareStatement(insertOrderQ);
                pstm.setString(1, od.getOrderID());
                pstm.setString(2, od.getProductID());
                pstm.setInt(3, od.getQuantity());
                pstm.setInt(4, od.getPrice());
                pstm.setInt(5, od.getTotal());
                if (pstm.executeUpdate() == 1 ) valid = true;
                con.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (con!=null) con.rollback();
        } finally {
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return valid;
    }
    
}
