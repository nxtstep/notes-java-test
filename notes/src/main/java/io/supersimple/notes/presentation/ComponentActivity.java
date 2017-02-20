package io.supersimple.notes.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.supersimple.notes.application.NotesApplication;
import io.supersimple.notes.di.activity.ActivityComponentBuilderProvider;


public abstract class ComponentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    protected void setupActivityComponent() {
        injectMembers(NotesApplication.get(this));
    }

    protected abstract void injectMembers(ActivityComponentBuilderProvider componentBuilderProvider);
}
