package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object.
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns a RemarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format("Invalid command format! \n%1$s", RemarkCommand.MESSAGE_USAGE), pe);
        }

        String remarkValue = argMultimap.getValue(PREFIX_REMARK).orElse("");
        Remark remark = new Remark(remarkValue.trim());

        return new RemarkCommand(index, remark);
    }

    private void requireNonNull(String input) throws ParseException {
        if (input == null) {
            throw new ParseException("Input cannot be null.");
        }
    }
    
}
