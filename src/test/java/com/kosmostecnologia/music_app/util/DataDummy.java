package com.kosmostecnologia.music_app.util;

import com.kosmostecnologia.music_app.dto.AlbumDTO;
import com.kosmostecnologia.music_app.dto.RecordCompanyDTO;
import com.kosmostecnologia.music_app.dto.TrackDTO;
import com.kosmostecnologia.music_app.entity.AlbumEntity;
import com.kosmostecnologia.music_app.entity.RecordCompanyEntity;
import com.kosmostecnologia.music_app.entity.TrackEntity;

import java.util.Arrays;
import java.util.HashSet;

public class DataDummy {

    public static final RecordCompanyEntity RECORD_COMPANY_ENTITY;
    public static final TrackEntity TRACK_ENTITY_1;
    public static final TrackEntity TRACK_ENTITY_2;
    public static final TrackEntity TRACK_ENTITY_3;
    public static final TrackEntity TRACK_ENTITY_4;
    public static final TrackEntity TRACK_ENTITY_5;
    public static final AlbumEntity ALBUM_ENTITY;
    public static final RecordCompanyDTO RECORD_COMPANY_DTO;
    public static final TrackDTO TRACK_1_DTO;
    public static final TrackDTO TRACK_2_DTO;
    public static final TrackDTO TRACK_3_DTO;
    public static final TrackDTO TRACK_4_DTO;
    public static final TrackDTO TRACK_5_DTO;
    public static final AlbumDTO ALBUM_DTO;
    public static final AlbumDTO ALBUM_DTO_INVALID;

    static {

        TRACK_ENTITY_1 = new TrackEntity(1L, "test-1", "lyrics-1");
        TRACK_ENTITY_2 = new TrackEntity(2L, "test-2", "lyrics-2");
        TRACK_ENTITY_3 = new TrackEntity(3L, "test-3", "lyrics-3");
        TRACK_ENTITY_4 = new TrackEntity(4L, "test-4", "lyrics-4");
        TRACK_ENTITY_5 = new TrackEntity(5L, "test-5", "lyrics-5");

        TRACK_1_DTO = new TrackDTO(1L, "test-1", "lycris-1");
        TRACK_2_DTO = new TrackDTO(2L, "test-2", "lycris-2");
        TRACK_3_DTO = new TrackDTO(3L, "test-3", "lycris-3");
        TRACK_4_DTO = new TrackDTO(4L, "test-4", "lycris-4");
        TRACK_5_DTO = new TrackDTO(5L, "test-5", "lycris-5");


        RECORD_COMPANY_ENTITY = new RecordCompanyEntity("test-company-records");

        RECORD_COMPANY_DTO = new RecordCompanyDTO("test-company-records");

        ALBUM_ENTITY = new AlbumEntity(
                1L,
                "Album-test",
                "Actor-test",
                20.20,
                RECORD_COMPANY_ENTITY,
                new HashSet<>(Arrays.asList(TRACK_ENTITY_1, TRACK_ENTITY_2, TRACK_ENTITY_3, TRACK_ENTITY_4)));

        ALBUM_DTO = new AlbumDTO(
                1L,
                "Album-test",
                "Actor-test",
                20.20,
                RECORD_COMPANY_DTO,
                new HashSet<>(Arrays.asList(TRACK_1_DTO, TRACK_2_DTO, TRACK_3_DTO, TRACK_4_DTO)));

        ALBUM_DTO_INVALID = new AlbumDTO(
                1L,
                "album-test",
                "actor-test",
                20.20,
                RECORD_COMPANY_DTO,
                new HashSet<>(Arrays.asList(TRACK_1_DTO, TRACK_2_DTO, TRACK_3_DTO, TRACK_4_DTO)));
    }

}
