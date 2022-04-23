package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ville_france")
public class Ville {
	
	@Id
	@Column(name="Code_commune_INSEE")
	private String codeCommune;
	
	@Column(name="Nom_commune")
	private String nomCommune;
	
	@Column(name="Code_postal")
	private String codePostal;
	
	@Column(name="Libelle_acheminement")
	private String libelleAcheminement;
	
	@Column(name="Ligne_5")
	private String ligne;
	
	@Column(name="Latitude")
	private String latitude;
	
	@Column(name="Longitude")
	private String longitude;
}