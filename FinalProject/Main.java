package FinalProject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @JamesFuller
 */

/**
 * This class launches the inventory management application.
 */
public class Main extends Application {

    /**
     * This is the start method.
     * This method sets the stage and opens the scene for the main page loading and displaying the fxml file in a window.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.FXML"));
        primaryStage.setTitle("Inventory Management Program");
        primaryStage.setScene(new Scene(root, 1009, 500));
        primaryStage.show();
    }

    /**
     * This is the main method.
     * This method launches the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
