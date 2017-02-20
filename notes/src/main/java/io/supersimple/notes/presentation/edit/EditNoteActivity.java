package io.supersimple.notes.presentation.edit;


import io.supersimple.notes.di.activity.ActivityComponentBuilderProvider;
import io.supersimple.notes.presentation.ComponentActivity;

public class EditNoteActivity extends ComponentActivity {
    @Override
    protected void injectMembers(ActivityComponentBuilderProvider componentBuilderProvider) {
        componentBuilderProvider.getActivityComponentBuilder(EditNoteActivity.class)
                .activityModule(new EditNoteActivityComponent.EditNoteModule(this))
                .build()
                .injectMembers(this);
    }
}
