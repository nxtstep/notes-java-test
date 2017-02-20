package io.supersimple.notes.presentation.list;

public interface NoteListView {
    void showError(Throwable error);

    void showEmpty();

    void showLoading(boolean flag);
}
