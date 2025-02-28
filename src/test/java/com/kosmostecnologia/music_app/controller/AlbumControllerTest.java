package com.kosmostecnologia.music_app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosmostecnologia.music_app.dto.AlbumDTO;
import com.kosmostecnologia.music_app.service.IAlbumService;
import com.kosmostecnologia.music_app.util.DataDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.NoSuchElementException;

import static com.kosmostecnologia.music_app.util.DataDummy.ALBUM_DTO_INVALID;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class AlbumControllerTest extends ControllerSpec{

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IAlbumService albumServiceMock;

    ObjectMapper objectMapper;

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 2L;
    private static final String RESOURCE_PATH = "/v1/album";


    @BeforeEach
    void init(){
        this.objectMapper =  new ObjectMapper();
    }

    @BeforeEach
    void setupMocks(){
        when(this.albumServiceMock.findById(eq(VALID_ID))).thenReturn(DataDummy.ALBUM_DTO);
        when(this.albumServiceMock.findById(eq(INVALID_ID))).thenThrow(NoSuchElementException.class);
        when(this.albumServiceMock.save(eq(DataDummy.ALBUM_DTO))).thenReturn(DataDummy.ALBUM_DTO);
    }

    @Test
    @DisplayName("call findById should works")
    void findById() throws Exception {
        final String uri = RESOURCE_PATH + "/" + VALID_ID;

        this.mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(DataDummy.ALBUM_DTO.getName()))
                .andExpect(jsonPath("$.autor").value(DataDummy.ALBUM_DTO.getAutor()))
                .andExpect(jsonPath("$.price").value(DataDummy.ALBUM_DTO.getPrice()));

        verify(this.albumServiceMock).findById(eq(VALID_ID));
   }

    @Test
    @DisplayName("call findByIdException should works")
    void findByIdException() throws Exception {
        final String uri = RESOURCE_PATH + "/" + INVALID_ID;

        this.mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(this.albumServiceMock).findById(eq(INVALID_ID));
    }

    @Test
    @DisplayName("call save should works")
    void save() throws Exception {
        this.mockMvc.perform(post(RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(DataDummy.ALBUM_DTO)))
                .andExpect(status().isCreated());

        verify(this.albumServiceMock).save(any(AlbumDTO.class));
    }

    @Test
    @DisplayName("call saveException should works")
    void saveException() throws Exception {
        this.mockMvc.perform(post(RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(DataDummy.ALBUM_DTO_INVALID)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").isMap())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));


    }

    @Test
    @DisplayName("call update should works")
    void update() throws Exception {
        final String uri = RESOURCE_PATH + "/" + VALID_ID;
        AlbumDTO albumToUpdate = DataDummy.ALBUM_DTO;
        albumToUpdate.setAutor("Autor updated");

        when(this.albumServiceMock.update(eq(albumToUpdate), eq(VALID_ID))).thenReturn(albumToUpdate);

        this.mockMvc.perform(
                        put(uri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(albumToUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.autor").value("Autor updated"));
        verify(this.albumServiceMock).update(eq(albumToUpdate),eq(VALID_ID));
    }

    @Test
    @DisplayName("call updateException should works")
    void updateException() throws Exception {
        final String uri = RESOURCE_PATH + "/" + INVALID_ID;
        AlbumDTO albumToUpdate = DataDummy.ALBUM_DTO;
        albumToUpdate.setAutor("Autor updated");

        when(this.albumServiceMock.update(eq(albumToUpdate), eq(INVALID_ID))).thenThrow(NoSuchElementException.class);

        this.mockMvc.perform(
                        put(uri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(albumToUpdate)))
                .andExpect(status().isNotFound());

        verify(this.albumServiceMock).update(eq(albumToUpdate),eq(INVALID_ID));
    }

    @Test
    @DisplayName("call delete should works")
    void delete() throws Exception{
        final String uri =  RESOURCE_PATH + "/" + VALID_ID;

        this.mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isNoContent());

        verify(this.albumServiceMock).delete(VALID_ID);
    }

    @Test
    @DisplayName("call deleteException should works")
    void deleteException() throws Exception{
        final String uri =  RESOURCE_PATH + "/" + INVALID_ID;

        doThrow(NoSuchElementException.class).when(this.albumServiceMock).delete(eq(INVALID_ID));

        this.mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(status().isNotFound());
        verify(this.albumServiceMock).delete(eq(INVALID_ID));
    }

}



