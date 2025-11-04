package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameMatchesPredicate;
import seedu.address.model.prescription.HavingPatientIdPredicate;

/**
 * Lists all prescriptions in the address book to the user.
 */
public class ViewPrescriptionsCommand extends Command {
    public static final String COMMAND_WORD = "p-view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all prescriptions tagged to "
        + "the specific patient name and displays them as a list.\n"
        + "Parameters: n/PATIENT_NAME \n"
        + "Example: " + COMMAND_WORD + " n/Alex Yeoh";

    private final HavingPatientIdPredicate predicate;
    private final NameMatchesPredicate predicate2;

    /**
     * Constructor for a ViewPrescription Command
     * @param predicate predicate to check for Prescriptions having the given patient name
     * @param predicate2 predicate to check whether patient exists in addressbook
     */
    public ViewPrescriptionsCommand(HavingPatientIdPredicate predicate, NameMatchesPredicate predicate2) {
        this.predicate = predicate;
        this.predicate2 = predicate2;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPatientList(predicate2);
        if (model.getFilteredPatientList().isEmpty()) {
            throw new CommandException("Patient Not Found in AddressBook!");
        }
        model.updateFilteredPrescriptionList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_PRESCRIPTION_LISTED_OVERVIEW, model.getFilteredPrescriptionList().size()),
            false, false, ViewType.PRESCRIPTION_LIST);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewPrescriptionsCommand)) {
            return false;
        }

        ViewPrescriptionsCommand otherViewPrescriptionCommand = (ViewPrescriptionsCommand) other;
        return predicate.equals(otherViewPrescriptionCommand.predicate)
            && predicate2.equals((otherViewPrescriptionCommand.predicate2));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("predicate", predicate)
            .toString();
    }
}
