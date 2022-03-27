package com.dao;

import java.util.List;

import com.dto.Ville;

public interface VilleDao {
	public List<Ville> list();
	public List<Ville> searchByPostcode(String postcode);
	public boolean checkIfExists(String codeCommune);
	public void addVille(Ville ville);
	public void updateVille(String codeCommune, Ville ville);
	public void deleteVille(String codeCommune);
}