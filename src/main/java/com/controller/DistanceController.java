package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.DistanceService;

import javassist.tools.rmi.ObjectNotFoundException;

@RequestMapping(value = "/ville/distance/")
@RestController
public class DistanceController {

	@Autowired
	DistanceService distanceService;

	@GetMapping()
	@ResponseBody
	public int listVilles(@RequestParam(required = true, value = "ville1") String codeCommune1,
							@RequestParam(required = true, value = "ville2") String codeCommune2, 
							HttpServletResponse response)
			throws ObjectNotFoundException {
		int distance = 0;
		try {
			distance = distanceService.distanceBetween(codeCommune1, codeCommune2);
		} catch (ObjectNotFoundException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return distance;
	}
}
