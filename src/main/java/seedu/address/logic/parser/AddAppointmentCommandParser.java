package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;

/**
 * Parses input arguments and creates a new {@code AddAppointmentCommand} object.
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    private static final Prefix PREFIX_APPT_PATIENT = new Prefix("n/");
    private static final Prefix PREFIX_APPT_DOCTOR = new Prefix("d/");
    private static final Prefix PREFIX_APPT_TIME = new Prefix("t/");
    private static final Prefix PREFIX_APPT_NOTE = new Prefix("note/");

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm]");

    private static final Set<String> ALLOWED_PREFIXES = Set.of("n/", "d/", "t/", "note/");
    private static final Pattern ANY_PREFIX_PATTERN = Pattern.compile("(^|\\s)([A-Za-z]+/)");

    @Override
    public AddAppointmentCommand parse(String raw) throws ParseException {
        final String original = raw;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                " " + raw, PREFIX_APPT_PATIENT, PREFIX_APPT_DOCTOR, PREFIX_APPT_TIME, PREFIX_APPT_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_APPT_PATIENT, PREFIX_APPT_DOCTOR, PREFIX_APPT_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        if (containsUnknownPrefix(original)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        final String patientName = argMultimap.getValue(PREFIX_APPT_PATIENT).get();
        final String doctor = argMultimap.getValue(PREFIX_APPT_DOCTOR).get();
        final String timeRaw = argMultimap.getValue(PREFIX_APPT_TIME).get();
        final String note = argMultimap.getValue(PREFIX_APPT_NOTE).orElse("");

        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(timeRaw, FORMATTER);
        } catch (DateTimeParseException e) {
            if (looksLikeEmbeddedPrefix(timeRaw)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddAppointmentCommand.MESSAGE_USAGE));
            }
            throw new ParseException("Invalid datetime format. Use yyyy-MM-dd HH:mm");
        }

        Appointment appointment = new Appointment(patientName, dateTime, doctor, note);
        return new AddAppointmentCommand(patientName, appointment);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap map, Prefix... prefixes) {
        for (Prefix p : prefixes) {
            if (map.getValue(p).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static boolean containsUnknownPrefix(String s) {
        Matcher m = ANY_PREFIX_PATTERN.matcher(s);
        while (m.find()) {
            String token = m.group(2);
            if (!ALLOWED_PREFIXES.contains(token)) {
                return true;
            }
        }
        return false;
    }

    private static boolean looksLikeEmbeddedPrefix(String s) {
        return ANY_PREFIX_PATTERN.matcher(" " + s).find();
    }
}
