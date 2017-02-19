package io.supersimple.notes.data.repository.note;

import io.supersimple.notes.data.models.Note;
import io.supersimple.notes.data.repository.SimpleCache;

public class NoteCache extends SimpleCache<String, Note> {

    @Override
    public String getId(Note value) {
        return value.id();
    }
}
