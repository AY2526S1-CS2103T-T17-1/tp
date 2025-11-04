package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the Birthday of a patient.
 * Guarantees: immutable and must represent a time in the past.
 */
public class Birthday {

    public static final String MESSAGE_CONSTRAINTS = "Birthday must be after 1900-1-1 and must be a date in the past. "
            + "The only format accepted is yyyy-MM-dd.";
    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}"; // only accept this format for now
    //private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LocalDateTime value;

    /**
     * Constructs an {@code Birthday}.
     *
     * @param value The {@code LocalDateTime} of the Birthday.
     * @throws IllegalArgumentException if {@code value} is after the current time.
     */
    public Birthday(LocalDateTime value) {
        requireNonNull(value);
        if (value.isAfter(LocalDateTime.now()) || value.isBefore(
                LocalDateTime.of(1900, 1, 1, 00, 00))) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = value;
    }

    /**
     * Constructs an {@code Birthday} from a string.
     *
     * @param birthdayString A string in yyyy-MM-dd format.
     * @throws IllegalArgumentException if the string is invalid or represents a future date.
     */
    public Birthday(String birthdayString) {
        requireNonNull(birthdayString);
        if (!isValidBirthday(birthdayString)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        LocalDateTime parsedDate = LocalDateTime.parse(birthdayString + "T00:00:00",
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        if (parsedDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = parsedDate;
    }

    /**
     * Returns true if a given string is a valid birthday format.
     */
    public static boolean isValidBirthday(String birthday) {
        if (birthday == null || !birthday.matches(VALIDATION_REGEX)) {
            return false;
        }

        try {
            LocalDateTime parsedDate = LocalDateTime.parse(birthday + "T00:00:00",
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return !parsedDate.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public LocalDateTime getBirthday() {
        return value;
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Birthday)) {
            return false;
        }
        return value.equals(((Birthday) other).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
