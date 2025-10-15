package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    // Custom prefixes for prescription
    public static final Prefix PREFIX_PATIENT = new Prefix("/pat");
    public static final Prefix PREFIX_MEDICATION = new Prefix("/med");
    public static final Prefix PREFIX_DOSAGE = new Prefix("/dose");
    public static final Prefix PREFIX_FREQUENCY = new Prefix("/freq");
    public static final Prefix PREFIX_STARTDATE = new Prefix("/start");
    public static final Prefix PREFIX_DURATION = new Prefix("/dur");
    public static final Prefix PREFIX_NOTE = new Prefix("/note");
}
