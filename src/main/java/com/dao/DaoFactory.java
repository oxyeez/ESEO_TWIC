package com.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dao.impl.VilleDaoImpl;

@Component("daoFactory")
public class DaoFactory {
	
	@Value("${springboot.datasource.url}")
    private String url;
	
	@Value("${springboot.datasource.username}")
    private String username;
	
	@Value("${springboot.datasource.password}")
    private String password;
	
	@Value("${spring.datasource.driver-class-name}")
	private static String driverClassName;

    DaoFactory() {}

    public static DaoFactory getInstance() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
        }
        return new DaoFactory();
    }

    public Connection getConnection() throws SQLException {
        Connection connexion = DriverManager.getConnection(url, username, password);
        connexion.setAutoCommit(false);
        return connexion; 
    }
    
    public VilleDao getVilleFranceDao() {
    	return new VilleDaoImpl();
    }

}