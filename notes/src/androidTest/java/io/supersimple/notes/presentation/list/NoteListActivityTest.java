package io.supersimple.notes.presentation.list;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.TestScheduler;
import io.supersimple.notes.R;
import io.supersimple.notes.application.MockApplication;
import io.supersimple.notes.data.models.Note;
import io.supersimple.notes.data.repository.note.NoteRepository;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.supersimple.notes.test.TestUtils.withRecyclerView;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class NoteListActivityTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ActivityTestRule activityRule =
            new ActivityTestRule<>(NoteListActivity.class, true, false);

    @Mock
    NoteListActivityComponent.Builder builder;

    @Mock
    NoteRepository mockRepository;

    protected TestScheduler testScheduler = new TestScheduler();

    protected MockApplication getTargetApplication() {
        return (MockApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }

    private NoteListActivityComponent activityComponent = new NoteListActivityComponent() {
        @Override
        public void injectMembers(NoteListActivity instance) {
            NoteListViewModel viewModel = new NoteListViewModel(mockRepository, AndroidSchedulers.mainThread(), testScheduler);
            instance.viewModel = viewModel;
            instance.noteListAdapter = new NoteListAdapter(viewModel);
        }
    };

    @Before
    public void setup() {
        when(builder.build()).thenReturn(activityComponent);
        when(builder.activityModule(any(NoteListActivityComponent.NoteListModule.class))).thenReturn(builder);

        MockApplication mockApplication = getTargetApplication();
        mockApplication.putActivityComponentBuilder(builder, NoteListActivity.class);
    }

    @Test
    public void testShowNoteList() {
        List<Note> notes = Arrays.asList(Note.create("1", "First note"), Note.create("2", "Second note"));
        when(mockRepository.list()).thenReturn(Maybe.just(notes));

        activityRule.launchActivity(new Intent());
        testScheduler.triggerActions();

        //
        // Check item 0
        onView(withRecyclerView(R.id.rv_notes)
                .atPositionOnView(1, R.id.tv_note))
                .check(matches(withText("First note")));
        onView(withRecyclerView(R.id.rv_notes)
                .atPositionOnView(2, R.id.tv_note))
                .check(matches(withText("Second note")));
    }
}
