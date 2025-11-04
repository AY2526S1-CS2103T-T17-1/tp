package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.address.logic.commands.ViewAppointmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for {@link ViewAppointmentsCommand}.
 *
 * Format: a-view n/PATIENT_NAME [from/YYYY-MM-DD] [to/YYYY-MM-DD]
 */
public class ViewAppointmentsCommandParser implements Parser<ViewAppointmentsCommand> {

    private static final Prefix PREFIX_PATIENT = new Prefix("n/");
    private static final Prefix PREFIX_FROM = new Prefix("from/");
    private static final Prefix PREFIX_TO = new Prefix("to/");

    @Override
    public ViewAppointmentsCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(
                " " + args, PREFIX_PATIENT, PREFIX_FROM, PREFIX_TO);

        if (!map.getValue(PREFIX_PATIENT).isPresent() || !map.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewAppointmentsCommand.MESSAGE_USAGE));
        }

        map.verifyNoDuplicatePrefixesFor(PREFIX_PATIENT, PREFIX_FROM, PREFIX_TO);

        String patientName = map.getValue(PREFIX_PATIENT).get();

        Optional<LocalDate> fromDate = parseIsoDateOpt(map.getValue(PREFIX_FROM));
        Optional<LocalDate> toDate = parseIsoDateOpt(map.getValue(PREFIX_TO));

        // Validate range if both present
        if (fromDate.isPresent() && toDate.isPresent() && fromDate.get().isAfter(toDate.get())) {
            throw new ParseException("Invalid range: from-date is after to-date.");
        }

        return new ViewAppointmentsCommand(patientName, fromDate, toDate);
    }

    private Optional<LocalDate> parseIsoDateOpt(Optional<String> raw) throws ParseException {
        if (raw.isEmpty()) {
            return Optional.empty();
        }
        try {
            DateTimeFormatter strictFormatter = DateTimeFormatter
                    .ofPattern("uuuu-MM-dd")
                    .withResolverStyle(java.time.format.ResolverStyle.STRICT);
            return Optional.of(LocalDate.parse(raw.get(), strictFormatter));
        } catch (DateTimeParseException e) {
            throw new ParseException(
                "Invalid date. Please use format YYYY-MM-DD and ensure it is a real calendar date.");
        }
    }
}
