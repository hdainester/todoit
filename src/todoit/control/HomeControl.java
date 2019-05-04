package todoit.control;

import todoit.util.PopupHelper;
import todoit.TodoList;
import todoit.Todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Window;
import javafx.fxml.FXML;

public class HomeControl extends StageControl {
    private ObservableList<Todo> listModel;

    @FXML private Label labelHeader;
    @FXML private Button buttonNewList;
    @FXML private Button buttonOpenList;
    @FXML private Button buttonNewTodo;
    @FXML private Button buttonEditTodo;
    @FXML private Button buttonDeleteTodo;
    @FXML private ListView<Todo> listViewTodo;

    public void setTodoList(TodoList todoList) {
        labelHeader.setText(todoList.getTitle());
        listModel.setAll(todoList.getChildren());
        buttonNewTodo.setDisable(false);
    }

    @FXML
    public void initialize() {
        listModel = FXCollections.observableArrayList();
        listViewTodo.itemsProperty().set(listModel);
        buttonNewTodo.setDisable(true);
        buttonEditTodo.disableProperty().bind(listViewTodo.getSelectionModel().selectedItemProperty().isNull());
        buttonDeleteTodo.disableProperty().bind(listViewTodo.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    protected void handleNewList() {
        open("listForm", buttonNewList.getScene().getWindow());
    }

    @FXML
    protected void handleOpenList() {
        open("browser", buttonOpenList.getScene().getWindow());
    }

    @FXML
    protected void handleNewTodo() {
        open("todoForm", buttonNewTodo.getScene().getWindow());
    }
    
    @FXML
    protected void handleEditTodo() {
        // TODO
        // open("todoEditForm", buttonEditTodo.getScene().getWindow());
    }

    @FXML
    protected void handleDeleteTodo() {
        Window owner = buttonDeleteTodo.getScene().getWindow();
        Todo todo = listViewTodo.getSelectionModel().getSelectedItem();
        PopupHelper.showAlert(AlertType.CONFIRMATION, owner, "Delete", "Delete Todo \"" + todo.getTitle() + "\"?")
            .filter(b -> b == ButtonType.OK)
            .ifPresent(b -> {
                String todoList = labelHeader.getText(); // for now
                todoManager.removeTodo(todoList, todo);
                todoManager.saveLists();
                listModel.remove(todo);
            });
    }
}