package com.kosmostecnologia.music_app.service;


import com.kosmostecnologia.music_app.dto.AlbumDTO;
import com.kosmostecnologia.music_app.repository.AlbumRepository;
import com.kosmostecnologia.music_app.repository.RecordCompanyRepository;
import com.kosmostecnologia.music_app.repository.TrackRepository;
import com.kosmostecnologia.music_app.service.impl.AlbumServiceImpl;
import com.kosmostecnologia.music_app.util.DataDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.Optional;

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
                () -> {this.iAlbumService.findById(INVALID_ID); });
        verify(albumRepositoryMock).findById(eq(INVALID_ID));


    }
}
