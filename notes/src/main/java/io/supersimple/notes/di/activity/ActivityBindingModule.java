package io.supersimple.notes.di.activity;


import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.supersimple.notes.presentation.edit.EditNoteActivity;
import io.supersimple.notes.presentation.edit.EditNoteActivityComponent;
import io.supersimple.notes.presentation.list.NoteListActivity;
import io.supersimple.notes.presentation.list.NoteListActivityComponent;

@Module(
        subcomponents = {
                NoteListActivityComponent.class,
                EditNoteActivityComponent.class
        })
public abstract class ActivityBindingModule {
    @Binds
    @IntoMap
    @ActivityKey(NoteListActivity.class)
    public abstract ActivityComponentBuilder noteListActivityComponentBuilder(NoteListActivityComponent.Builder impl);

    @Binds
    @IntoMap
    @ActivityKey(EditNoteActivity.class)
    public abstract ActivityComponentBuilder editNoteActivityComponentBuilder(EditNoteActivityComponent.Builder impl);
}
