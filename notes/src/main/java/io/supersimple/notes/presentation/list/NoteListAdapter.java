package io.supersimple.notes.presentation.list;

import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.supersimple.notes.data.models.Note;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private final NoteListViewModel viewModel;
    private final ObservableList.OnListChangedCallback<ObservableList<Note>> listener =
            new ObservableList.OnListChangedCallback<ObservableList<Note>>() {
                @Override
                public void onChanged(ObservableList<Note> notes) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<Note> notes, int positionStart, int itemCount) {
                    notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeInserted(ObservableList<Note> notes, int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(ObservableList<Note> notes, int i, int i1, int i2) {
                    // Empty stub
                }

                @Override
                public void onItemRangeRemoved(ObservableList<Note> notes, int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                }
            };

    @Inject
    public NoteListAdapter(NoteListViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addOnListChangedCallback(listener);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO
        return null;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        //TODO
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        public NoteViewHolder(View itemView) {
            super(itemView);
        }
    }
}
