package com.controller;

import com.dto.VilleDTO;
import com.model.Ville;
import com.service.VilleService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Optional;

@RequestMapping(value = "/ville")
@RestController
public class VilleController {

	@Autowired
	VilleService villeService;

	@GetMapping()
	@ResponseBody
	public Object listVilles(@RequestParam(required = false, value = "codePostal") String codePostal,
			@RequestParam(required = false, value = "codeCommune") String codeCommune, 
			@RequestParam(required = false, value = "size", defaultValue = "-1") int size,
			@RequestParam(required = false, value = "page", defaultValue = "-1") int page,
			HttpServletResponse response) {
		if (codeCommune != null && codePostal != null) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		} else if (codeCommune != null) {
			Optional<Ville> ville = villeService.getVille(codeCommune);
			if (ville.isPresent()) {
				return ville.get();
			} else {
				response.setStatus(HttpStatus.NOT_FOUND.value());
				return null;
			}
		} else {
			return villeService.getVilles(codePostal, size, page);
		}
	}

	@PostMapping()
	public void addVille(@RequestBody VilleDTO newVille, HttpServletResponse response) {
		try {
			villeService.createVille(newVille);
		} catch (SQLException e) {
			response.setStatus(HttpStatus.CONFLICT.value());
		}
	}

	@PutMapping()
	public void replace(@RequestBody VilleDTO newVille, HttpServletResponse response) {
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
