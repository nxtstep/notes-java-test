package io.supersimple.notes.presentation.list;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
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
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.supersimple.notes.R;
import io.supersimple.notes.application.MockApplication;
import io.supersimple.notes.data.models.Note;
import io.supersimple.notes.data.repository.note.NoteRepository;
import io.supersimple.notes.test.rx.activity.RxActivityTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.supersimple.notes.test.TestUtils.withRecyclerView;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class NoteListActivityTest extends RxActivityTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ActivityTestRule activityRule =
            new ActivityTestRule<>(NoteListActivity.class, true, false);

    @Mock
    NoteListActivityComponent.Builder builder;

    @Mock
    NoteRepository mockRepository;

    private Scheduler mainScheduler = AndroidSchedulers.mainThread();
    private Scheduler ioScheduler = Schedulers.io();

    public MockApplication getTargetApplication() {
        return (MockApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }

    private NoteListActivityComponent activityComponent = new NoteListActivityComponent() {
        @Override
        public void injectMembers(NoteListActivity instance) {
            NoteListViewModel viewModel = new NoteListViewModel(mockRepository, mainScheduler, ioScheduler);
            instance.viewModel = viewModel;
            instance.noteListAdapter = new NoteListAdapter(viewModel);
        }
    };

    @Before
    public void setup() {
        super.setup();

        when(builder.build()).thenReturn(activityComponent);
        when(builder.activityModule(any(NoteListActivityComponent.NoteListModule.class))).thenReturn(builder);

        MockApplication mockApplication = getTargetApplication();
        mockApplication.putActivityComponentBuilder(builder, NoteListActivity.class);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testShowNoteList() {
        List<Note> notes = Arrays.asList(Note.create("1", "First note"), Note.create("2", "Second note"));
        when(mockRepository.list()).thenReturn(Maybe.just(notes));

        activityRule.launchActivity(new Intent());

        //
        // Check items in list
        onView(withRecyclerView(R.id.rv_notes)
                .atPositionOnView(1, R.id.tv_note))
                .check(matches(withText("First note")));
        onView(withRecyclerView(R.id.rv_notes)
                .atPositionOnView(2, R.id.tv_note))
                .check(matches(withText("Second note")));
    }

    @Test
    public void testAddNoteToList() {
        when(mockRepository.list()).thenReturn(Maybe.empty());
        when(mockRepository.save(any())).thenAnswer(invocationOnMock ->
                Single.just(invocationOnMock.getArguments()[0]));

        activityRule.launchActivity(new Intent());

        onView(withRecyclerView(R.id.rv_notes)
                .atPositionOnView(0, R.id.tv_note))
                .perform(typeText("Important note"), pressImeActionButton());

        onView(withRecyclerView(R.id.rv_notes)
                .atPositionOnView(1, R.id.tv_note))
                .check(matches(withText("Important note")));
    }
}
