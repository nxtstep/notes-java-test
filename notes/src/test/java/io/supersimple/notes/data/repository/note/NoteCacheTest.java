package io.supersimple.notes.data.repository.note;

import org.junit.Test;

import java.util.Arrays;

import io.supersimple.notes.data.models.Note;

/**
 * Created by arjan on 19/02/2017.
 */

public class NoteCacheTest {
    @Test
    public void testSimpleMemCache() {
        NoteCache cache = new NoteCache();

        //
        // Save
        cache.save(Note.create("1", "Test me"))
                .test()
                .assertResult(Note.create("1", "Test me"));

        //
        // Get
        cache.get("1")
                .test()
                .assertResult(Note.create("1", "Test me"));

        cache.get("2")
                .test()
                .assertNoValues()
                .assertNoErrors()
                .assertComplete();

        cache.list()
                .test()
                .assertResult(Arrays.asList(Note.create("1", "Test me")));
        //
        // Delete
        cache.delete("2")
                .test()
                .assertNoValues()
                .assertNoErrors()
                .assertComplete();

        cache.delete("1")
                .test()
                .assertResult(Note.create("1", "Test me"));

        cache.deleteValue(Note.create("1", "Test me"))
                .test()
                .assertNoValues()
                .assertNoErrors()
                .assertComplete();

        //
        // Clear
        cache.save(Note.create("2", "Whatever value"))
                .test()
                .assertValueCount(1)
                .assertNoErrors()
                .assertComplete();

        cache.clear();

        cache.list()
                .test()
                .assertNoValues()
                .assertNoErrors()
                .assertComplete();
    }
}
