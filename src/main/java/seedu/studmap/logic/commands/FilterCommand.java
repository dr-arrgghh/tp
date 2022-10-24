package seedu.studmap.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.studmap.commons.core.Messages;
import seedu.studmap.model.Model;
import seedu.studmap.model.student.TagContainsKeywordsPredicate;


/**
 * Filters and list all persons in the address book
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose tags contain any of "
            + "the specified alphabets (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends";

    private final TagContainsKeywordsPredicate predicate;

    public FilterCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.filterPersonListWithTag(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
