package com.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Ville;
import com.repository.VilleRepo;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class VilleService {

    @Autowired
    private VilleRepo villeRepo;

    public Optional<Ville> getVille(final String codeCommune) {
        return villeRepo.findById(codeCommune);
    }
    
    public List<Ville> getVilles(final String codePostal) {
    	if (codePostal == null) {
    		return villeRepo.findAll();
    	} else {
    		return villeRepo.findByCodePostal(codePostal);
    	}
    }

    public void createVille(Ville ville) throws SQLException {
    	if (villeRepo.existsById(ville.getCodeCommune())) {
    		throw new SQLException("Primary key already exists!");
    	}
    	villeRepo.save(ville);
    }

    public void replaceVille(final Ville ville) throws ObjectNotFoundException {
    	if (!villeRepo.existsById(ville.getCodeCommune())) {
    		throw new ObjectNotFoundException("Record does not exists!");
    	}
    	villeRepo.save(ville);
    }
    
    public void deleteVille(final String id) throws ObjectNotFoundException {
    	if (!villeRepo.existsById(id)) {
    		throw new ObjectNotFoundException("Record does not exists!");
    	}
    	villeRepo.deleteById(id);
    }
}