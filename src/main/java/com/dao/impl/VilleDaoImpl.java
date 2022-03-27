package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.DaoFactory;
import com.dao.VilleDao;
import com.dto.Ville;


@Component("villeDao")
public class VilleDaoImpl implements VilleDao {
	
	@Autowired
	private DaoFactory daoFactory;
	
	@Override
    public List<Ville> list() {
        List<Ville> villes = new ArrayList<Ville>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM ville_france;");

            while (resultat.next()) {
            	Ville ville = new Ville();

            	ville.setCodeCommune(resultat.getString("code_commune_INSEE"));
            	ville.setNomCommune(resultat.getString("nom_commune"));
            	ville.setCodePostal(resultat.getString("code_postal"));
            	ville.setLibelleAcheminement(resultat.getString("libelle_acheminement"));
            	ville.setLigne(resultat.getString("ligne_5"));
            	ville.setLatitude(resultat.getString("latitude"));
            	ville.setLongitude(resultat.getString("longitude"));            	
            	
                villes.add(ville);
                connexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return villes;
    }

	@Override
	public List<Ville> searchByPostcode(String postcode) {
        List<Ville> villes = new ArrayList<Ville>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM ville_france WHERE code_postal = '"+postcode+"';");
            while (resultat.next()) {
            	Ville ville = new Ville();

            	ville.setCodeCommune(resultat.getString("code_commune_INSEE"));
            	ville.setNomCommune(resultat.getString("nom_commune"));
            	ville.setCodePostal(resultat.getString("code_postal"));
            	ville.setLibelleAcheminement(resultat.getString("libelle_acheminement"));
            	ville.setLigne(resultat.getString("ligne_5"));
            	ville.setLatitude(resultat.getString("latitude"));
            	ville.setLongitude(resultat.getString("longitude"));            	
            	
                villes.add(ville);
                connexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return villes;
	}

	@Override
	public boolean checkIfExists(String codeCommune) {
        boolean exists = false;
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT EXISTS "
            								+ "(SELECT * "
            								+ "	FROM ville_france "
            								+ "	WHERE code_commune_INSEE = '" + codeCommune + "') AS ville_exists;");
            while (resultat.next()) {
            	exists = resultat.getBoolean("ville_exists");
                connexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
	}

	@Override
	public void addVille(Ville ville) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO ville_france VALUES(?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, ville.getCodeCommune());
            preparedStatement.setString(2, ville.getNomCommune());
            preparedStatement.setString(3, ville.getCodePostal());
            preparedStatement.setString(4, ville.getLibelleAcheminement());
            preparedStatement.setString(5, ville.getLigne());
            preparedStatement.setString(6, ville.getLatitude());
            preparedStatement.setString(7, ville.getLongitude());

            preparedStatement.executeUpdate();
            connexion.commit();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	@Override
	public void updateVille(String codeCommune, Ville ville) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            String query = "UPDATE ville_france\n"
					 	 + "SET" + (ville.getNomCommune() == null ? "" : " Nom_commune = '" + ville.getNomCommune() + "',")
					 		 	 + (ville.getCodePostal() == null ? "" : " Code_postal = '" + ville.getCodePostal() + "',")
					 		 	 + (ville.getLibelleAcheminement() == null ? "" : " Libelle_acheminement = '" + ville.getLibelleAcheminement() + "',")
							 	 + (ville.getLigne() == null ? "" : " Ligne_5 = '" + ville.getLigne() + "',")
							 	 + (ville.getLatitude() == null ? "" : " Latitude = '" + ville.getLatitude() + "',")
							 	 + (ville.getLongitude() == null ? "" : " Longitude = '" + ville.getLongitude() + "',")
					 	 + "WHERE Code_commune_INSEE = ?;";
            query = query.substring(0, query.lastIndexOf(',')) + "\n" + query.substring(query.lastIndexOf(',')+1);
            
            preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, codeCommune);
            
            preparedStatement.executeUpdate();
            connexion.commit();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void deleteVille(String codeCommune) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();            
            preparedStatement = connexion.prepareStatement("DELETE FROM ville_france WHERE Code_commune_INSEE = ?;");
            preparedStatement.setString(1, codeCommune);
            
            preparedStatement.executeUpdate();
            connexion.commit();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
