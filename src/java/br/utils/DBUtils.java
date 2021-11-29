/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utils;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Death
 */
public class DBUtils {
    
    public static Connection getConnection() throws SQLException, NamingException{
        Connection con = null;
        try {
            Context context = new InitialContext();
            Context end = (Context) context.lookup("java:comp/env");
            DataSource ds = (DataSource) end.lookup("DBCon");
            con = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    
    public static Connection getStaticConnection() {
        Connection con =null;
        try {
            DriverManager.registerDriver(new SQLServerDriver());
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=BOOK_MUA";
            con = DriverManager.getConnection(dbURL, "sa", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
