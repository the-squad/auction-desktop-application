package app;

import app.components.InputField;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 *
 * @author Muhammad
 */
public class main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, IOException { 
        InputField firstNameField = new InputField("First name", "Text", "");
        InputField lastNameField = new InputField("Last name", "Text", "");
        
        

        //App container
        BorderPane app = new BorderPane();
        app.setCenter(firstNameField.render());
        app.setLeft(lastNameField.render());
        
        System.out.println(firstNameField.getValue());
        
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
