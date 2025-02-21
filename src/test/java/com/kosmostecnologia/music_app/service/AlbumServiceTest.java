package com.kosmostecnologia.music_app.service;


import com.kosmostecnologia.music_app.dto.AlbumDTO;
import com.kosmostecnologia.music_app.entity.AlbumEntity;
import com.kosmostecnologia.music_app.repository.AlbumRepository;
import com.kosmostecnologia.music_app.repository.RecordCompanyRepository;
import com.kosmostecnologia.music_app.repository.TrackRepository;
import com.kosmostecnologia.music_app.service.impl.AlbumServiceImpl;
import com.kosmostecnologia.music_app.util.DataDummy;
import com.kosmostecnologia.music_app.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;
import java.util.stream.Collectors;

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
        when(this.albumRepositoryMock.findById(VALID_ID))
                .thenReturn(Optional.of(DataDummy.ALBUM));
        when(this.albumRepositoryMock.findById(INVALID_ID))
                .thenReturn(Optional.empty());
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

        when(this.albumRepositoryMock.findAll()).thenReturn(Set.of(DataDummy.ALBUM));
        Set<AlbumDTO> actual = this.iAlbumService.getAll();
        assertFalse(actual.isEmpty());
        assertEquals(1,actual.size());
        verify(this.albumRepositoryMock,times(2)).findAll();
    }
    @Test
    @DisplayName("save should works")
    void save(){
        when(this.recordCompanyRepositoryMock.findById(anyString()))
                .thenReturn(Optional.of(DataDummy.RECORD_COMPANY));
        when(this.albumRepositoryMock.save(any(AlbumEntity.class)))
                .thenReturn(DataDummy.ALBUM);
        AlbumDTO expected = DataDummy.ALBUM_DTO;
        AlbumDTO actual = this.iAlbumService.save(DataDummy.ALBUM_DTO);
        assertEquals(expected,actual);
        verify(this.recordCompanyRepositoryMock,times(1)).findById(anyString());
    }



}
