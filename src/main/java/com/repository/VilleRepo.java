package com.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Ville;

@Repository
public interface VilleRepo extends JpaRepository<Ville, String> {

	List<Ville> findByCodePostalOrderByNomCommune(String codePostal);
	Page<Ville> findByCodePostalOrderByNomCommune(String codePostal, Pageable pageable);
	List<Ville> findByOrderByNomCommune();
	Page<Ville> findByOrderByNomCommune(Pageable pageable);
}