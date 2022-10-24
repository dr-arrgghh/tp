package seedu.studmap.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.studmap.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.studmap.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.studmap.logic.commands.CommandTestUtil.VALID_TAG_STUDENT;
import static seedu.studmap.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.studmap.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.studmap.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.studmap.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.studmap.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.studmap.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.studmap.commons.core.Messages;
import seedu.studmap.commons.core.index.Index;
import seedu.studmap.model.AddressBook;
import seedu.studmap.model.Model;
import seedu.studmap.model.ModelManager;
import seedu.studmap.model.UserPrefs;
import seedu.studmap.model.student.Student;
import seedu.studmap.model.tag.Tag;
import seedu.studmap.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditCommand.
 */
public class AddTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Set<Tag> singleTagSet = Stream.of(VALID_TAG_FRIEND).map(Tag::new).collect(Collectors.toSet());
    private Set<Tag> multiTagSet = Stream.of(VALID_TAG_FRIEND, VALID_TAG_STUDENT).map(Tag::new)
            .collect(Collectors.toSet());
    private Set<Tag> multiTagSetReversed = Stream.of(VALID_TAG_STUDENT, VALID_TAG_FRIEND).map(Tag::new)
            .collect(Collectors.toSet());

    @Test
    public void execute_addSingleTagUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Student lastStudent = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastStudent);
        Student editedStudentBase = personInList.withTags(VALID_TAG_HUSBAND).build();
        Student editedStudentExpected = personInList.withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        Model baseModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        baseModel.setPerson(lastStudent, editedStudentBase);
        AddTagCommand addTagCommand = new AddTagCommand(indexLastPerson, singleTagSet);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastStudent, editedStudentExpected);
        String expectedMessage = String.format(
                AddTagCommand.MESSAGE_SINGLE_ADD_TAGS_SUCCESS,
                AddTagCommand.tagSetToSting(singleTagSet),
                editedStudentBase.getName());

        assertCommandSuccess(addTagCommand, baseModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultiTagUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Student lastStudent = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastStudent);
        Student editedStudentBase = personInList.withTags(VALID_TAG_HUSBAND).build();
        Student editedStudentExpected = personInList.withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND, VALID_TAG_STUDENT)
                .build();

        Model baseModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        baseModel.setPerson(lastStudent, editedStudentBase);
        AddTagCommand addTagCommand = new AddTagCommand(indexLastPerson, multiTagSet);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastStudent, editedStudentExpected);
        String expectedMessage = String.format(
                AddTagCommand.MESSAGE_SINGLE_ADD_TAGS_SUCCESS,
                AddTagCommand.tagSetToSting(multiTagSet),
                editedStudentBase.getName());

        assertCommandSuccess(addTagCommand, baseModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addSingleTagAllUnfilteredList_success() {
        PersonBuilder personInList;
        Student editedStudentBase;
        Student editedStudentExpected;

        Model baseModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        String expectedMessage = String.format(
                AddTagCommand.MESSAGE_MULTI_ADD_TAGS_SUCCESS,
                AddTagCommand.tagSetToSting(singleTagSet),
                model.getFilteredPersonList().size());

        for (Student student : model.getFilteredPersonList()) {
            personInList = new PersonBuilder(student);
            editedStudentBase = personInList.withTags(VALID_TAG_HUSBAND).build();
            baseModel.setPerson(student, editedStudentBase);
            editedStudentExpected = personInList.withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
            expectedModel.setPerson(student, editedStudentExpected);
        }

        AddTagCommand addTagCommand = new AddTagCommand(singleTagSet);
        assertCommandSuccess(addTagCommand, baseModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultiTagAllUnfilteredList_success() {
        PersonBuilder personInList;
        Student editedStudentBase;
        Student editedStudentExpected;

        Model baseModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        String expectedMessage = String.format(
                AddTagCommand.MESSAGE_MULTI_ADD_TAGS_SUCCESS,
                AddTagCommand.tagSetToSting(multiTagSet),
                model.getFilteredPersonList().size());

        for (Student student : model.getFilteredPersonList()) {
            personInList = new PersonBuilder(student);
            editedStudentBase = personInList.withTags(VALID_TAG_HUSBAND).build();
            baseModel.setPerson(student, editedStudentBase);
            editedStudentExpected = personInList.withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND, VALID_TAG_STUDENT)
                    .build();
            expectedModel.setPerson(student, editedStudentExpected);
        }

        AddTagCommand addTagCommand = new AddTagCommand(multiTagSet);
        assertCommandSuccess(addTagCommand, baseModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateTagUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Student lastStudent = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastStudent);
        Student editedStudentBase = personInList.withTags(VALID_TAG_FRIEND).build();
        Student editedStudentModel = personInList.withTags(VALID_TAG_FRIEND, VALID_TAG_STUDENT).build();

        Model baseModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        baseModel.setPerson(lastStudent, editedStudentBase);
        AddTagCommand addTagCommand = new AddTagCommand(indexLastPerson, multiTagSet);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastStudent, editedStudentModel);
        String expectedMessage = String.format(
                AddTagCommand.MESSAGE_SINGLE_ADD_TAGS_SUCCESS,
                AddTagCommand.tagSetToSting(multiTagSet),
                editedStudentBase.getName());

        assertCommandSuccess(addTagCommand, baseModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addToNewSingleTagUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Student lastStudent = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastStudent);
        Student editedStudentBase = personInList.withTags().build();
        Student editedStudentModel = personInList.withTags(VALID_TAG_FRIEND).build();

        Model baseModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        baseModel.setPerson(lastStudent, editedStudentBase);
        AddTagCommand addTagCommand = new AddTagCommand(indexLastPerson, singleTagSet);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastStudent, editedStudentModel);
        String expectedMessage = String.format(
                AddTagCommand.MESSAGE_SINGLE_ADD_TAGS_SUCCESS,
                AddTagCommand.tagSetToSting(singleTagSet),
                editedStudentBase.getName());

        assertCommandSuccess(addTagCommand, baseModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addSingleTagAllFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        PersonBuilder personInList;
        Student editedStudentBase;
        Student editedStudentExpected;

        Model baseModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        String expectedMessage = String.format(
                AddTagCommand.MESSAGE_MULTI_ADD_TAGS_SUCCESS,
                AddTagCommand.tagSetToSting(singleTagSet),
                model.getFilteredPersonList().size());

        for (Student student : model.getFilteredPersonList()) {
            personInList = new PersonBuilder(student);
            editedStudentBase = personInList.withTags(VALID_TAG_HUSBAND).build();
            baseModel.setPerson(student, editedStudentBase);
            editedStudentExpected = personInList.withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
            expectedModel.setPerson(student, editedStudentExpected);
        }

        showPersonAtIndex(baseModel, INDEX_SECOND_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(singleTagSet);
        assertCommandSuccess(addTagCommand, baseModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultiTagFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Student lastStudent = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastStudent);
        Student editedStudentBase = personInList.withTags(VALID_TAG_HUSBAND).build();
        Student editedStudentExpected = personInList.withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        Model baseModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        baseModel.setPerson(lastStudent, editedStudentBase);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastStudent, editedStudentExpected);
        String expectedMessage = String.format(
                AddTagCommand.MESSAGE_SINGLE_ADD_TAGS_SUCCESS,
                AddTagCommand.tagSetToSting(singleTagSet),
                editedStudentBase.getName());

        showPersonAtIndex(baseModel, INDEX_SECOND_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(indexLastPerson, singleTagSet);

        assertCommandSuccess(addTagCommand, baseModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, singleTagSet);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, singleTagSet);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddTagCommand standardSingleCommand = new AddTagCommand(INDEX_FIRST_PERSON, singleTagSet);
        final AddTagCommand standardMultiCommand = new AddTagCommand(INDEX_FIRST_PERSON, multiTagSetReversed);

        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(INDEX_FIRST_PERSON, singleTagSet);
        assertTrue(standardSingleCommand.equals(commandWithSameValues));

        // same values but different order -> returns true
        AddTagCommand commandWithSameMultiValues = new AddTagCommand(INDEX_FIRST_PERSON, multiTagSetReversed);
        assertTrue(standardMultiCommand.equals(commandWithSameMultiValues));

        // same object -> returns true
        assertTrue(standardSingleCommand.equals(standardSingleCommand));

        // null -> returns false
        assertFalse(standardSingleCommand.equals(null));

        // different types -> returns false
        assertFalse(standardSingleCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardSingleCommand.equals(new AddTagCommand(INDEX_SECOND_PERSON, singleTagSet)));

        // different descriptor -> returns false
        assertFalse(standardSingleCommand.equals(new AddTagCommand(INDEX_FIRST_PERSON, multiTagSet)));
    }

    public static void main(String[] args) {
        AddTagCommandTest test = new AddTagCommandTest();
        test.execute_invalidPersonIndexFilteredList_failure();
    }
}
