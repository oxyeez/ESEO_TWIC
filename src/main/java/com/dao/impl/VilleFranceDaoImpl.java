package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.beans.VilleFrance;
import com.dao.DaoFactory;
import com.dao.VilleFranceDao;

public class VilleFranceDaoImpl implements VilleFranceDao {
	private DaoFactory daoFactory;
	
	public VilleFranceDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

	@Override
    public List<VilleFrance> list() {
        List<VilleFrance> villes = new ArrayList<VilleFrance>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM ville_france;");

            while (resultat.next()) {
            	String codeCommune = resultat.getString("code_commune_INSEE");
            	String nomCommune = resultat.getString("nom_commune");
            	String codePostal = resultat.getString("code_postal");
            	String libelleAcheminement = resultat.getString("libelle_acheminement");
            	String ligne5 = resultat.getString("ligne_5");
            	String latitude = resultat.getString("latitude");
            	String longitude = resultat.getString("longitude");
            	
            	VilleFrance ville = new VilleFrance(codeCommune, nomCommune, codePostal, libelleAcheminement, ligne5, latitude, longitude);

                villes.add(ville);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return villes;
    }

	@Override
	public List<VilleFrance> searchByPostcode(String postcode) {
        List<VilleFrance> villes = new ArrayList<VilleFrance>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM ville_france WHERE code_postal = '"+postcode+"';");
            while (resultat.next()) {
            	String codeCommune = resultat.getString("code_commune_INSEE");
            	String nomCommune = resultat.getString("nom_commune");
            	String codePostal = resultat.getString("code_postal");
            	String libelleAcheminement = resultat.getString("libelle_acheminement");
            	String ligne5 = resultat.getString("ligne_5");
            	String latitude = resultat.getString("latitude");
            	String longitude = resultat.getString("longitude");
            	
            	VilleFrance ville = new VilleFrance(codeCommune, nomCommune, codePostal, libelleAcheminement, ligne5, latitude, longitude);

                villes.add(ville);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return villes;
	}

	@Override
	public void addVille(VilleFrance ville) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO ville_france VALUES(?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, ville.getCodeCommune());
            preparedStatement.setString(2, ville.getNomCommune());
            preparedStatement.setString(3, ville.getCodePostal());
            preparedStatement.setString(4, ville.getLibelleAcheminement());
            preparedStatement.setString(5, ville.getLigne5());
            preparedStatement.setString(6, ville.getLatitude());
            preparedStatement.setString(7, ville.getLongitude());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		DaoFactory dao = DaoFactory.getInstance();
		VilleFrance newVille = new VilleFrance("53223", "SAINT GERMAIN DE COULAMER", "53700", "SAINT GERMAIN DE COULAMER", "", "48.2644444", "-0.17055555555555554");
		dao.getVilleFranceDao().addVille(newVille);
	}
}
