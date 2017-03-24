package app;

import app.components.DropdownField;
import app.components.InputField;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 *
 * @author Muhammad
 */
public class app extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //App container
        BorderPane app = new BorderPane();
        app.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> app.requestFocus());
        
        //App Scene
        Scene scene = new Scene(app, 1100, 600);
        primaryStage.setTitle("Auction System!");
        
        //Importing CSS Files
        scene.getStylesheets().add("/styles/colors.css");
        importCSS().stream().forEach((cssFile) -> scene.getStylesheets().add("/styles/components/" + cssFile + ".css"));
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private ArrayList<String> importCSS() {
        ArrayList<String> cssFiles = new ArrayList<>();
        
        //Components CSS
        cssFiles.add("buttons");
        cssFiles.add("cards");
        cssFiles.add("filter");
        cssFiles.add("fonts");
        cssFiles.add("header");
        cssFiles.add("inputField");
        cssFiles.add("photos");
        cssFiles.add("radioButtons");
        cssFiles.add("searchBar");
        cssFiles.add("scrollbar");
        
        return cssFiles;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
