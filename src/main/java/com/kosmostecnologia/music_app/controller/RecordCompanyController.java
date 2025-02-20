package com.kosmostecnologia.music_app.controller;

import com.kosmostecnologia.music_app.dto.RecordCompanyDTO;
import com.kosmostecnologia.music_app.entity.RecordCompanyEntity;
import com.kosmostecnologia.music_app.service.IRecordCompanyService;
import com.kosmostecnologia.music_app.util.JsonUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "v1/record")
public class RecordCompanyController {

	private final IRecordCompanyService service;

	public RecordCompanyController(IRecordCompanyService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Set<RecordCompanyEntity>> getAll(){
		return ResponseEntity.ok().body(this.service.getAll());
	}
	
	@PostMapping
	public ResponseEntity<RecordCompanyEntity> save(@Valid @RequestBody RecordCompanyDTO record) {;
	RecordCompanyEntity toSave = (RecordCompanyEntity) JsonUtil.bodyMapper(record, RecordCompanyEntity.class); 
	return ResponseEntity.ok().body(this.service.save(toSave));
	}

}
