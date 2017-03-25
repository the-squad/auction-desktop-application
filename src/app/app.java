package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
        importComponentsCSS().stream().forEach((cssFile) -> scene.getStylesheets().add("/styles/components/" + cssFile + ".css"));
        importPartialsCSS().stream().forEach((cssFile) -> scene.getStylesheets().add("/styles/partials/" + cssFile + ".css"));
        importPagesCSS().stream().forEach((cssFile) -> scene.getStylesheets().add("/styles/pages/" + cssFile + ".css"));
        
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/assets/app-logo.png"));
        primaryStage.show();
    }
    
    private ArrayList<String> importComponentsCSS() {
        ArrayList<String> cssFiles = new ArrayList<>();
        
        //Components CSS
        cssFiles.add("buttons");
        cssFiles.add("cards");
        cssFiles.add("filter");
        cssFiles.add("header");
        cssFiles.add("inputField");
        cssFiles.add("photos");
        cssFiles.add("radioButtons");
        cssFiles.add("searchBar");
        cssFiles.add("scrollbar");
        
        return cssFiles;
    }

    private ArrayList<String> importPartialsCSS() {
        ArrayList<String> cssFiles = new ArrayList<>();

        //Partials CSS
        cssFiles.add("fonts");
        cssFiles.add("colors");

        return cssFiles;
    }

    private ArrayList<String> importPagesCSS() {
        ArrayList<String> cssFiles = new ArrayList<>();

        //Partials CSS
        cssFiles.add("landingPage");
        cssFiles.add("homePage");

        return cssFiles;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
