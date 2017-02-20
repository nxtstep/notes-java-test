package io.supersimple.notes.data.repository.note;

import java.util.Map;

import io.supersimple.notes.data.models.Note;
import io.supersimple.notes.data.repository.SimpleCache;

public class NoteCache extends SimpleCache<String, Note> {

    public NoteCache(Map<String, Note> cache) {
        super(cache);
    }

    public NoteCache() {
    }

    @Override
    public String getId(Note value) {
        return value.id();
    }
}
