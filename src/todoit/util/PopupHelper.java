package todoit.util;

import java.util.Optional;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class PopupHelper {
    private PopupHelper() {}

    public static Optional<ButtonType> showAlert(AlertType type, Window owner, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        return alert.showAndWait();
    }
}