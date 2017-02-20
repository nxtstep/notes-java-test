package io.supersimple.notes.presentation.list;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import io.reactivex.Scheduler;
import io.supersimple.notes.data.repository.note.NoteRepository;
import io.supersimple.notes.di.activity.ActivityComponent;
import io.supersimple.notes.di.activity.ActivityComponentBuilder;
import io.supersimple.notes.di.activity.ActivityModule;
import io.supersimple.notes.di.activity.ActivityScope;
import io.supersimple.notes.di.qualifier.IOScheduler;
import io.supersimple.notes.di.qualifier.MainScheduler;

@ActivityScope
@Subcomponent(modules = NoteListActivityComponent.NoteListModule.class)
public interface NoteListActivityComponent extends ActivityComponent<NoteListActivity> {
    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<NoteListModule, NoteListActivityComponent> {
    }

    @Module
    class NoteListModule extends ActivityModule<NoteListActivity> {
        public NoteListModule(NoteListActivity activity) {
            super(activity);
        }

        @ActivityScope
        @Provides
        public NoteListViewModel providesNoteListViewModel(NoteRepository repository,
                                                           @MainScheduler Scheduler mainScheduler,
                                                           @IOScheduler Scheduler ioScheduler) {
            return new NoteListViewModel(repository, mainScheduler, ioScheduler);
        }
    }
}
