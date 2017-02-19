package io.supersimple.notes.data.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Note implements Parcelable {
    public static final Note EMPTY = Note.create("-1", "");

    public abstract String id();
    public abstract String value();

    static Builder builder() {
        return new AutoValue_Note.Builder();
    }

    public static Builder builder(Note note) {
        return new AutoValue_Note.Builder(note);
    }

    public static Note create(String id, String value) {
        return new AutoValue_Note(id, value);
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setId(String id);
        public abstract Builder setValue(String value);

        public abstract Note build();
    }
}
