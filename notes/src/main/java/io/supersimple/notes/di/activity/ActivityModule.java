package io.supersimple.notes.di.activity;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ActivityModule<A extends Activity> {
    protected final A activity;

    public ActivityModule(A activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public A provideActivity() {
        return activity;
    }
}
