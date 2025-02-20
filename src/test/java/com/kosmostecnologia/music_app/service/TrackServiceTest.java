package com.kosmostecnologia.music_app.service;


import com.kosmostecnologia.music_app.dto.TrackDTO;
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
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class TrackServiceTest extends ServiceSpec{

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

    @Test
    @DisplayName("getAll should works")
    void getAll(){
        Set<TrackEntity> expected = Set.of(DataDummy.TRACK_4,DataDummy.TRACK_2);
        when(this.repositoryMock.findAll()).thenReturn(expected);
        Set<TrackEntity> actual = this.trackService.getAll();
        assertEquals(2,actual.size());
        assertEquals(expected,actual);
    }

    @Test
    @DisplayName("save should works")
    void save(){
        this.trackService.save(DataDummy.TRACK_2);
        //NO ES NECESARIO USAR EL doNothing(), PORQUE LO CONFIGURA EL SpringBootTest
        verify(this.repositoryMock,times(1)).save(any(TrackEntity.class));
    }

    @Test
    @DisplayName("delete should works")
    void delete(){
        this.trackService.delete(VALID_ID);
        verify(this.repositoryMock,times(1)).deleteById(VALID_ID);
    }

    @Test
    @DisplayName("update should works")
    void update(){

        when(this.repositoryMock.existsById(VALID_ID)).thenReturn(true);
        when(this.repositoryMock.existsById(INVALID_ID)).thenReturn(false);
        when(this.repositoryMock.save(any(TrackEntity.class))).thenReturn(DataDummy.TRACK_2);

        TrackEntity actual = this.trackService.update(DataDummy.TRACK_1,VALID_ID);

        assertEquals(DataDummy.TRACK_2,actual);
        assertThrows(NoSuchElementException.class,
                ()-> this.trackService.update(DataDummy.TRACK_1,INVALID_ID));
        //PODRÍA SER OPCIONAL
        verify(this.repositoryMock,times(2)).existsById(anyLong());

    }

}
