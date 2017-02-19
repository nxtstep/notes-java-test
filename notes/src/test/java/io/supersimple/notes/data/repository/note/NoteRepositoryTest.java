package io.supersimple.notes.data.repository.note;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.supersimple.notes.data.models.Note;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteRepositoryTest {

    @Mock
    private NoteCache mockCache;

    @Test
    public void testList() {
        List<Note> testNotes = Arrays.asList(Note.create("1", "Note value"));
        when(mockCache.list()).thenReturn(Maybe.just(testNotes));

        NoteRepository repo = new NoteRepository(mockCache);
        repo.list()
                .test()
                .assertResult(testNotes);

        verify(mockCache, times(1)).list();
    }

    @Test
    public void testSave() {
        Note note = Note.create("2", "New Note");
        when(mockCache.save(any())).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Single.just(invocation.getArguments()[0]);
            }
        });

        NoteRepository repo = new NoteRepository(mockCache);
        repo.save(note)
                .test()
                .assertResult(note);

        verify(mockCache, times(1)).save(eq(note));
    }

    @Test
    public void testGet() {
        Note note = Note.create("1", "Existing Note");
        when(mockCache.get(any())).thenReturn(Maybe.just(note));

        NoteRepository repo = new NoteRepository(mockCache);
        repo.get("1")
                .test()
                .assertResult(note);
    }
}
