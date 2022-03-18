package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beans.VilleFrance;
import com.dao.DaoFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping(value="/ville")
@RestController
public class VilleController {

	@GetMapping()
	public String listVilles(@RequestParam(required  = false, value="codePostal") String codePostal) throws JsonProcessingException {
		DaoFactory dao = DaoFactory.getInstance();
		List<VilleFrance> villes = new ArrayList();
		if (codePostal == null) {
			villes = dao.getVilleFranceDao().list();
		} else {
			villes = dao.getVilleFranceDao().searchByPostcode(codePostal);
		}
		ObjectMapper mapper = new ObjectMapper();
		
		String ret = mapper.writeValueAsString(villes);
		
		return ret;
	}
	
	@PostMapping()
	public void addVille(@RequestBody VilleFrance newVille) throws JsonMappingException, JsonProcessingException {
		DaoFactory dao = DaoFactory.getInstance();
		
		dao.getVilleFranceDao().addVille(newVille);
	}
	
	
	@PutMapping()
	public void correct(@RequestBody VilleFrance newVille) {
		DaoFactory dao = DaoFactory.getInstance();

		dao.getVilleFranceDao().correctVille(newVille);
	}
	
	@DeleteMapping()
	public void deleteVille(@RequestParam(required  = true, value="codeCommune") String codeCommunet ) {
		DaoFactory dao = DaoFactory.getInstance();

		dao.getVilleFranceDao().deleteVille(codeCommune);
	}

}


