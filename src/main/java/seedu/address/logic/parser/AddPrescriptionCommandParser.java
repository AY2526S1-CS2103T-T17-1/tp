package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddPrescriptionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.prescription.Prescription;

/**
 * Parses input arguments and creates a new AddPrescriptionCommand object
 */
public class AddPrescriptionCommandParser implements Parser<AddPrescriptionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPrescriptionCommand
     * and returns an AddPrescriptionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPrescriptionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PATIENT,
                PREFIX_MEDICATION, PREFIX_DOSAGE, PREFIX_FREQUENCY, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_PATIENT, PREFIX_MEDICATION, PREFIX_DOSAGE,
                PREFIX_FREQUENCY, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPrescriptionCommand.MESSAGE_USAGE));
        }

        String patientId = argMultimap.getValue(PREFIX_PATIENT).orElse("");
        String medicationName = argMultimap.getValue(PREFIX_MEDICATION).orElse("");
        String dosageStr = argMultimap.getValue(PREFIX_DOSAGE).orElse("");
        String frequencyStr = argMultimap.getValue(PREFIX_FREQUENCY).orElse("");
        String startDateStr = argMultimap.getValue(PREFIX_STARTDATE).orElse("");
        String durationStr = argMultimap.getValue(PREFIX_DURATION).orElse("");
        String note = argMultimap.getValue(PREFIX_NOTE).orElse("");

        Float dosage = dosageStr.isEmpty() ? null : Float.parseFloat(dosageStr);
        Integer frequency = frequencyStr.isEmpty() ? null : Integer.parseInt(frequencyStr);
        LocalDateTime startDate = startDateStr.isEmpty() ? null : LocalDateTime.parse(startDateStr);
        Integer duration = durationStr.isEmpty() ? null : Integer.parseInt(durationStr);

        return new AddPrescriptionCommand(new Prescription(patientId, medicationName,
                dosage, frequency, startDate, duration, note));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
