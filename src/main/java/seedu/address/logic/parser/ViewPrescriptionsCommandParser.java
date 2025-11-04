package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_PATIENT_NAME_FIELD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewPrescriptionsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameMatchesPredicate;
import seedu.address.model.prescription.HavingPatientIdPredicate;
import seedu.address.model.prescription.exceptions.PrescriptionNotFoundException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ViewPrescriptionsCommandParser implements Parser<ViewPrescriptionsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewPrescriptionCommand
     * and returns a ViewPrescriptionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewPrescriptionsCommand parse(String args) throws ParseException, PrescriptionNotFoundException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_PATIENT);
        if (!arePrefixesPresent(argMultimap, PREFIX_PATIENT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewPrescriptionsCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PATIENT);
        String patientId = argMultimap.getValue(PREFIX_PATIENT).orElse("");
        if (patientId.trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_EMPTY_PATIENT_NAME_FIELD,
                ViewPrescriptionsCommand.MESSAGE_USAGE));
        }

        return new ViewPrescriptionsCommand(new HavingPatientIdPredicate(patientId),
            new NameMatchesPredicate(patientId));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
