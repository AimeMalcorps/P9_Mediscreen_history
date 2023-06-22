package com.mediscreen.history.notes;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mediscreen.history.notes.dto.NoteDTO;
import com.mediscreen.history.notes.models.Note;
import com.mediscreen.history.notes.repository.NotesRepository;
import com.mediscreen.history.notes.service.NotesService;

@SpringBootTest
@AutoConfigureMockMvc
public class NotesControllerTests {
	
	@Autowired
	public MockMvc mockMvc;
	
	@Autowired
	NotesRepository notesRepository;
	
	@Autowired
	NotesService notesService;
	
	@BeforeEach
	public void setUp() throws Exception {
		notesRepository.deleteAll();
		Note note = new Note();
		note.setId(333);
		note.setPatientId(444);
		note.setNote("Controller Test Note");
		notesRepository.save(note);
	}
	
	@Test
	public void getAllNotes() throws Exception {
		this.mockMvc.perform(get("/patHistory/all")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].note", is("Controller Test Note"))).andReturn();
	}
	
	@Test
	public void searchNotesByPatientId() throws Exception {
		this.mockMvc.perform(get("/patHistory/patient/444"))
		.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].note", is("Controller Test Note"))).andReturn();
	}
	
	@Test
	public void addNote() throws Exception {
		NoteDTO noteDTO = new NoteDTO();
		noteDTO.setPatientId(555);
		noteDTO.setNote("Note Ajoutée par controlleur");
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(noteDTO);

		this.mockMvc.perform(post("/patHistory/add").contentType(MediaType.APPLICATION_JSON).content(requestJson))
		.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/patHistory/patient/555"))
		.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].note", is("Note Ajoutée par controlleur"))).andReturn();
	}
	
	@Test
	public void updateNote() throws Exception {
		NoteDTO noteDTO = new NoteDTO();
		noteDTO.setId(333);
		noteDTO.setPatientId(444);
		noteDTO.setNote("Note Modifée");
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(noteDTO);

		this.mockMvc.perform(post("/patHistory/update").contentType(MediaType.APPLICATION_JSON).content(requestJson))
		.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/patHistory/patient/444"))
		.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].note", is("Note Modifée"))).andReturn();
	}
	
	@Test
	public void deleteNote() throws Exception {

		this.mockMvc.perform(get("/patHistory/delete/333"))
		.andExpect(status().isOk());
		
		assertEquals(0, notesService.getNotesByPatientId(444).size());
	}
	
	@Test
	public void deleteAllPatientNote() throws Exception {
		
		Note note = new Note();
		note.setId(666);
		note.setPatientId(444);
		note.setNote("Deuxieme note pour patient 444");
		notesRepository.save(note);
		
		assertEquals(2, notesService.getNotesByPatientId(444).size());

		this.mockMvc.perform(get("/patHistory/delete/all/444"))
		.andExpect(status().isOk());
		
		assertEquals(0, notesService.getNotesByPatientId(444).size());
	}
	

}
