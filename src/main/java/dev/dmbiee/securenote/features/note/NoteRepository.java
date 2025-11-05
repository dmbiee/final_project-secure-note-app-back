package dev.dmbiee.securenote.features.note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByOwner(String owner);

    List<Note> findByIsSharedTrue();
}
