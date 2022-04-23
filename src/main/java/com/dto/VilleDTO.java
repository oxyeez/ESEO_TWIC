package com.dto;

import com.model.Ville;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VilleDTO {

    private String codeCommune;
    private String nomCommune;
    private String codePostal;
    private String libelleAcheminement;
    private String ligne;
    private String latitude;
    private String longitude;

    public Ville toEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Ville.class);
    }
}
