package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.stream.Stream;

import seedu.academyassist.logic.commands.AddClassCommand;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Subject;


/**
 * Parses input arguments and creates a new AddClassCommand object
 */
public class AddClassCommandParser implements Parser<AddClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddClassCommand
     * and returns an AddClassCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddClassCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IC, PREFIX_SUBJECT);

        if (!arePrefixesPresent(argMultimap, PREFIX_IC, PREFIX_SUBJECT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_IC, PREFIX_SUBJECT); // Only can add one class at a time.
        Ic ic = ParserUtil.parseIc(argMultimap.getValue(PREFIX_IC).get());

        try {
            Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get());
            return new AddClassCommand(ic, subject);
        } catch (ParseException pe) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
