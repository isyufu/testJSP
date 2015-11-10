/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.web.core;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
/**
 * Initialising EntityManagerFactory
 * @author Ivan Syufu
 */
@WebListener
public class EMF implements ServletContextListener {

    private static final Logger logger = Logger.getLogger("ContextListener");
    private static EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent e) {
        Map prop = new HashMap<String, String>();
        try {
            Context initContext = new InitialContext();
            String dialect  = (String)initContext.lookup("java:comp/env/webDialect");
            String ds  = (String)initContext.lookup("java:comp/env/webDataSource");
            prop.put("hibernate.dialect", dialect);
            prop.put("hibernate.connection.datasource", ds);
        } catch (NamingException ex) {
            logger.log(Level.SEVERE, "Not found dialect in context", ex);
        }

        emf = Persistence.createEntityManagerFactory("webJPA", prop);
        DataUtil.setEmf(emf);
        logger.info("TestApp init");
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        emf.close();
        logger.info("TestApp destroy");
    }

}
