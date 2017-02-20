package io.supersimple.notes.test.rx.activity;


import android.support.test.espresso.Espresso;

import io.reactivex.plugins.RxJavaPlugins;
import io.supersimple.notes.test.rx.scheduler.RxIdlingResource;

public abstract class RxActivityTest {

    protected RxIdlingResource rxIdlingResource;

    public void setup() {
        rxIdlingResource = new RxIdlingResource();
        Espresso.registerIdlingResources(rxIdlingResource);
        RxJavaPlugins.setScheduleHandler(rxIdlingResource);
    }

    public void tearDown() {
        Espresso.unregisterIdlingResources(rxIdlingResource);
        rxIdlingResource = null;
    }
}
