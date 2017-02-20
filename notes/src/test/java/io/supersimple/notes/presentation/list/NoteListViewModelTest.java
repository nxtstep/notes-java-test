package io.supersimple.notes.presentation.list;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import io.reactivex.Maybe;
import io.reactivex.schedulers.TestScheduler;
import io.supersimple.notes.data.models.Note;
import io.supersimple.notes.data.repository.note.NoteRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteListViewModelTest {
    private Note note = Note.create("1", "Test note");

    @Mock
    private NoteRepository mockRepository;
    @Mock
    private NoteListView mockView;

    @Test
    public void testNoteListViewModel_list() {
        when(mockRepository.list()).thenReturn(Maybe.just(Arrays.asList(note)));
        TestScheduler testScheduler = new TestScheduler();

        NoteListViewModel viewModel = new NoteListViewModel(mockRepository, testScheduler, testScheduler);
        viewModel.bind(mockView);

        testScheduler.triggerActions();
        verify(mockRepository, times(1)).list();
        verify(mockView, never()).showError(any());
        verify(mockView, never()).showEmpty();
    }

    @Test
    public void testNoteListViewModel_empty_list() {
        when(mockRepository.list()).thenReturn(Maybe.empty());
        TestScheduler testScheduler = new TestScheduler();

        NoteListViewModel viewModel = new NoteListViewModel(mockRepository, testScheduler, testScheduler);
        viewModel.bind(mockView);

        testScheduler.triggerActions();
        verify(mockRepository, times(1)).list();
        verify(mockView, never()).showError(any());
        verify(mockView, times(1)).showEmpty();
    }
}
