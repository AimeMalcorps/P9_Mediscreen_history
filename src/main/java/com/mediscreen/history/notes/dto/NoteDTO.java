package com.mediscreen.history.notes.dto;

import com.mediscreen.history.notes.models.Note;

public class NoteDTO {

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

	public NoteDTO() {

	}

	public NoteDTO(Note notes) {
		this.id = notes.getId();
		this.patientId = notes.getPatientId();
		this.note = notes.getNote();
	}

}
