package com.mediscreen.history.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.history.notes.dto.NoteDTO;
import com.mediscreen.history.notes.service.NotesService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class NotesController {
	
	@Autowired
	NotesService notesService;
	
	@GetMapping("/patHistory/all")
	public List<NoteDTO> getAllNotes() {
		return notesService.getAllNotes();
	}
	
	@GetMapping("/patHistory/patient/{id}")
	public List<NoteDTO> getNoteByPatientId(@PathVariable Integer id) {
		return notesService.getNotesByPatientId(id);
	}
	
	@PostMapping("/patHistory/add")
	public boolean addPatient(@RequestBody NoteDTO noteDTO) {
		return notesService.addNote(noteDTO);
	}
	
	@PostMapping("/patHistory/update")
	public boolean modifyPatient(@RequestBody NoteDTO noteDTO) {
		return notesService.modifyNote(noteDTO);
	}
	
	@GetMapping("/patHistory/delete/{id}")
	public boolean modifyPatient(@PathVariable Integer id) {
		return notesService.deleteNote(id);
	}
	
	@GetMapping("/patHistory/delete/all/{id}")
	public boolean deleteAllPatientNotes(@PathVariable Integer id) {
		return notesService.deleteAllPatientNotes(id);
	}
	

}
