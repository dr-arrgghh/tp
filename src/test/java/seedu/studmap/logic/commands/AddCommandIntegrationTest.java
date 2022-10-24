package seedu.studmap.logic.commands;

import static seedu.studmap.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.studmap.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.studmap.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.studmap.model.Model;
import seedu.studmap.model.ModelManager;
import seedu.studmap.model.UserPrefs;
import seedu.studmap.model.student.Student;
import seedu.studmap.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Student validStudent = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validStudent);

        assertCommandSuccess(new AddCommand(validStudent), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validStudent), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Student studentInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(studentInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
