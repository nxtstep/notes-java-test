package io.supersimple.notes.presentation.list;


import android.databinding.ObservableArrayList;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.supersimple.notes.data.models.Note;
import io.supersimple.notes.data.repository.note.NoteRepository;
import io.supersimple.notes.di.qualifier.IOScheduler;
import io.supersimple.notes.di.qualifier.MainScheduler;
import io.supersimple.notes.rx.RxUtils;

public class NoteListViewModel extends ObservableArrayList<Note> {

    private final NoteRepository noteRepository;
    private final Scheduler mainScheduler;
    private final Scheduler ioScheduler;

    private CompositeDisposable disposeBag;
    private NoteListView noteListView;

    @Inject
    public NoteListViewModel(NoteRepository noteRepository,
                             @MainScheduler Scheduler mainScheduler,
                             @IOScheduler Scheduler ioScheduler) {
        this.noteRepository = noteRepository;
        this.mainScheduler = mainScheduler;
        this.ioScheduler = ioScheduler;
    }

    public void bind(NoteListView view) {
        RxUtils.safeDispose(disposeBag);
        noteListView = view;
        disposeBag = new CompositeDisposable();
        disposeBag.add(
                noteRepository.list()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(this::addAll,
                        noteListView::showError)

        );
    }

    public void unbind() {
        RxUtils.safeDispose(disposeBag);
        disposeBag = null;
        noteListView = null;
    }

}
