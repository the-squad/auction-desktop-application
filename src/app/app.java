package app;

import app.pages.LandingPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application {

    private LandingPage landingPage;
    public static BorderPane app;

    @Override
    public void start(Stage primaryStage) {
        //App container
        app = new BorderPane();
        app.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> app.requestFocus());

        //Landing page
        landingPage = LandingPage.getInstance();
        app.setCenter(landingPage.getLandingPage());

        //App Scene
        Scene scene = new Scene(app, 1200, 600);
        primaryStage.setTitle("Auction System!");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(600);

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
        cssFiles.add("scrollbar");
        cssFiles.add("categoriesPanel");
        cssFiles.add("loading");

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
        cssFiles.add("accountSettings");
        cssFiles.add("pagesBackground");
        cssFiles.add("searchPage");
        cssFiles.add("profilePage");

        return cssFiles;
    }

    public static BorderPane getMainContainer() {
        return app;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
