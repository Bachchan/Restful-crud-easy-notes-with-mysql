package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {
	@Autowired
	NoteRepository noteRepository;

	@GetMapping("/notes")
	public List<Note> getAlNotes() {
		return noteRepository.findAll();
	}

	@GetMapping("/notes/{id}")
	public Note getNote(@PathVariable(value = "id") Long noteId) {
		return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("note", "id", noteId));
	}

	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		return noteRepository.save(note);
	}

	@PutMapping("/notes/{id}")
	public Note updateNote(@Valid @RequestBody Note note, @PathVariable(value = "id") Long noteId) {
		Note note1 = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("note", "id", noteId));
		note1.setTitle(note.getTitle());
		note1.setContent(note.getContent());
		Note updateNote = noteRepository.save(note1);

		return updateNote;
	}
	
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId){
		Note note=noteRepository.findById(noteId).orElseThrow(()->new ResourceNotFoundException("Note", "id",noteId));
		noteRepository.delete(note);
		return ResponseEntity.ok().build();
	}

}
