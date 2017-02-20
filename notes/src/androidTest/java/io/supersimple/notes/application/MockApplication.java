package io.supersimple.notes.application;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Provider;

import io.supersimple.notes.di.activity.ActivityComponentBuilder;

public class MockApplication extends NotesApplication {
    public void putActivityComponentBuilder(final ActivityComponentBuilder builder, Class<? extends Activity> cls) {
        Map<Class<? extends Activity>, Provider<ActivityComponentBuilder>> activityComponentBuilders = new HashMap<>(this.activityComponentBuilders);
        activityComponentBuilders.put(cls, () -> builder);
        this.activityComponentBuilders = activityComponentBuilders;
    }
}