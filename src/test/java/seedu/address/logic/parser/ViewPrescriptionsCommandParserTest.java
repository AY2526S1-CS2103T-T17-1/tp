package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PATIENT_DESC_P10293;
import static seedu.address.logic.commands.CommandTestUtil.PATIENT_DESC_P20485;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewPrescriptionsCommand;
import seedu.address.model.prescription.HavingPatientIdPredicate;

public class ViewPrescriptionsCommandParserTest {

    private final ViewPrescriptionsCommandParser parser = new ViewPrescriptionsCommandParser();

    @Test
    public void parse_validPatientId_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PATIENT_DESC_P10293,
            new ViewPrescriptionsCommand(new HavingPatientIdPredicate("P-10293")));

        // another valid patient id
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PATIENT_DESC_P20485,
            new ViewPrescriptionsCommand(new HavingPatientIdPredicate("P-20485")));
    }

    @Test
    public void parse_missingPatientPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ViewPrescriptionsCommand.MESSAGE_USAGE);

        // missing prefix
        assertParseFailure(parser, "P-10293", expectedMessage);

        // preamble non-empty
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PATIENT_DESC_P10293, expectedMessage);

        // empty string
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_repeatedPatientPrefix_failure() {
        String validPatient = PATIENT_DESC_P10293;

        // repeated patient prefix
        assertParseFailure(parser, PATIENT_DESC_P10293 + PATIENT_DESC_P10293,
            "Multiple values specified for the following single-valued field(s): /patient");
    }

}
