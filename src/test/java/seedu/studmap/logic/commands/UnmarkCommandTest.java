package seedu.studmap.logic.commands;

import static seedu.studmap.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.studmap.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.studmap.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.studmap.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.studmap.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.studmap.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.studmap.commons.core.Messages;
import seedu.studmap.commons.core.index.Index;
import seedu.studmap.model.Model;
import seedu.studmap.model.ModelManager;
import seedu.studmap.model.UserPrefs;
import seedu.studmap.model.student.Attendance;
import seedu.studmap.model.student.Student;
import seedu.studmap.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkCommand}.
 */
class UnmarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToUnmark = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Attendance attendance = new Attendance("T01", true);
        UnmarkCommand unmarkCommand = new UnmarkCommand(INDEX_SECOND_PERSON, attendance);

        Set<Attendance> attendanceSet = new HashSet<>(studentToUnmark.getAttendances());
        attendanceSet.remove(attendance);
        Student unmarkedStudent = new PersonBuilder(studentToUnmark).setAttended(attendanceSet).build();

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_SUCCESS,
                attendance.className, unmarkedStudent);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()), unmarkedStudent);
        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Student studentInFilteredList = model.getFilteredPersonList().get(0);
        Attendance attendance = new Attendance("T01", true);
        UnmarkCommand unmarkCommand = new UnmarkCommand(INDEX_FIRST_PERSON, attendance);

        Set<Attendance> attendanceSet = new HashSet<>(studentInFilteredList.getAttendances());
        attendanceSet.remove(attendance);
        Student unmarkedStudent = new PersonBuilder(studentInFilteredList).setAttended(attendanceSet).build();

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_SUCCESS,
                attendance.className, unmarkedStudent);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), unmarkedStudent);
        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Attendance attendance = new Attendance("T04", true);
        UnmarkCommand unmarkCommand = new UnmarkCommand(outOfBoundIndex, attendance);

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
