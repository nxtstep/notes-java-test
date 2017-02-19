package io.supersimple.notes.data.repository.note;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.supersimple.notes.data.models.Note;

public class NoteRepository {
    private final NoteCache cache;

    public NoteRepository(NoteCache cache) {
        this.cache = cache;
    }

    public Maybe<List<Note>> list() {
        return cache.list();
    }

    public Single<Note> save(Note note) {
        return cache.save(note);
    }

    public Maybe<Note> get(String id) {
        return cache.get(id);
    }
}
