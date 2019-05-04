package todoit.control;

import todoit.TodoList;
import todoit.util.PopupHelper;

import javafx.fxml.FXML;
import javafx.stage.Window;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ListFormControl extends StageControl {
    @FXML private TextField textFieldTitle;
    @FXML private TextField textFieldDescription;

    @FXML
    public void initialize() {
        buttonConfirm.disableProperty().bind(textFieldTitle
            .lengthProperty()
            .lessThan(3));
    }

    @FXML
    @Override
    protected void handleConfirm() {
        String title = textFieldTitle.getText();
        String description = textFieldDescription.getText();
        Window owner = buttonConfirm.getScene().getWindow();
        TodoList list = new TodoList(title, description);

        if(todoManager.getLists().stream().noneMatch(l -> l.getTitle().equals(title)))
            addListAndConfirm(list);
        else {
            PopupHelper.showAlert(AlertType.CONFIRMATION, owner, "Duplicate List", "List with the title \"" + title + "\" already exists. Overwrite it?")
                .filter(b -> b == ButtonType.OK)
                .ifPresent(b -> addListAndConfirm(list));
        }
    }
    
    private void addListAndConfirm(TodoList list) {
        todoManager.addList(list);
        todoManager.saveLists();
        super.handleConfirm();
        ((HomeControl)parentControl).setTodoList(list);
    }

    @FXML
    @Override
    protected void handleCancel() {
        Window owner = buttonCancel.getScene().getWindow();

        if(textFieldTitle.getText().length() > 0
        || textFieldDescription.getText().length() > 0) {
            PopupHelper.showAlert(
                AlertType.WARNING, owner, buttonCancel.getText(),
                "All input will be lost. Continue?")
                    .filter(r -> r == ButtonType.OK)
                    .ifPresent(r -> super.handleCancel());
        } else super.handleCancel();
    }
}