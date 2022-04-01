package com.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Ville;
import com.service.VilleService;

import javassist.tools.rmi.ObjectNotFoundException;

@RequestMapping(value = "/ville")
@RestController
public class VilleController {

	@Autowired
	VilleService villeService;

	@GetMapping()
	@ResponseBody
	public String listVilles(@RequestParam(required = false, value = "codePostal") String codePostal)
			throws JsonProcessingException {
		List<Ville> villes = villeService.getVilles(codePostal);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(villes);
	}

	@PostMapping()
	public void addVille(@RequestBody Ville newVille, HttpServletResponse response) {
		try {
			villeService.createVille(newVille);
		} catch (SQLException e) {
			response.setStatus(HttpStatus.CONFLICT.value());
		}
	}

	@PutMapping()
	public void replace(@RequestBody Ville newVille, HttpServletResponse response) {
		try {
			villeService.replaceVille(newVille);
		} catch (ObjectNotFoundException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}

	@DeleteMapping()
	public void deleteVille(@RequestParam(required = true, value = "codeCommune") String codeCommune,
			HttpServletResponse response) {
		try {
			villeService.deleteVille(codeCommune);
		} catch (ObjectNotFoundException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}
}
