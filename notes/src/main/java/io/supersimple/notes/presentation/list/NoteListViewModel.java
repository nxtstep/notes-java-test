package io.supersimple.notes.presentation.list;


import android.databinding.ObservableArrayList;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import io.supersimple.notes.data.models.Note;
import io.supersimple.notes.data.repository.note.NoteRepository;
import io.supersimple.notes.di.qualifier.IOScheduler;
import io.supersimple.notes.di.qualifier.MainScheduler;
import io.supersimple.notes.rx.RxUtils;

public class NoteListViewModel extends ObservableArrayList<Note> {

    enum LoadingState {
        EMPTY,
        LOADING,
        FINISHED,
        ERROR
    }

    private final NoteRepository noteRepository;
    private final Scheduler mainScheduler;
    private final Scheduler ioScheduler;

    private final PublishSubject<LoadingState> stateSubject = PublishSubject.create();

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
                stateSubject.observeOn(mainScheduler)
                        .subscribe(state -> {
                            switch (state) {
                                case EMPTY:
                                    noteListView.showEmpty();
                                    break;
                                case LOADING:
                                    noteListView.showLoading(true);
                                case FINISHED:
                                    noteListView.showLoading(false);
                                    break;
                                default:
                                    break;
                            }
                        })
        );

        disposeBag.add(
                noteRepository.list()
                        .doOnSubscribe(__ -> stateSubject.onNext(LoadingState.LOADING))
                        .doFinally(() -> stateSubject.onNext(LoadingState.FINISHED))
                        .doOnComplete(() -> stateSubject.onNext(LoadingState.EMPTY))
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

    public void addNote(String note) {
        disposeBag.add(
                Single.just(note)
                        .map(n -> Note.create("" + this.size(), n))
                        .flatMap(noteRepository::save)
                        .subscribeOn(ioScheduler)
                        .observeOn(mainScheduler)
                        .subscribe(this::add,
                                noteListView::showError)
        );
    }

}
