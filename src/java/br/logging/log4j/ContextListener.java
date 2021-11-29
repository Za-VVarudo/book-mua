/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.logging.log4j;

import java.io.File;
import java.sql.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.PropertyConfigurator;

/**
 * Web application lifecycle listener.
 *
 * @author Death
 */
@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("/") + File.separator + log4jConfigFile;
        System.setProperty("PATH",context.getRealPath("/"));
        System.setProperty("DATE", new Date(System.currentTimeMillis()).toString());
        PropertyConfigurator.configure(fullPath);
    }

    public void contextDestroyed(ServletContextEvent event) {
        
    }
    
}
