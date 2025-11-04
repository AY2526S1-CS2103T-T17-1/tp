---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the HospitalAdminProMax application.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App, handling patient, appointment, and prescription views.
* [**`Logic`**](#logic-component): The command executor for patient info, appointments, and prescriptions.
* [**`Model`**](#model-component): Holds the data of the App including patients, appointments, and prescriptions.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk in JSON format.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `i-delete n/John Doe`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Patient` `Appointment` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a patient).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

* The appointment commands work by first routing their respective command parsers e.g. `AddAppointmentCommandParser`  to construct `AddAppointmentCommand` and `ViewAppointmentsCommand` objects.
* On execution, the `AddAppointmentCommand` objects validates the appointment before appending the `Appointment` to the model.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S1-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="839" />

The `Model` component,

* stores the hospital management system data including:
    * All `Patient` objects (contained in a `UniquePatientList` object)
    * All `Appointment` objects (contained in a `UniqueAppointmentList` object)
    * All `Prescription` objects (contained in a `UniquePrescriptionList` object)
* stores the currently 'selected' objects as separate _filtered_ lists which are exposed to outsiders as unmodifiable `ObservableList<T>` that can be 'observed'
* stores a `UserPref` object that represents the user's preferences
* does not depend on any of the other three components

**Key Design Decisions:**
* **Separation of Entities**: Patients, Appointments, and Prescriptions are separate entities to maintain clear domain boundaries
* **Relationships**: Appointments and Prescriptions reference Patients by name rather than direct object references for simplicity and data integrity
* **Filtered Lists**: Each entity type maintains its own filtered list for UI display purposes


### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S1-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

**Key Features:**
- **Multi-Entity Storage**: Handles serialization of `Patient`, `Appointment`, and `Prescription` objects
- **JSON Adaptation**: Uses `JsonAdaptedPatient`, `JsonAdaptedAppointment`, and `JsonAdaptedPrescription` for JSON serialization
- **Data Integrity**: Maintains relationships between entities during serialization/deserialization
--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

## Patient Information Management Feature

### Implementation
The patient information management mechanism is facilitated by `Patient` and `UniquePatientList`. It stores comprehensive patient details including personal information, emergency contacts, and medical identification.

**Patient Entity Structure:**
```java
public class Patient {
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Birthday birthday;
    private final String gender;
    private final String emergency;  // Emergency contact
    private final String id;         // Patient ID
    private final String lang;       // Language preference
    private final Address address;
}
```

**Key Operations in UniquePatientList:**
* `contains(Patient)` - Checks if a patient exists using `isSamePatient()`
* `add(Patient)` - Adds a patient with duplicate name checking
* `remove(Patient)` - Removes a patient using exact equality matching
* `setPatient(Patient, Patient)` - Updates a patient with uniqueness validation

### Design Considerations:
**Aspect: How to handle patient identity:**
* **Alternative 1 (current choice):** Use name as primary identifier
    * Pros: Simple for users to remember and reference
    * Cons: Cannot handle patients with identical names
* **Alternative 2:** Use patient ID as primary identifier
    * Pros: Guaranteed uniqueness, follows hospital standards
    * Cons: Less user-friendly, requires users to remember IDs

## Appointment Management Feature

### Implementation
The appointment management system allows scheduling patient appointments with doctors with built-in conflict detection to prevent double-booking.

**Appointment Entity Structure:**
```java
public class Appointment {
    private final String patientName;    // References patient by name
    private final LocalDateTime dateTime;
    private final String doctor;
    private final String reason;         // Visit purpose/notes
}
```

**Key Operations in UniqueAppointmentList:**
* `contains(Appointment)` - Checks for exact appointment duplicates
* `add(Appointment)` - Prevents duplicate appointments
* `remove(Appointment)` - Removes specific appointment matches

**Conflict Detection Implementation:**
Time slot conflict detection is implemented in `AddAppointmentCommand`:
```java
boolean hasClashingAppointment = model.getAddressBook()
        .getAppointmentList()
        .stream()
        .anyMatch(existingAppointment ->
                existingAppointment.getPatientName().equals(toAdd.getPatientName())
                        && existingAppointment.getDateTime().equals(toAdd.getDateTime()));

if (hasClashingAppointment) {
    throw new CommandException(MESSAGE_TIMESLOT_CLASH);
}
```

Here's how to incorporate the prescription workflow diagrams into your Implementation section:

## Prescription Management Feature

### Implementation
The prescription system manages patient medications with comprehensive dosage, frequency, and duration information.

**Prescription Entity Structure:**
```java
public class Prescription {
    private final String patientId;      // References patient by ID
    private final String medicationName;
    private final Float dosage;          // In milligrams
    private final Integer frequency;     // Times per day
    private final LocalDateTime startDate;
    private final Integer duration;      // In days
    private final String note;           // Additional instructions
}
```

**Class Structure:**
The following class diagram shows the relationship between prescription-related classes:


**Key Operations in UniquePrescriptionList:**
* `contains(Prescription)` - Checks for duplicate prescriptions
* `add(Prescription)` - Prevents duplicate prescriptions
* `setPrescription(Prescription, Prescription)` - Updates prescriptions with uniqueness validation
* `remove(Prescription)` - Removes specific prescriptions

### Prescription Addition Workflow
Adding a prescription involves command parsing, validation, and persistence as shown in the following sequence:

<img src="images/PrescriptionAdditionSequenceDiagram.png" width="800" />

The process follows these steps:
1. **Command Parsing**: User input is parsed by `AddPrescriptionCommandParser` which extracts and validates all parameters
2. **Patient Validation**: System verifies the patient exists before creating prescription
3. **Duplicate Check**: `UniquePrescriptionList` ensures no identical prescription already exists
4. **Persistence**: New prescription is added to model and saved to storage

**Command Parsing Details:**
The parsing sequence ensures all required parameters are present and properly formatted:

<img src="images/PrescriptionCommandParsingSequenceDiagram.png" width="500" />

### Prescription Viewing Workflow
Viewing prescriptions follows a decision-based workflow:

<img src="images/PrescriptionViewingSequenceDiagram.png" width="400" />

Key validation points include:
- Command format validation
- Patient existence check
- Prescription availability check

### Prescription Deletion Workflow
Deleting prescriptions involves index-based selection and UI updates:

<img src="images/PrescriptionDeletionSequenceDiagram.png" width="600" />

The deletion process:
1. **Index Validation**: Ensures the provided index is valid within the current filtered list
2. **Target Identification**: Retrieves the specific prescription to delete
3. **Removal Execution**: Removes prescription from the model
4. **UI Update**: Refreshes the displayed prescription list
5. **Data Persistence**: Saves changes to storage

### Design Considerations:
**Aspect: Prescription Reference Method**
* **Current Choice**: Reference patients by ID in prescriptions
    * Pros: Consistent patient identification, follows database conventions
    * Cons: Inconsistent with appointments which use patient names
* **Alternative**: Reference patients by name in all entities
    * Pros: Uniform reference method across the system
    * Cons: Potential issues with duplicate patient names

**Aspect: Prescription Uniqueness**
* **Current Choice**: Exact match on all fields prevents duplicates
    * Pros: Prevents identical prescriptions for same patient
    * Cons: May be too restrictive for legitimate duplicate medications
* **Alternative**: Allow duplicates with different start dates
    * Pros: Supports medication refills and course repetitions
    * Cons: Requires more sophisticated duplicate detection

The prescription system provides robust medication management with comprehensive validation and clear user workflows, though some reference consistency issues exist between different entity types.

## Data Integrity Considerations

**Reference Consistency:**
- Appointments reference patients by name
- Prescriptions reference patients by ID
- This inconsistency may complicate data relationships

**Cascading Operations:**
- Patient deletion does not automatically remove related appointments and prescriptions

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th patient in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new patient. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the patient was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the patient being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**: Hospital Admin Manager at General Medicine Department

**Value proposition**: provide fast access to patient information and details using CLI


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                    | So that I can…​                                            |
|----------| ------------------------------------------ |---------------------------------|------------------------------------------------------------|
| `* * *`  | user                                   | view all patients               | see a complete list of all possible patient's information. |
| `* * *`  | user                                       | add a patient's information     |                                                            |
| `* * *`  | user                                       | delete a patient's information  | remove entries that I no longer need                       |
| `* * *`  | user                                   | view all patient's appointments | see a complete list of appointments for a given patient.   |
| `* * *`  | user                                       | add a patient's appointment     |                                                            |
| `* * *`  | user                                       | delete a patient's appointment  | remove entries that I no longer need                       |
| `* * *`  | user                                   | view all patient's prescription | see what I have prescribed to the patient                     |
| `* * *`  | user                                       | add a patient's prescription    | provide treatment plan for the patient                     |
| `* * *`  | user                                       | delete a patient's appointment  | remove entries that I no longer need                       |


### Use cases

(For all use cases below, the **System** is the `HospitalContactsXPM` and the **Actor** is the `user`, unless specified otherwise)


**Use case: Add a patient's information**

**MSS**

1. User requests to add a new patient with required information
2. System validates the patient data
3. System adds the patient to the database
4. System displays success message

Use case ends.

**Extensions**
* 2a. Patient data is invalid or missing required fields
    * 2a1. System shows error message with required fields
    * Use case resumes at step 1


**Use case: Schedule an appointment**

**MSS**

1. User requests to schedule an appointment for a patient
2. System validates patient exists and time slot is available
3. System creates the appointment
4. System displays success message

Use case ends.

**Extensions**
* 2a. Patient does not exist
    * 2a1. System shows "Patient not found" error
    * Use case ends
* 2b. Time slot is already booked
    * 2b1. System shows "Time slot clash" error
    * Use case resumes at step 1

**Use case: Delete a patient's appointment**

**MSS**

1.  User requests to list patient's appointments
2.  HospitalContactsXPM shows a list of patient's appointments
3.  User requests to delete a specific appointment in the list
4.  HospitalContactsXPM deletes the appointment

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given appointment ID in invalid.

    * 3a1. HospitalContactsXPM shows an error message.

      Use case resumes at step 2.

**Use case: Add a prescription for a patient**

**MSS**

1. User requests to prescribe medication to a patient.
2. HospitalContactsXPM prompts the user for the required details: patient, medication, dosage, frequency, and duration.
3. User provides all required details in the correct format.
4. HospitalContactsXPM adds the prescription to the patient's record.
5. HospitalContactsXPM displays a success message confirming the prescription has been added.

Use case ends.

**Extensions**

* 2a. The patient list is empty.

Use case ends.

* 3a. The command format is invalid (e.g., missing parameters or incorrect prefixes).
  * 3a1. HospitalContactsXPM shows an error message indicating the correct command format:
  p-add: Prescribes medication to a patient. Parameters: p/ PATIENT m/ MEDICATION d/ DOSAGE f/ FREQUENCY dur/ DURATION.

Use case resumes from step 2.

* 3b. The specified patient does not exist.
  * 3b1. HospitalContactsXPM shows an error message indicating an invalid patient ID.

Use case resumes from step 2.

* 3c. The same prescription already exists for the patient.
  * 3c1. HospitalContactsXPM notifies the user that the prescription already exists.

Use case ends.

**Use case: View a patient's prescriptions**

**MSS**

1. User requests to view prescriptions for a specific patient
2. System validates patient exists
3. System retrieves all prescriptions for the patient
4. System displays the prescriptions

Use case ends.

**Extensions**
* 2a. Patient does not exist
    * 2a1. System shows "Patient not found" error
    * Use case ends

**Use case: Delete a prescription**

**MSS**

1. User requests to list prescriptions
2. System shows list of prescriptions
3. User requests to delete a specific prescription
4. System deletes the prescription

Use case ends.

**Extensions**
* 2a. The list is empty
    * Use case ends
* 3a. The given index is invalid
    * 3a1. System shows error message
    * Use case resumes at step 2

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 patients without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should be able to list 100 patients at a time on the UI without visible lag.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Patient**: A patient that use healthcare services of the hospital
* **Hospital Admin Manager**: A person that has full access to patient's database

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch
    1. Download the jar file and copy into an empty folder
    1. Double-click the jar file<br>
       Expected: Shows the GUI with a set of sample patients, appointments, and prescriptions.

1. Saving window preferences
    1. Resize the window to an optimum size. Move the window to a different location. Close the window.
    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Patient Information Management

1. Adding a patient
    1. Prerequisites: None
    1. Test case: `i-add n/John Lee dob/1985-08-20 g/Male p/91234567 e/johnlee@example.com a/456 Orchard Road em/Mary Lee - 87654321 id/S9876543B lang/English`<br>
       Expected: New patient "John Lee" is added to the list. Success message shown.
    1. Test case: `i-add n/John Lee dob/1990-01-01 g/Male p/91111111 e/test@test.com a/Test Street em/Test - 92222222 id/S1111111A lang/Chinese`<br>
       Expected: No patient added. Error message about duplicate name.

1. Viewing patient information
    1. Prerequisites: Patient "John Lee" exists in the system (from previous test)
    1. Test case: `i-view n/John Lee`<br>
       Expected: John Lee's patient details are displayed in the result box.

1. Deleting a patient
    1. Prerequisites: Patient "John Lee" exists in the system
    1. Test case: `i-delete n/John Lee`<br>
       Expected: Patient "John Lee" is deleted from the list. Success message shown.

### Prescription Management

1. Adding a prescription
    1. Prerequisites: Patient "John Lee" exists in the system
    1. Test case: `p-add n/John Lee m/Paracetamol d/500 f/2 dur/7`<br>
       Expected: Prescription for John Lee added. Success message shown.
    1. Test case: `p-add n/John Lee m/Amoxicillin d/250 f/3 dur/10`<br>
       Expected: Second prescription for John Lee added. Success message shown.

1. Viewing prescriptions
    1. Prerequisites: Patient "John Lee" has existing prescriptions
    1. Test case: `p-view n/John Lee`<br>
       Expected: All prescriptions for John Lee are displayed.

1. Deleting prescriptions
    1. Prerequisites: Patient "John Lee" has at least one prescription
    1. Test case: `p-delete 1` (after viewing John Lee's prescriptions)<br>
       Expected: First prescription for John Lee is deleted. Success message shown.

### Appointment Management

1. Scheduling an appointment
    1. Prerequisites: Patient "John Lee" exists in the system
    1. Test case: `a-add n/John Lee d/Dr Wee t/2025-11-11 14:00 note/Follow-up`<br>
       Expected: Appointment for John Lee added. Success message shown.
    1. Test case: `a-add n/John Lee d/Dr Tan t/2025-12-01 09:30 note/Annual check-up`<br>
       Expected: Second appointment for John Lee added. Success message shown.

1. Viewing appointments
    1. Prerequisites: Patient "John Lee" has existing appointments
    1. Test case: `a-view n/John Lee`<br>
       Expected: All appointments for John Lee are displayed.

1. Deleting appointments
    1. Prerequisites: Patient "John Lee" has an appointment at a specific time
    1. Test case: `a-delete n/John Lee t/2025-11-11 14`<br>
       Expected: John Lee's appointment at 2025-11-11 14:00 is deleted. Success message shown.

### Data Persistence

1. Data integrity across sessions
    1. Add patient "John Lee" with `i-add n/John Lee dob/1985-08-20 g/Male p/91234567 e/johnlee@example.com a/456 Orchard Road em/Mary Lee - 87654321 id/S9876543B lang/English`
    1. Add prescription for John Lee with `p-add n/John Lee m/Paracetamol d/500 f/2 dur/7`
    1. Add appointment for John Lee with `a-add n/John Lee d/Dr Wee t/2025-11-11 14:00 note/Follow-up`
    1. Close the application and restart it<br>
       Expected: All John Lee's data (patient info, prescription, appointment) persists and is displayed correctly

