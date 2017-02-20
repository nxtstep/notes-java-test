package io.supersimple.notes.di.app;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.supersimple.notes.di.qualifier.IOScheduler;
import io.supersimple.notes.di.qualifier.MainScheduler;

@Module
public class ThreadingModule {

    @Singleton
    @IOScheduler
    @Provides
    public Scheduler provideIOScheduler() {
        return Schedulers.io();
    }

    @Singleton
    @MainScheduler
    @Provides
    public Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
