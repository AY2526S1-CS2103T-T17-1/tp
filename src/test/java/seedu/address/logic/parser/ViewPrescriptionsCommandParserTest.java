package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PATIENT_DESC_ALEX_YEOH;
import static seedu.address.logic.commands.CommandTestUtil.PATIENT_DESC_BERNICE_YU;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewPrescriptionsCommand;
import seedu.address.model.person.NameMatchesPredicate;
import seedu.address.model.prescription.HavingPatientIdPredicate;

public class ViewPrescriptionsCommandParserTest {

    private final ViewPrescriptionsCommandParser parser = new ViewPrescriptionsCommandParser();

    @Test
    public void parse_validPatientId_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PATIENT_DESC_ALEX_YEOH,
                new ViewPrescriptionsCommand(new HavingPatientIdPredicate("Alex Yeoh"),
                    new NameMatchesPredicate("Alex Yeoh")));

        // another valid patient id
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PATIENT_DESC_BERNICE_YU,
                new ViewPrescriptionsCommand(new HavingPatientIdPredicate("Bernice Yu"),
                    new NameMatchesPredicate("Bernice Yu")));
    }

    @Test
    public void parse_missingPatientPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewPrescriptionsCommand.MESSAGE_USAGE);

        // missing prefix
        assertParseFailure(parser, "P-10293", expectedMessage);

        // preamble non-empty
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PATIENT_DESC_ALEX_YEOH, expectedMessage);

        // empty string
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_repeatedPatientPrefix_failure() {
        String validPatient = PATIENT_DESC_ALEX_YEOH;

        // repeated patient prefix
        assertParseFailure(parser, PATIENT_DESC_ALEX_YEOH + PATIENT_DESC_ALEX_YEOH,
                "Multiple values specified for the following single-valued field(s): n/");
    }

}
