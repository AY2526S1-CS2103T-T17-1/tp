package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.prescription.HavingPatientIdPredicate;

/**
 * Lists all prescriptions in the address book to the user.
 */
public class ViewPrescriptionsCommand extends Command {
    public static final String COMMAND_WORD = "p-view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all prescriptions tagged to "
        + "the specific patient id and displays them as a list.\n"
        + "Parameters: /patient [patient_id]\n"
        + "Example: " + COMMAND_WORD + " /patient P-10293";

    private final HavingPatientIdPredicate predicate;

    public ViewPrescriptionsCommand(HavingPatientIdPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
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
        return predicate.equals(otherViewPrescriptionCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("predicate", predicate)
            .toString();
    }
}
