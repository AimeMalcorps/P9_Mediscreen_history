package com.mediscreen.history.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.history.notes.dto.NoteDTO;
import com.mediscreen.history.notes.models.Note;
import com.mediscreen.history.notes.repository.NotesRepository;
import com.mediscreen.history.notes.service.NotesService;

@SpringBootTest
public class NotesTests {
	
	@Autowired
	NotesRepository notesRepository;
	
	@Autowired
	NotesService notesService;
	
	@Test
	public void testAddNote() {
		NoteDTO noteDTO = new NoteDTO();
		noteDTO.setNote("Ceci est une note de test");
		noteDTO.setPatientId(1);
		notesService.addNote(noteDTO);
		
		Note foundNote = null;
		
		List<Note> notesFromRepository = notesRepository.findAll();
		
		foundNote = notesFromRepository.stream()
				  .filter(note -> note.getNote().equals("Ceci est une note de test"))
				  .findAny()
				  .orElse(null);
		
		assertNotNull(foundNote);
	}
	
	@Test
	public void testGetAllNotes() {
		
		List<Note> notesFromRepository = notesRepository.findAll();
		
		List<NoteDTO> notesFromService = notesService.getAllNotes();
		
		assertEquals(notesFromRepository.size(), notesFromService.size());
		
	}
	
	@Test
	public void testGetNotesByPatientId() {
		
		Note note = new Note();
		note.setId(222);
		note.setNote("TEST");
		note.setPatientId(1);
		
		List<NoteDTO> listNoteDTO = notesService.getNotesByPatientId(222);
		
		boolean notesForSamePatient = true;
		
		for (NoteDTO noteDTO : listNoteDTO) {
			if (noteDTO.getPatientId() != 222) {
				notesForSamePatient = false;
			}
		}
		assertTrue(notesForSamePatient);
	}
	
	@Test
	public void testModifyNote() {
		
		Note note = new Note();
		note.setId(22);
		note.setNote("Old Note");
		note.setPatientId(1);
		
		notesRepository.save(note);
		
		String newNote = "Modified note";
		
		note = notesRepository.findById(22).get();
		
		note.setNote(newNote);
		
		NoteDTO noteDTO = new NoteDTO(note);
		
		notesService.modifyNote(noteDTO);
			
		assertEquals(newNote, notesRepository.findById(22).get().getNote());	
	}
	
	@Test
	public void testDeleteNote() {
		Note note = new Note();
		note.setId(11);
		note.setPatientId(11);
		note.setNote("Test");
		notesRepository.save(note);
		
		int bataBaseSize = notesRepository.findAll().size();
		
		notesService.deleteNote(11);
		
		int newDataBaseSize = notesRepository.findAll().size();
		
		assertEquals(newDataBaseSize, bataBaseSize - 1);
	}
	
	@Test
	public void testDeleteAllNote() {
		
		Note note = new Note();
		note.setPatientId(12);
		note.setNote("Test");
		notesRepository.save(note);
		
		int patientNotesSize = notesRepository.findAll().size();
		
		notesService.deleteAllPatientNotes(12);
		
		int newDataBaseSize = notesRepository.findAll().size();
		
		assertEquals(newDataBaseSize, patientNotesSize - 1);
	}

}
