package io.supersimple.notes.presentation.list;


import java.util.LinkedList;
import java.util.List;

import io.supersimple.notes.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.supersimple.notes.test.TestUtils.withRecyclerView;

public class NoteListTestRobot {
    List<String> expectedList = new LinkedList<>();

    NoteListTestRobot expectedNote(String noteText) {
        expectedList.add(noteText);
        return this;
    }

    NoteListTestRobot expectedNotes(List<String> notes) {
        expectedList.addAll(notes);
        return this;
    }

    NoteListTestRobot addNote(String noteText) {
        expectedList.add(noteText);

        onView(withRecyclerView(R.id.rv_notes)
                .atPositionOnView(0, R.id.tv_note))
                .perform(typeText(noteText), pressImeActionButton());

        return this;
    }

    ResultRobot result() {
        return new ResultRobot(this);
    }

    class ResultRobot {
        private final List<String> notes;

        public ResultRobot(NoteListTestRobot robot) {
            this.notes = robot.expectedList;
        }

        ResultRobot verify() {
            for (int i = 1; i <= notes.size(); i++) {
                onView(withId(R.id.rv_notes))
                        .perform(scrollToPosition(i));

                onView(withRecyclerView(R.id.rv_notes)
                        .atPositionOnView(i, R.id.tv_note))
                        .check(matches(withText(notes.get(i - 1))));
            }
            return this;
        }
    }
}
