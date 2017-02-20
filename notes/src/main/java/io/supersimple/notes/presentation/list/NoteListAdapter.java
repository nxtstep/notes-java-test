package io.supersimple.notes.presentation.list;

import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.supersimple.notes.R;
import io.supersimple.notes.data.models.Note;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    private static final int NOTE_ADD = 0;
    private static final int NOTE_ITEM = 1;

    private final NoteListViewModel viewModel;
    private final ObservableList.OnListChangedCallback<ObservableList<Note>> listener =
            new ObservableList.OnListChangedCallback<ObservableList<Note>>() {
                @Override
                public void onChanged(ObservableList<Note> notes) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<Note> notes, int positionStart, int itemCount) {
                    notifyItemRangeChanged(positionStart + 1, itemCount);
                }

                @Override
                public void onItemRangeInserted(ObservableList<Note> notes, int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart + 1, itemCount);
                }

                @Override
                public void onItemRangeMoved(ObservableList<Note> notes, int i, int i1, int i2) {
                    // Empty stub
                }

                @Override
                public void onItemRangeRemoved(ObservableList<Note> notes, int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart + 1, itemCount);
                }
            };

    @Inject
    public NoteListAdapter(NoteListViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addOnListChangedCallback(listener);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final NoteViewHolder holder;
        final View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case NOTE_ADD:
                view = inflater.inflate(R.layout.list_item_note_add, parent, false);
                holder = new EditNoteViewHolder(view, viewModel);
                break;
            case NOTE_ITEM:
                view = inflater.inflate(R.layout.list_item_note, parent, false);
                holder = new NoteViewHolder(view);
                break;
            default:
                return null;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return viewModel.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return NOTE_ADD;
        }
        return NOTE_ITEM;
    }

    private Note getItem(int position) {
        if (position > 0 && viewModel.size() > 0) {
            return viewModel.get(position - 1);
        }
        return null;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_note)
        TextView textView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(Note note) {
            if (note != null) {
                textView.setText(note.value());
            }
        }
    }

    static class EditNoteViewHolder extends NoteViewHolder {
        private final NoteListViewModel viewModel;

        public EditNoteViewHolder(View itemView, NoteListViewModel vm) {
            super(itemView);
            this.viewModel = vm;

            EditText editText = (EditText) textView;
            editText.setOnEditorActionListener((textView, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_NEXT ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    viewModel.addNote(editText.getText().toString());
                    return true;
                }
                return false;
            });
        }
    }
}
