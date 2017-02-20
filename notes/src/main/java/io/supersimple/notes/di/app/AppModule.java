package io.supersimple.notes.di.app;

import java.util.LinkedHashMap;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.supersimple.notes.data.repository.note.NoteCache;
import io.supersimple.notes.data.repository.note.NoteRepository;

@Module
public class AppModule {
    @Singleton
    @Provides
    public NoteRepository provideNoteRepository() {
        return new NoteRepository(new NoteCache(new LinkedHashMap<>()));
    }
}
