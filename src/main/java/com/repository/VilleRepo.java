package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Ville;

@Repository
public interface VilleRepo extends JpaRepository<Ville, String> {

	List<Ville> findByCodePostal(String codePostal);

}