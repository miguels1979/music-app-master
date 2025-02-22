package com.kosmostecnologia.music_app.repository;

import com.kosmostecnologia.music_app.entity.AlbumEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AlbumRepositoryTest extends RepositorySpec{

    @Autowired
    private AlbumRepository albumRepository;

    private static final Long VALID_ID = 100L;
    private static final Long INVALID_ID = 900L;

    @Test
    @DisplayName("findById success should works")
    void findByIdSuccess(){
        String actual = this.albumRepository.findById(VALID_ID).get().getName();
        String actual1 = this.albumRepository.findById(VALID_ID).get().getAutor();
        Double actual2 = this.albumRepository.findById(VALID_ID).get().getPrice();


        String expected = "fear on the dark";
        String expected1 = "iron maiden";
        Double expected2 = 280.50;

        assertAll(
                ()->assertEquals(expected,actual),
                ()->assertEquals(expected1,actual1),
                ()->assertEquals(expected2,actual2)
        );

    }

    @Test
    @DisplayName("findById success should works")
    void findByIdFailure(){
        Optional<AlbumEntity> actual = this.albumRepository.findById(INVALID_ID);
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("findByPriceBetween should works")
    void findByPriceBetween(){
        Set<AlbumEntity> actual = this.albumRepository.findByPriceBetween(270.00,271.00);
        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());

    }


}
