package com.blo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.blo.VilleBlo;
import com.dao.VilleDao;
import com.dto.Ville;

@Service
@Component("villeBloService")
public class VilleBloImpl implements VilleBlo {

	@Autowired
	private VilleDao villeDao;
	
	@Override
	public List<Ville> getVilles(String postCode) {
		List<Ville> listeVille = null;
		
		if (postCode == null) {
			 listeVille = villeDao.list();
		} else {
			listeVille = villeDao.searchByPostcode(postCode);
		}
		
		return listeVille;
	}
	
	@Override
	public void addVille(Ville newVille) {		
		if (newVille != null) {
			 villeDao.addVille(newVille);
		}		
	}

	@Override
	public ResponseEntity<HttpStatus> updateVille(String codeCommune, Ville newVille) {
		if (newVille != null) {
			boolean exists = villeDao.checkIfExists(codeCommune);
			if (exists) {
				villeDao.updateVille(codeCommune, newVille);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<HttpStatus> deleteVille(String codeCommune) {
		boolean exists = villeDao.checkIfExists(codeCommune);
		if (exists) {
			villeDao.deleteVille(codeCommune);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
