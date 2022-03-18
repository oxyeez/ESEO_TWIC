package com.dao;

import java.util.List;

import com.beans.VilleFrance;

public interface VilleFranceDao {
	public List<VilleFrance> list();
	public List<VilleFrance> searchByPostcode(String postcode);
	public void addVille(VilleFrance ville);
	public void correctVille(VilleFrance ville);
	public void deleteVille(String codeCommune);
}