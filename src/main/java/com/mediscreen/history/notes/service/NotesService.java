package com.mediscreen.history.notes.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.history.notes.dto.NoteDTO;
import com.mediscreen.history.notes.models.Note;
import com.mediscreen.history.notes.repository.NotesRepository;

@Service
public class NotesService {
	
	@Autowired
	NotesRepository notesRepository;
	
	private Logger logger = LoggerFactory.getLogger(NotesService.class);
	
	public List<NoteDTO> getAllNotes() {
		List<NoteDTO> notesDTOList = new ArrayList<NoteDTO>();
		try {
			for (Note patient : notesRepository.findAll()) {
				notesDTOList.add(new NoteDTO(patient));
			}
		} catch(Exception e) {
			logger.info("Error getAllNotes : " + e);
		}
		return notesDTOList;
	}
	
	public List<NoteDTO> getNotesByPatientId(int patientId) {
		List<NoteDTO> noteListDTO = new ArrayList<NoteDTO>();
		try {
			List<Note> noteList = notesRepository.findByPatientId(patientId);
			for (Note note :  noteList) {
				noteListDTO.add(new NoteDTO(note));
			}
		} catch(Exception e) {
			logger.error("Error getNoteByPatientId : " + e);
			return noteListDTO;
		}
		
		return noteListDTO;
	}
	
	public boolean addNote(NoteDTO noteDTO) {
		try {
			List<NoteDTO> listNotes = getAllNotes();
			System.out.println(listNotes.size());
			Note note = new Note(noteDTO, listNotes.size() > 0 ? listNotes.get(listNotes.size() - 1).getId() : 1);
			notesRepository.save(note);
			
		} catch(Exception e) {
			logger.error("Error addNote : " + e);
			return false;
		}
		
		return true;
	}
	
	public boolean modifyNote(Integer id, NoteDTO noteDTO) {
		try {
			Note note = notesRepository.findById(id).get();
			if (note != null) {
				note.setNote(noteDTO.getNote());
				notesRepository.save(note);
			}
		} catch(Exception e) {
			logger.error("Error modifyNote : " + e);
			return false;
		}
		
		return true;
	}
	
	public boolean deleteNote(Integer id) {
		try {
			notesRepository.deleteById(id);
		} catch(Exception e) {
			logger.error("Error deleteNote : " + e);
			return false;
		}
		return true;
	}

}
