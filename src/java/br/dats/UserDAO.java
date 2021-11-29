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
public class UserDAO {
    
    public User loginUser( String userID, String password) throws SQLException {
        User user = null;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                String loginQ = " SELECT fullName, email, address, phone, gender, roleID, status "
                        + " FROM tblUser "
                        + " WHERE userID =? and password =? and status =1 ";
                pstm = con.prepareStatement(loginQ);
                pstm.setString(1, userID);
                pstm.setString(2, password);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    char gender = rs.getString("gender").charAt(0);
                    String roleID = rs.getString("roleID");
                    boolean status = rs.getBoolean("status");
                    user = new User(userID, fullName, "****", email, address, phone, gender, roleID, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) rs.close();
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return user;
    }
    
    public User loginGoogle( String email) throws SQLException {
        User user = null;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                String loginQ = " SELECT userID ,fullName, address, phone, gender, roleID, status "
                        + " FROM tblUser "
                        + " WHERE email =? ";
                pstm = con.prepareStatement(loginQ);
                pstm.setString(1, email);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    char gender = rs.getString("gender").charAt(0);
                    String roleID = rs.getString("roleID");
                    boolean status = rs.getBoolean("status");
                    user = new User(userID, fullName, "****", email, address, phone, gender, roleID, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) rs.close();
            if (pstm!=null) pstm.close();
            if (con!=null) con.close();
        }
        return user;
    }
    
    public ArrayList<User> searchUserList(String searchKey) throws SQLException {
        ArrayList<User> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                String loginQ = " SELECT userID, fullName, email, address, phone, gender, roleID, status "
                        + " FROM tblUser "
                        + " WHERE ( userID =? OR fullName LIKE ? ) and status =1 ";
                pstm = con.prepareStatement(loginQ);
                pstm.setString(1, searchKey);
                pstm.setString(2, "%"+searchKey+"%");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    char gender = rs.getString("gender").charAt(0);
                    String roleID = rs.getString("roleID");
                    boolean status = rs.getBoolean("status");
                    list.add( new User(userID, fullName, "****", email, address, phone, gender, roleID, status));
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
    
    public boolean registerUser(User user) throws SQLException {
        boolean valid = false;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                con.setAutoCommit(false);
                String registerQ = " INSERT INTO tblUser (userID , password, fullName, email, address, phone, gender, roleID, status) "
                        +" VALUES (?,?,?,?,?,?,?,?,?) ";
                pstm = con.prepareStatement(registerQ);
                pstm.setString(1, user.getUserID());
                pstm.setString(2, user.getPassword());
                pstm.setString(3, user.getFullName());
                pstm.setString(4, user.getEmail());
                pstm.setString(5, user.getAddress());
                pstm.setString(6, user.getPhone());
                pstm.setString(7, user.getGender()+"");
                pstm.setString(8, "US");
                pstm.setBoolean(9, true);
                
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
    
    public boolean deactiveUser (String userID) throws SQLException {
        boolean valid = false;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                con.setAutoCommit(false);
                String deactiveQ = " UPDATE tblUser "
                        +" SET status=0 "
                        +" WHERE userID =? ";
                pstm = con.prepareStatement(deactiveQ);
                pstm.setString(1, userID);
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
    
    public boolean updateUser (User user) throws SQLException {
        boolean valid = false;
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DBUtils.getConnection();
            if (con!=null) {
                con.setAutoCommit(false);
                String updateQ = " UPDATE tblUser "
                        +" SET fullName =?, email =?, address =?, phone =?, gender =?, roleID =?, status =? "
                        +" WHERE userID =? ";
                pstm = con.prepareStatement(updateQ);
                pstm.setString(1, user.getFullName());
                pstm.setString(2, user.getEmail());
                pstm.setString(3, user.getAddress());
                pstm.setString(4, user.getPhone());
                pstm.setString(5, user.getGender()+"");
                pstm.setString(6, user.getRoleID());
                pstm.setBoolean(7, user.getStatus());
                pstm.setString(8, user.getUserID());
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
    
}
