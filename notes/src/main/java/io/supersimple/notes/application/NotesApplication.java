package io.supersimple.notes.application;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import io.supersimple.notes.di.activity.ActivityComponentBuilder;
import io.supersimple.notes.di.activity.ActivityComponentBuilderProvider;
import io.supersimple.notes.di.app.DaggerAppComponent;

public class NotesApplication extends Application implements ActivityComponentBuilderProvider {

    @Inject
    Map<Class<? extends Activity>, Provider<ActivityComponentBuilder>> activityComponentBuilders;

    public static ActivityComponentBuilderProvider get(Context context) {
        return ((ActivityComponentBuilderProvider) context.getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.create()
                .inject(this);
    }

    @Override
    public ActivityComponentBuilder getActivityComponentBuilder(Class<? extends Activity> activityClass) {
        return activityComponentBuilders.get(activityClass).get();
    }
}
