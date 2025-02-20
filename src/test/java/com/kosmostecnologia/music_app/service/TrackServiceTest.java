package com.kosmostecnologia.music_app.service;


import com.kosmostecnologia.music_app.entity.TrackEntity;
import com.kosmostecnologia.music_app.repository.TrackRepository;
import com.kosmostecnologia.music_app.service.impl.TrackServiceImpl;
import com.kosmostecnologia.music_app.util.DataDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TrackServiceTest {

    @Mock
    private TrackRepository repositoryMock;

    @InjectMocks
    private TrackServiceImpl trackService;

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 2L;

    @BeforeEach
    void setMocks(){
        when(this.repositoryMock.findById(eq(VALID_ID)))
                .thenReturn(Optional.of(DataDummy.TRACK_1));
        when(this.repositoryMock.findById(eq(INVALID_ID)))
                .thenReturn(Optional.empty());
   }

    @Test
    @DisplayName("findById should works")
    void findByID(){
        TrackEntity actual = this.trackService.findById(VALID_ID);
        assertEquals(DataDummy.TRACK_1,actual);
        assertThrows(NoSuchElementException.class,
                ()-> this.trackService.findById(INVALID_ID));
    }

}
