import Model.Mediator;
import Model.Original;
import Model.Originals;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    /**
     * The start method of the main stage of the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        if (Files.notExists(Paths.get("Recordings"))) {
            Files.createDirectory(Paths.get("Recordings"));
        }
        if (Files.notExists(Paths.get("Names"))) {
            Files.createDirectory(Paths.get("Names"));
        }

	    Originals.getInstance().populateFolders();
	    Mediator.getInstance().setPage("Root");
        try {
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("/Header.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
