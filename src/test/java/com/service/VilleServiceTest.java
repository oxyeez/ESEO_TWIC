package com.service;

import com.dto.VilleDTO;
import com.model.Ville;
import com.repository.VilleRepo;
import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = VilleService.class)
public class VilleServiceTest {

    @Autowired
    private VilleService villeService;

    @MockBean
    private VilleRepo villeRepo;

    @Test
    public void testGetVille() {
        when(villeRepo.findById(anyString()))
                .thenReturn(Optional.of(new Ville()));
        assertThat(villeService.getVille("49007"))
                .isEqualTo(Optional.of(new Ville()));
    }

    @Test
    public void testGetVillesAllNotPaginated() {
        when(villeRepo.findByOrderByNomCommune())
                .thenReturn(List.of(new Ville(), new Ville()));
        assertThat(villeService.getVilles(null, -1, -1)
                .equals(List.of(new Ville(), new Ville())));
    }

    @Test
    public void testGetVillesAllPagineatedDefaultPage() {
        when(villeRepo.findByOrderByNomCommune(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl(List.of(new Ville(), new Ville()), PageRequest.of(0, 10), 1));
        assertThat(villeService.getVilles(null, 10, -1)
                .equals(new PageImpl(List.of(new Ville(), new Ville()), PageRequest.of(0, 10), 1)));
    }

    @Test
    public void testGetVillesAllPaginated() {
        when(villeRepo.findByOrderByNomCommune(PageRequest.of(1, 10)))
                .thenReturn(new PageImpl(List.of(new Ville(), new Ville()), PageRequest.of(1, 10), 2));
        assertThat(villeService.getVilles(null, 10, 2)
                .equals(new PageImpl(List.of(new Ville(), new Ville()), PageRequest.of(1, 10), 2)));
    }

    @Test
    public void testGetVillesByCodePostalNotPaginated() {
        when(villeRepo.findByCodePostalOrderByNomCommune(anyString()))
                .thenReturn(List.of(new Ville(), new Ville()));
        assertThat(villeService.getVilles("00000", -1, -1)
                .equals(List.of(new Ville(), new Ville())));
    }

    @Test
    public void testGetVillesByCodePostalPagineatedDefaultPage() {
        when(villeRepo.findByCodePostalOrderByNomCommune(anyString(), eq(PageRequest.of(0, 10))))
                .thenReturn(new PageImpl(List.of(new Ville(), new Ville()), PageRequest.of(0, 10), 1));
        assertThat(villeService.getVilles("00000", 10, -1)
                .equals(new PageImpl(List.of(new Ville(), new Ville()), PageRequest.of(0, 10), 1)));
    }

    @Test
    public void testGetVillesByCodePostalPaginated() {
        when(villeRepo.findByCodePostalOrderByNomCommune(anyString(), eq(PageRequest.of(1, 10))))
                .thenReturn(new PageImpl(List.of(new Ville(), new Ville()), PageRequest.of(1, 10), 2));
        assertThat(villeService.getVilles("00000", 10, 2)
                .equals(new PageImpl(List.of(new Ville(), new Ville()), PageRequest.of(1, 10), 2)));
    }

    @Test
    public void testCreateVille() throws SQLException {
        when(villeRepo.existsById(anyString()))
                .thenReturn(false);
        when(villeRepo.save(any(Ville.class)))
                .thenReturn(new Ville());
        assertThatNoException()
                .isThrownBy(() -> villeService
                        .createVille(VilleDTO.builder()
                                .codeCommune("00000")
                                .build()));
    }

    @Test
    public void testCreateVilleAlreadyExists() {
        when(villeRepo.existsById(anyString()))
                .thenReturn(true);
        assertThatThrownBy(() -> villeService
                        .createVille(VilleDTO
                                .builder()
                                .codeCommune("00000")
                                .build()))
                .isInstanceOf(SQLException.class)
                .hasMessageContaining("Primary key already exists!");
    }

    @Test
    public void testReplaceVille() throws SQLException {
        when(villeRepo.existsById(anyString()))
                .thenReturn(true);
        when(villeRepo.save(any(Ville.class)))
                .thenReturn(new Ville());
        assertThatNoException()
                .isThrownBy(() -> villeService
                        .replaceVille(VilleDTO
                                .builder()
                                .codeCommune("00000")
                                .build()));
    }

    @Test
    public void testReplaceVilleNotExists() {
        when(villeRepo.existsById(anyString()))
                .thenReturn(false);
        assertThatThrownBy(() -> villeService
                        .replaceVille(VilleDTO
                                .builder()
                                .codeCommune("00000")
                                .build()))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Record does not exists!");
    }

    @Test
    public void testDeleteVille() throws SQLException {
        when(villeRepo.existsById(anyString()))
                .thenReturn(true);
        assertThatNoException().isThrownBy(() -> villeService.deleteVille("00000"));
    }

    @Test
    public void testDeleteVilleNotExists() {
        when(villeRepo.existsById(anyString()))
                .thenReturn(false);
        assertThatThrownBy(() -> villeService.deleteVille("00000"))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Record does not exists!");
    }
}
