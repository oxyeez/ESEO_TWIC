package com.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dao.impl.VilleFranceDaoImpl;

//@Component
public class DaoFactory {
	@Value("${springboot.datasource.url}")
    private String url;
	
	@Value("${springboot.datasource.username}")
    private String username;
	
	@Value("${springboot.datasource.password}")
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
        DaoFactory instance = new DaoFactory(
                "jdbc:mariadb://localhost:3306/TWIC", "twic", "twic123");
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection connexion = DriverManager.getConnection(url, username, password);
        connexion.setAutoCommit(false);
        return connexion; 
    }
    
    public VilleFranceDao getVilleFranceDao() {
    	return new VilleFranceDaoImpl(this);
    }

}