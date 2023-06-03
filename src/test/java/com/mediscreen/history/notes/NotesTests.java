package com.mediscreen.history.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Order;
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
	@Order(1)
	public void testAddNote() {
		NoteDTO noteDTO = new NoteDTO();
		noteDTO.setNote("Ceci est une note de test");
		noteDTO.setPatientId(666);
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
	@Order(2)
	public void testGetAllNotes() {
		
		List<Note> notesFromRepository = notesRepository.findAll();
		
		List<NoteDTO> notesFromService = notesService.getAllNotes();
		
		assertEquals(notesFromRepository.size(), notesFromService.size());
		
	}
	
	@Test
	@Order(3)
	public void testGetNotesByPatientId() {
		
		List<NoteDTO> listNoteDTO = notesService.getNotesByPatientId(666);
		
		NoteDTO foundNoteDTO = null;
		
		if (listNoteDTO.size() == 1) {
			foundNoteDTO = listNoteDTO.get(0);
		} else {
			foundNoteDTO = listNoteDTO.stream()
			  .filter(note -> note.getNote().equals("Ceci est une note de test"))
			  .findAny()
			  .orElse(null);
		}
		
		assertNotNull(foundNoteDTO);
	}
	
	@Test
	@Order(4)
	public void testModifyNote() {
		
		NoteDTO noteDTO = new NoteDTO();
		noteDTO.setNote("Old Note");
		noteDTO.setPatientId(1);
		
		notesRepository.save(new Note(noteDTO, 0));
		
		String newNote = "Modified note";
		
		Note note = notesRepository.findById(1).get();
		
		noteDTO.setNote(newNote);
		noteDTO.setPatientId(note.getPatientId());
		
		notesService.modifyNote(note.getId(), noteDTO);
			
		assertEquals(newNote, notesRepository.findById(1).get().getNote());	
	}
	
	@Test
	@Order(5)
	public void testDeleteNote() {
		
		int bataBaseSize = notesRepository.findAll().size();
		
		notesService.deleteNote(1);
		
		int newDataBaseSize = notesRepository.findAll().size();
		
		assertEquals(bataBaseSize, newDataBaseSize + 1);
	}

}
