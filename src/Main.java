import Model.Mediator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * The start method of the main stage of the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
	    Mediator.getInstance().setPage("Root");
        try {
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("GUI/Header.fxml"));
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
