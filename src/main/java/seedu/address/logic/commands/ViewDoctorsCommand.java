package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * View the list of doctors available.
 */

public class ViewDoctorsCommand extends Command {
    public static final String COMMAND_WORD = "doctors";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all doctors available. ";

    public static final String[] DOCTORS = {
        "Dr Anderson",
        "Dr Boon",
        "Dr Choi"
    };

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(showListOfDoctors());
    }

    private String showListOfDoctors() {
        String doctors = "";
        for (int i = 1; i <= DOCTORS.length; i++) {
            doctors += i + ". " + DOCTORS[i - 1] + "\n";
        }
        return doctors;
    }
}
