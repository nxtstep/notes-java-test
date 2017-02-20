package io.supersimple.notes.di.app;

import javax.inject.Singleton;

import dagger.Component;
import io.supersimple.notes.application.NotesApplication;
import io.supersimple.notes.di.activity.ActivityBindingModule;

@Singleton
@Component(
        modules = {
                AppModule.class,
                ThreadingModule.class,
                ActivityBindingModule.class
        }
)
public interface AppComponent {
    NotesApplication inject(NotesApplication app);
}
