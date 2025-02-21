package com.kosmostecnologia.music_app.service;


import com.kosmostecnologia.music_app.dto.AlbumDTO;
import com.kosmostecnologia.music_app.entity.AlbumEntity;
import com.kosmostecnologia.music_app.repository.AlbumRepository;
import com.kosmostecnologia.music_app.repository.RecordCompanyRepository;
import com.kosmostecnologia.music_app.repository.TrackRepository;
import com.kosmostecnologia.music_app.util.DataDummy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AlbumServiceTest extends ServiceSpec {

    @MockBean
    private AlbumRepository albumRepositoryMock;

    @MockBean
    private TrackRepository trackRepositoryMock;

    @MockBean
    private RecordCompanyRepository recordCompanyRepositoryMock;

    @Autowired
    private IAlbumService iAlbumService;

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 2L;

    @BeforeEach
    void setMocks() {
        when(this.albumRepositoryMock.findById(VALID_ID)).thenReturn(Optional.of(DataDummy.ALBUM_ENTITY));
        when(this.albumRepositoryMock.findById(INVALID_ID)).thenReturn(Optional.empty());
        when(this.albumRepositoryMock.save(any(AlbumEntity.class))).thenReturn(DataDummy.ALBUM_ENTITY);
    }

    @AfterEach
    void resetMocks(){
        reset(this.albumRepositoryMock);
    }

    @Test
    @DisplayName("findById should works")
    void findById() {
        AlbumDTO actual = this.iAlbumService.findById(VALID_ID);
        assertEquals(DataDummy.ALBUM_DTO, actual);
        verify(albumRepositoryMock, times(1)).findById(VALID_ID);
        assertThrows(NoSuchElementException.class,
                () -> this.iAlbumService.findById(INVALID_ID));
        verify(albumRepositoryMock).findById(eq(INVALID_ID));

    }

    @Test
    @DisplayName("getAll should works")
    void getAll(){
        when(this.albumRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NoSuchElementException.class,
                ()-> this.iAlbumService.getAll());
        verify(this.albumRepositoryMock,times(1)).findAll();

        when(this.albumRepositoryMock.findAll()).thenReturn(Set.of(DataDummy.ALBUM_ENTITY));
        Set<AlbumDTO> actual = this.iAlbumService.getAll();
        assertFalse(actual.isEmpty());
        assertEquals(1,actual.size());
        verify(this.albumRepositoryMock,times(2)).findAll();
    }
    @Test
    @DisplayName("save should works")
    void save(){
        when(this.recordCompanyRepositoryMock.findById(anyString()))
                .thenReturn(Optional.of(DataDummy.RECORD_COMPANY_ENTITY));
        when(this.albumRepositoryMock.save(any(AlbumEntity.class)))
                .thenReturn(DataDummy.ALBUM_ENTITY);
        AlbumDTO expected = DataDummy.ALBUM_DTO;
        AlbumDTO actual = this.iAlbumService.save(DataDummy.ALBUM_DTO);
        assertEquals(expected,actual);
        verify(this.recordCompanyRepositoryMock,times(1)).findById(anyString());
    }

    @Test
    @DisplayName("delete should works")
    void delete(){

        // 🔹 Simulamos que deleteById no hace nada
        doNothing().when(this.albumRepositoryMock).deleteById(VALID_ID);

        // 🔹 Llamamos al método delete con un ID válido (no debe lanzar excepción)
        this.iAlbumService.delete(VALID_ID);

        // 🔹 Verificamos que se llamó a findById y deleteById una sola vez
        verify(this.albumRepositoryMock, times(1)).findById(VALID_ID);
        verify(this.albumRepositoryMock, times(1)).deleteById(VALID_ID);

        // 🔹 Simulamos que el ID inválido no existe en la base de datos
        when(this.albumRepositoryMock.findById(INVALID_ID)).thenReturn(Optional.empty());

        // 🔹 Probamos que se lance la excepción al intentar borrar un ID inexistente
        assertThrows(NoSuchElementException.class, () -> this.iAlbumService.delete(INVALID_ID));

        // 🔹 Verificamos que findById fue llamado con el ID inválido, pero NO deleteById
        verify(this.albumRepositoryMock, times(1)).findById(INVALID_ID);
        verify(this.albumRepositoryMock, never()).deleteById(INVALID_ID);
    }

    @Test
    @DisplayName("update should works")
    void updateSuccess(){

        when(this.albumRepositoryMock.save(any(AlbumEntity.class)))
                .thenReturn(DataDummy.ALBUM_ENTITY);

        AlbumDTO actual = this.iAlbumService.update(DataDummy.ALBUM_DTO,VALID_ID);
        AlbumDTO expected = DataDummy.ALBUM_DTO;

        assertEquals(expected,actual);

        verify(this.albumRepositoryMock, times(2)).findById(VALID_ID);
        verify(this.albumRepositoryMock).save(any(AlbumEntity.class));

    }

    @Test
    @DisplayName("update should throw exception")
    void updateFailure(){

        assertThrows(NoSuchElementException.class, () -> this.iAlbumService.update(DataDummy.ALBUM_DTO,INVALID_ID));
        verify(this.albumRepositoryMock).findById(INVALID_ID);
        verify(this.albumRepositoryMock, never()).save(any(AlbumEntity.class));
    }

    @Test
    @DisplayName("findBetweenPrice should works")
    void findBetweenPriceSuccess(){

        // Valores reales para la prueba
        Double minPrice = 10.0;
        Double maxPrice = 50.0;

        when(this.albumRepositoryMock.findByPriceBetween(anyDouble(),anyDouble()))
                .thenReturn(Set.of(DataDummy.ALBUM_ENTITY));

        Set<AlbumDTO> actual =  this.iAlbumService.findBetweenPrice(minPrice,maxPrice);

        assertFalse(actual.isEmpty());
        assertEquals(1,actual.size());

    }

    @Test
    @DisplayName("findBetweenPrice should throw exception when no records found")
    void findBetweenPriceNotFound(){

        // Valores reales para la prueba
        Double minPrice = 10.0;
        Double maxPrice = 50.0;

        //Simula que no hay resultados
        when(this.albumRepositoryMock.findByPriceBetween(anyDouble(),anyDouble()))
                .thenReturn(Collections.emptySet());

        //Llamamos al método
        assertThrows(NoSuchElementException.class,
                ()-> this.iAlbumService.findBetweenPrice(minPrice,maxPrice));

    }

    @Test
    @DisplayName("addTrack should works")
    void addTrackSuccess(){

        //Simula que guarda un album
        when(this.albumRepositoryMock.save(any(AlbumEntity.class)))
                .thenReturn(DataDummy.ALBUM_ENTITY);

        //Llamamos al método
        AlbumDTO actual = this.iAlbumService.addTrack(DataDummy.TRACK_1_DTO,VALID_ID);
        AlbumDTO expected = DataDummy.ALBUM_DTO;

        //Comparamos resultados
        assertEquals(expected,actual);

        //Verificamos
        verify(this.albumRepositoryMock, times(2)).findById(VALID_ID);
        verify(this.albumRepositoryMock).save(any(AlbumEntity.class));

    }

    @Test
    @DisplayName("addTrack failure")
    void addTrackFailure(){

        //Llamamos al método y comparamos resultados
        assertThrows(NoSuchElementException.class, () -> this.iAlbumService.addTrack(DataDummy.TRACK_1_DTO,INVALID_ID));

        //Verificamos
        verify(this.albumRepositoryMock).findById(INVALID_ID);
        verify(this.albumRepositoryMock, never()).save(any(AlbumEntity.class));

    }


    @Test
    @DisplayName("removeTrack success")
    void removeTrackSuccess(){

        when(this.albumRepositoryMock.save(any(AlbumEntity.class)))
                .thenReturn(DataDummy.ALBUM_ENTITY);

        //Simula que existe una canción
        when(this.trackRepositoryMock.existsById(DataDummy.TRACK_1_DTO.getTrackId()))
                .thenReturn(true);

        //Llamamos al método
        AlbumDTO actual = this.iAlbumService.removeTrack(DataDummy.TRACK_1_DTO,VALID_ID);
        AlbumDTO expected = DataDummy.ALBUM_DTO;

        //Comparamos resultados
        assertEquals(expected,actual);

        //Verificamos
        verify(this.albumRepositoryMock, times(1)).findById(VALID_ID);
        verify(this.albumRepositoryMock, times(2)).save(any(AlbumEntity.class));

    }

    @Test
    @DisplayName("removeTrack failure")
    void removeTrackFailure(){

        //Simula que NO existe una canción
        when(this.trackRepositoryMock.existsById(DataDummy.TRACK_1_DTO.getTrackId()))
                .thenReturn(false);

        //Llamamos al método y comparamos resultados
        assertThrows(NoSuchElementException.class, () -> this.iAlbumService.removeTrack(DataDummy.TRACK_1_DTO,DataDummy.TRACK_1_DTO.getTrackId()));

        //verificamos
        verify(this.albumRepositoryMock, never()).save(any(AlbumEntity.class));
    }






}
