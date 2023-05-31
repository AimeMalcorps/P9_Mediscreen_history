package com.mediscreen.history.notes.models;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mediscreen.history.notes.dto.NoteDTO;

@Document
public class Note {
	
	private int id;
	private int patientId;
	private String note;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public Note() {
		
	}
	
	public Note(NoteDTO noteDTO, int dbSize) {
		this.setId(dbSize + 1);
		this.setPatientId(noteDTO.getPatientId());
		this.setNote(noteDTO.getNote());
	}

}
