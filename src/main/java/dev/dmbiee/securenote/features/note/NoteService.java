package dev.dmbiee.securenote.features.note;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotesByOwner(String owner) {
        return noteRepository.findByOwner(owner);
    }

    public Note createNote(Note note, String owner) {
        note.setOwner(owner);
        note.setDate(LocalDate.now());
        return noteRepository.save(note);
    }

    public Note updateNote(Long id, Note updatedNote, String owner) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getOwner().equals(owner)) {
            throw new RuntimeException("Access denied");
        }

        note.setTitle(updatedNote.getTitle());
        note.setDescription(updatedNote.getDescription());
        note.setDate(LocalDate.now());
        return noteRepository.save(note);
    }

    public void deleteNote(Long id, String owner) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getOwner().equals(owner)) {
            throw new RuntimeException("Access denied");
        }

        noteRepository.delete(note);
    }
}
