package io.supersimple.notes.test;

import io.supersimple.notes.test.matcher.RecyclerViewMatcher;

public class TestUtils {

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {

        return new RecyclerViewMatcher(recyclerViewId);
    }
}