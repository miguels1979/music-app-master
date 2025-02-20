package com.kosmostecnologia.music_app.repository;
import java.util.Set;

import com.kosmostecnologia.music_app.entity.AlbumEntity;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<AlbumEntity, Long>{

	Set<AlbumEntity> findByPriceBetween(Double min, Double max);
}
