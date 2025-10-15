package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.prescription.Prescription;

/**
 * Prescribe medication for an existing patient
 */
public class AddPrescriptionCommand extends Command {

    public static final String COMMAND_WORD = "p-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Prescribes medication to a patient. "
        + "Parameters: "
        + PREFIX_PATIENT + " PATIENT"
        + PREFIX_MEDICATION + " MEDICATION"
        + PREFIX_DOSAGE + " DOSAGE"
        + PREFIX_FREQUENCY + " FREQUENCY"
        + PREFIX_DURATION + " DURATION";

    public static final String MESSAGE_SUCCESS = "New prescription added: %1$s";

    private final Prescription prescription;

    public AddPrescriptionCommand(Prescription prescription) {
        this.prescription = prescription;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(prescription)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddPrescriptionCommand c)) {
            return false;
        }

        return prescription.equals(c.prescription);
    }
}
