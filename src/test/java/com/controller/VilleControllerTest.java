package com.controller;

import com.dto.VilleDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Ville;
import com.service.VilleService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VilleController.class)
class VilleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VilleService villeServiceMock;

    @Test
    void testGetAllVilles() throws Exception {
        mockMvc.perform(get("/ville"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetVillesByCodePostal() throws Exception {
        mockMvc.perform(get("/ville?codePostal=49000"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetVilleByCodeCommuneKnown() throws Exception {
        when(villeServiceMock.getVille("49007"))
                .thenReturn(Optional.of(new Ville()));
        mockMvc.perform(get("/ville?codeCommune=49007"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetVilleByCodeCommuneUnknown() throws Exception {
        when(villeServiceMock.getVille("49007"))
                .thenReturn(Optional.empty());
        mockMvc.perform(get("/ville?codeCommune=49007"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetVilleByCodeCommuneAndCodePostal() throws Exception {
        mockMvc.perform(get("/ville?codeCommune=49007&codePostal=49000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostVille() throws Exception {
        VilleDTO requestVille = new VilleDTO("49007", "Angers", "49000", "", "", "", "");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(requestVille);
        mockMvc.perform(post("/ville")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testPostVilleAlreadyKnown() throws Exception {
        VilleDTO requestVille = new VilleDTO("49007", "Angers", "49000", "", "", "", "");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(requestVille);
        doThrow(SQLException.class)
                .when(villeServiceMock)
                .createVille(requestVille);
        mockMvc.perform(post("/ville")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isConflict());
    }

    @Test
    void testPutVille() throws Exception {
        VilleDTO requestVille = new VilleDTO("49007", "Angers", "49000", "", "", "", "");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(requestVille);
        mockMvc.perform(put("/ville")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testPutVilleUnknown() throws Exception {
        VilleDTO requestVille = new VilleDTO("49007", "Angers", "49000", "", "", "", "");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(requestVille);
        doThrow(ObjectNotFoundException.class)
                .when(villeServiceMock)
                .replaceVille(requestVille);
        mockMvc.perform(put("/ville")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteVille() throws Exception {
        mockMvc.perform(delete("/ville?codeCommune=49007"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteVilleUnknown() throws Exception {
        doThrow(ObjectNotFoundException.class)
                .when(villeServiceMock)
                .deleteVille("49007");
        mockMvc.perform(delete("/ville?codeCommune=49007"))
                .andExpect(status().isNotFound());
    }
}
