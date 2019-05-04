package todoit.control;

import todoit.TodoManager;
import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public abstract class StageControl {
    protected TodoManager todoManager;
    protected StageControl parentControl;

    @FXML protected Button buttonCancel;
    @FXML protected Button buttonConfirm;

    public void setParent(StageControl parent) {
        parentControl = parent;
    }

    public void setManager(TodoManager manager) {
        todoManager = manager;
    }

    @FXML
    protected void handleCancel() {
        if(buttonCancel != null)
            close(buttonCancel.getScene().getWindow());
    }

    @FXML
    protected void handleConfirm() {
        if(buttonConfirm != null)
            close(buttonConfirm.getScene().getWindow());
    }

    protected void open(String view, Window owner) {
        open(view, owner, Modality.WINDOW_MODAL);
    }

    protected void open(String view, Window owner, Modality modality) {
        try {
            if(!view.startsWith("/")) view = "/views/" + view;
            if(!view.endsWith(".fxml")) view += ".fxml";
            
            FXMLLoader loader = new FXMLLoader(Object.class.getResource(view));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            StageControl control = loader.<StageControl>getController();
            control.setManager(todoManager);
            control.setParent(this);

            stage.initOwner(owner);
            stage.initModality(modality);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e) {
            // System.out.println("could not open view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void close(Window owner) {
        owner.fireEvent(new Event(WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}