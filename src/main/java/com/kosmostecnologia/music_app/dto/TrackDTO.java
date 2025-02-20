package com.kosmostecnologia.music_app.dto;

import java.io.Serial;
import java.io.Serializable;

import com.kosmostecnologia.music_app.util.JsonUtil;

public class TrackDTO implements Serializable{

	@Serial
	private static final long serialVersionUID = 5585894285878319988L;

	private Long trackId;
	private String name;
	private String lyrics;
	private AlbumDTO album;
	
	public TrackDTO() {}

	public TrackDTO(Long trackId, String name, String lyrics, AlbumDTO album) {
		this.trackId = trackId;
		this.name = name;
		this.lyrics = lyrics;
		this.album = album;
	}

	public TrackDTO(long trackId, String name, String lyrics) {
		this.trackId = trackId;
		this.name = name;
		this.lyrics = lyrics;
	}

	public Long getTrackId() {
		return trackId;
	}

	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public AlbumDTO getAlbum() {
		return album;
	}

	public void setAlbum(AlbumDTO album) {
		this.album = album;
	}
	
	
	@Override
	public String toString() {
		return JsonUtil.toStringJson(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trackId == null) ? 0 : trackId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrackDTO other = (TrackDTO) obj;
		if (trackId == null) {
			return other.trackId == null;
		} else return trackId.equals(other.trackId);
	}

	
}
