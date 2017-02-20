package io.supersimple.notes.test.runner;


import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import io.supersimple.notes.application.MockApplication;

public class TestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MockApplication.class.getName(), context);
    }
}
