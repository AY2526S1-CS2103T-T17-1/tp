package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.prescription.Prescription;

/**
 * Panel containing the list of prescriptions.
 */
public class PrescriptionListPanel extends UiPart<Region> {
    private static final String FXML = "PrescriptionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PrescriptionListPanel.class);

    @FXML
    private ListView<Prescription> prescriptionListView;

    /**
     * Creates a {@code PrescriptionListPanel} with the given {@code ObservableList}.
     */
    public PrescriptionListPanel(ObservableList<Prescription> prescriptionList) {
        super(FXML);
        prescriptionListView.setItems(prescriptionList);
        prescriptionListView.setCellFactory(listView -> new PrescriptionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Prescription} using a {@code PrescriptionCard}.
     */
    class PrescriptionListViewCell extends ListCell<Prescription> {
        @Override
        protected void updateItem(Prescription prescription, boolean empty) {
            super.updateItem(prescription, empty);

            if (empty || prescription == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PrescriptionCard(prescription, getIndex() + 1).getRoot());
            }
        }
    }
}
