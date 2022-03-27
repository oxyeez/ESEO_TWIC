package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blo.VilleBlo;
import com.dto.Ville;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping(value="/ville")
@RestController
public class VilleController {

	@Autowired
	VilleBlo villeBloService;
	
	@GetMapping()
	@ResponseBody
	public String listVilles(@RequestParam(required=false, value="codePostal") String postCode) throws JsonProcessingException {
		List<Ville> villes = villeBloService.getVilles(postCode);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String ret = mapper.writeValueAsString(villes);
		
		return ret;
	}
	
	@PostMapping()
	public void addVille(@RequestBody Ville newVille) throws JsonMappingException, JsonProcessingException {
		villeBloService.addVille(newVille);
	}
	
	
	@PutMapping()
	public ResponseEntity<HttpStatus> correct(@RequestBody Ville newVille,
						@RequestParam(required=true, value="codeCommune") String codeCommune ) {
		return villeBloService.updateVille(codeCommune, newVille);
	}
	
	@DeleteMapping()
	public void deleteVille(@RequestParam(required=true, value="codeCommune") String codeCommune ) {
		villeBloService.deleteVille(codeCommune);
	}

}


