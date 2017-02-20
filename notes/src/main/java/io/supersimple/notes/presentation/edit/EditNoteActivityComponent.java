package io.supersimple.notes.presentation.edit;

import dagger.Module;
import dagger.Subcomponent;
import io.supersimple.notes.di.activity.ActivityComponent;
import io.supersimple.notes.di.activity.ActivityComponentBuilder;
import io.supersimple.notes.di.activity.ActivityModule;
import io.supersimple.notes.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = EditNoteActivityComponent.EditNoteModule.class)
public interface EditNoteActivityComponent extends ActivityComponent<EditNoteActivity> {

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<EditNoteActivityComponent.EditNoteModule, EditNoteActivityComponent> {
    }

    @Module
    class EditNoteModule extends ActivityModule<EditNoteActivity> {
        public EditNoteModule(EditNoteActivity activity) {
            super(activity);
        }
    }
}
