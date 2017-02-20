package io.supersimple.notes.di.activity;


import android.app.Activity;

public interface ActivityComponentBuilderProvider {
    ActivityComponentBuilder getActivityComponentBuilder(Class<? extends Activity> activityClass);
}
