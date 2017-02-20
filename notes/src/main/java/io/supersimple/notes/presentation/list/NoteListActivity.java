package io.supersimple.notes.presentation.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.supersimple.notes.R;
import io.supersimple.notes.di.activity.ActivityComponentBuilderProvider;
import io.supersimple.notes.presentation.ComponentActivity;

public class NoteListActivity extends ComponentActivity implements NoteListView {

    @BindView(R.id.rv_notes)
    RecyclerView noteListView;

    @Inject
    NoteListAdapter noteListAdapter;
    @Inject
    NoteListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        ButterKnife.bind(this);

        noteListView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        noteListView.setAdapter(noteListAdapter);
    }

    @Override
    protected void injectMembers(ActivityComponentBuilderProvider componentBuilderProvider) {
        componentBuilderProvider.getActivityComponentBuilder(NoteListActivity.class)
                .activityModule(new NoteListActivityComponent.NoteListModule(this))
                .build()
                .injectMembers(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.bind(this);
    }

    @Override
    protected void onPause() {
        viewModel.unbind();
        super.onPause();
    }

    @Override
    public void showError(Throwable error) {
        //TODO
    }

    @Override
    public void showEmpty() {
        //TODO
    }

    @Override
    public void showLoading(boolean flag) {
        //TODO
    }
}
