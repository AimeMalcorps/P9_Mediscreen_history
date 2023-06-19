package com.mediscreen.history.notes.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mediscreen.history.notes.models.Note;

public interface NotesRepository extends MongoRepository<Note, Integer> {

	List<Note> findByPatientId(Integer patientId);

	void deleteByPatientId(Integer patientId);
}
