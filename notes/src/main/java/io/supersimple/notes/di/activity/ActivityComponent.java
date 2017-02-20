package io.supersimple.notes.di.activity;


import android.app.Activity;

import dagger.MembersInjector;

public interface ActivityComponent<A extends Activity> extends MembersInjector<A> {
}
