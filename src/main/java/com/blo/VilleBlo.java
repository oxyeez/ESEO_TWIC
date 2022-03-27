package com.blo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dto.Ville;

public interface VilleBlo {

	public List<Ville> getVilles(String postCode);
	public void addVille(Ville newVille);
	public ResponseEntity<HttpStatus> updateVille(String codeCommune, Ville newVille);
	public ResponseEntity<HttpStatus> deleteVille(String codeCommune);
	
}
