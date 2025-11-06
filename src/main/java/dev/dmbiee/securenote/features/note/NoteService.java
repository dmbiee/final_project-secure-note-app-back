package dev.dmbiee.securenote.features.note;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import dev.dmbiee.securenote.features.friend.Friend;
import dev.dmbiee.securenote.features.friend.FriendService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final FriendService friendService;

    public NoteService(NoteRepository noteRepository, FriendService friendService) {
        this.noteRepository = noteRepository;
        this.friendService = friendService;
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

    public List<Note> getSharedNotes() {
        return noteRepository.findByIsSharedTrue();
    }

    public Note toggleShareStatus(Long id, Authentication authentication) {
        String username = authentication.getName();

        Optional<Note> noteOpt = noteRepository.findById(id);
        if (noteOpt.isEmpty()) {
            throw new RuntimeException("Note not found");
        }

        Note note = noteOpt.get();

        if (!note.getOwner().equals(username)) {
            throw new RuntimeException("Access denied");
        }

        note.setShared(!note.isShared());
        return noteRepository.save(note);
    }

    public List<Note> getSharedNotesForUser(String username) {
        // Знаходимо усіх користувачів, у кого цей юзер є в друзях
        var friends = friendService.getUsersWhoAddedMe(username);
        var owners = friends.stream()
                .map(Friend::getUser)
                .toList();

        // Повертаємо поширені нотатки цих користувачів
        return noteRepository.findByIsSharedTrueAndOwnerIn(owners);
    }
}
