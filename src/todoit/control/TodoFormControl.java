package todoit.control;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import javafx.fxml.FXML;

public class TodoFormControl extends StageControl {
    @FXML private TextField textFieldTitle;
    @FXML private TextField textFieldDescription;
    @FXML private TextField textFieldPriority;
    @FXML private Button buttonPriorityInc;
    @FXML private Button buttonPriorityDec;

    @FXML
    protected void handlePriorityInc() {
        // TODO
    }

    @FXML
    protected void handlePriorityDec() {
        // TODO
    }

    @FXML
    @Override
    protected void handleConfirm() {
        super.handleConfirm();
    }
}