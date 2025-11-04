package dev.dmbiee.securenote.features.note;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes(Authentication authentication) {
        String owner = authentication.getName();
        return noteService.getNotesByOwner(owner);
    }

    @PostMapping
    public Note createNote(@RequestBody Note note, Authentication authentication) {
        String owner = authentication.getName();
        return noteService.createNote(note, owner);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @RequestBody Note updatedNote, Authentication authentication) {
        String owner = authentication.getName();
        return noteService.updateNote(id, updatedNote, owner);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id, Authentication authentication) {
        String owner = authentication.getName();
        noteService.deleteNote(id, owner);
    }

    @GetMapping("/shared")
    public List<Note> getSharedNotes() {
        return noteService.getSharedNotes();
    }

    @PutMapping("/{id}/share")
    public Note toggleShare(@PathVariable Long id, Authentication authentication) {
        return noteService.toggleShareStatus(id, authentication);
    }
}
