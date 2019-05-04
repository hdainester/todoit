package todoit.control;

import todoit.TodoManager;
import todoit.util.PopupHelper;
import todoit.TodoList;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Window;
import javafx.fxml.FXML;

public class BrowserControl extends StageControl {
    @FXML private ListView<TodoList> listViewTodoList;
    @FXML private Label labelHeader;
    @FXML private Button buttonDelete;

    @Override
    public void setManager(TodoManager manager) {
        super.setManager(manager);

        if(todoManager != null) {
            todoManager.loadLists();

            if(listViewTodoList != null)
                listViewTodoList.getItems()
                    .setAll(todoManager.getLists());
        }
    }

    @FXML
    public void initialize() {
        buttonDelete.disableProperty().bind(listViewTodoList
            .getSelectionModel()
            .selectedItemProperty()
            .isNull());

        buttonConfirm.disableProperty().bind(buttonDelete
            .disableProperty());

        listViewTodoList.setOnMouseClicked(e -> {
            TodoList selected = listViewTodoList.getSelectionModel().getSelectedItem();
            if(selected != null && e.getClickCount() == 2)
                handleConfirm();
        });
    }

    @FXML
    public void handleDelete() {
        Window owner = buttonDelete.getScene().getWindow();
        TodoList selected = listViewTodoList.getSelectionModel().getSelectedItem();

        if(selected != null) {
            PopupHelper.showAlert(AlertType.CONFIRMATION, owner, "Delete", "Delete TodoList \"" + selected + "\"?")
                .filter(b -> b == ButtonType.OK)
                .ifPresent(b -> {
                    listViewTodoList.getItems().remove(selected);
                    todoManager.removeList(selected);
                    todoManager.saveLists();
                });
        }
    }

    @FXML
    @Override
    public void handleConfirm() {
        TodoList todoList = listViewTodoList.getSelectionModel().getSelectedItem();
        ((HomeControl)parentControl).setTodoList(todoList);
        super.handleConfirm();
    }
}