package todoit;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import todoit.control.StageControl;

public class App extends Application {
    public static final String APP_DIRECTORY = (System.getenv("APPDATA") + File.separator + "todoit" + File.separator);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage ps) throws Exception {
        FXMLLoader loader = new FXMLLoader(Object.class.getResource("/views/home.fxml"));
        
        ps.setScene(new Scene(loader.load()));
        ps.getScene().getStylesheets().setAll(Object.class.getResource("/styles/style.css").toExternalForm());
        ps.setWidth(640);
        ps.setHeight(384);

        StageControl control = loader.<StageControl>getController();
        control.setManager(new TodoManager(APP_DIRECTORY));

        ps.show();
    }
}