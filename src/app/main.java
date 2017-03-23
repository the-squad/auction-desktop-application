package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import app.components.InputField;
import java.util.ArrayList;
import javafx.scene.control.Label;

/**
 *
 * @author Muhammad
 */
public class main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, IOException {        
        //App container
        BorderPane app = new BorderPane();
        
        //App Scene
        Scene scene = new Scene(app, 1100, 600);
        primaryStage.setTitle("Auction System!");
        
        //Imporing CSS Files
        importCSS().stream().forEach((cssFile) -> {
            scene.getStylesheets().add("/styles/components/" + cssFile + ".css");
        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private ArrayList<String> importCSS() {
        ArrayList<String> cssFiles = new ArrayList<String>();
        
        //Components CSS
        cssFiles.add("buttons");
        cssFiles.add("cards");
        cssFiles.add("dropdownMenu");
        cssFiles.add("filter");
        cssFiles.add("fonts");
        cssFiles.add("header");
        cssFiles.add("inputField");
        cssFiles.add("photos");
        cssFiles.add("radioButtons");
        cssFiles.add("searchBar");
        
        return cssFiles;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
