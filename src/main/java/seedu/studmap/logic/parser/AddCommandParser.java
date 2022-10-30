package seedu.studmap.logic.parser;

import static seedu.studmap.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.studmap.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.studmap.logic.parser.CliSyntax.PREFIX_GIT;
import static seedu.studmap.logic.parser.CliSyntax.PREFIX_HANDLE;
import static seedu.studmap.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.studmap.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.studmap.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.studmap.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.studmap.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_B;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_D;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_F;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_G;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_I;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_J;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_K;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_L;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_O;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_Q;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_R;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_U;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_V;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_W;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_X;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_Y;
import static seedu.studmap.logic.parser.InvalidCliSyntax.Invalid_Prefix_Z;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.studmap.logic.commands.AddCommand;
import seedu.studmap.logic.parser.exceptions.ParseException;
import seedu.studmap.model.student.Student;
import seedu.studmap.model.student.StudentData;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean containInvalidPrefix(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_MODULE,
                        PREFIX_ID, PREFIX_GIT, PREFIX_HANDLE, PREFIX_TAG);
        ArgumentMultimap invalidMultiMap =
                ArgumentTokenizer.tokenize(args, Invalid_Prefix_B, Invalid_Prefix_D, Invalid_Prefix_F, Invalid_Prefix_G,
                        Invalid_Prefix_I, Invalid_Prefix_J, Invalid_Prefix_K, Invalid_Prefix_L, Invalid_Prefix_O,
                        Invalid_Prefix_Q, Invalid_Prefix_R, Invalid_Prefix_U, Invalid_Prefix_V, Invalid_Prefix_W,
                        Invalid_Prefix_X, Invalid_Prefix_Y, Invalid_Prefix_Z);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MODULE, PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else if (containInvalidPrefix(
                invalidMultiMap, Invalid_Prefix_B, Invalid_Prefix_D, Invalid_Prefix_F, Invalid_Prefix_G,
                Invalid_Prefix_I, Invalid_Prefix_J, Invalid_Prefix_K, Invalid_Prefix_L, Invalid_Prefix_O,
                Invalid_Prefix_Q, Invalid_Prefix_R, Invalid_Prefix_U, Invalid_Prefix_V, Invalid_Prefix_W,
                Invalid_Prefix_X, Invalid_Prefix_Y, Invalid_Prefix_Z)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        StudentData studentData = new StudentData();

        Optional<String> nameString = argMultimap.getValue(PREFIX_NAME);
        if (nameString.isPresent()) {
            studentData.setName(ParserUtil.parseName(nameString.get()));
        }

        Optional<String> phoneString = argMultimap.getValue(PREFIX_PHONE);
        if (phoneString.isPresent()) {
            studentData.setPhone(ParserUtil.parsePhone(phoneString.get()));
        }

        Optional<String> emailString = argMultimap.getValue(PREFIX_EMAIL);
        if (emailString.isPresent()) {
            studentData.setEmail(ParserUtil.parseEmail(emailString.get()));
        }

        Optional<String> moduleString = argMultimap.getValue(PREFIX_MODULE);
        if (moduleString.isPresent()) {
            studentData.setModule(ParserUtil.parseModule(moduleString.get()));
        }

        Optional<String> idString = argMultimap.getValue(PREFIX_ID);
        if (idString.isPresent()) {
            studentData.setId(ParserUtil.parseId(idString.get()));
        }

        Optional<String> gitUserString = argMultimap.getValue(PREFIX_GIT);
        if (gitUserString.isPresent()) {
            studentData.setGitUser(ParserUtil.parseGitName(gitUserString.get()));
        }

        Optional<String> teleHandleString = argMultimap.getValue(PREFIX_HANDLE);
        if (teleHandleString.isPresent()) {
            studentData.setTeleHandle(ParserUtil.parseHandle(teleHandleString.get()));
        }

        studentData.setTags(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)));

        Student student = new Student(studentData);

        return new AddCommand(student);
    }
}
