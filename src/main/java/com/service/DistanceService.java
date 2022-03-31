package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Ville;
import com.repository.VilleRepo;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class DistanceService {

    @Autowired
    private VilleRepo villeRepo;

    public int distanceBetween(String codeCommune1, String codeCommune2) throws ObjectNotFoundException {
    	if (!villeRepo.existsById(codeCommune1) || !villeRepo.existsById(codeCommune2)) {
    		throw new ObjectNotFoundException("Record does not exists!");
    	}
    	Ville ville1 = villeRepo.findById(codeCommune1).get();
    	Ville ville2 = villeRepo.findById(codeCommune2).get();
    	
        double lon1 = Double.parseDouble(ville1.getLongitude());
        double lon2 = Double.parseDouble(ville2.getLongitude());
        double lat1 = Double.parseDouble(ville1.getLatitude());
        double lat2 = Double.parseDouble(ville2.getLatitude());
        
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                 + Math.cos(lat1) * Math.cos(lat2)
                 * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));
        
        double r = 6371*1000;
        
        return (int) Math.round(c * r);
    }
}
