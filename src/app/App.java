/*
 * The MIT License
 *
 * Copyright 2017 Contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package app;

import app.views.LandingPage;
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
        importComponentsCSS().forEach((cssFile) -> scene.getStylesheets().add("/styles/components/" + cssFile + ".css"));
        importPartialsCSS().forEach((cssFile) -> scene.getStylesheets().add("/styles/partials/" + cssFile + ".css"));
        importPagesCSS().forEach((cssFile) -> scene.getStylesheets().add("/styles/pages/" + cssFile + ".css"));

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
        cssFiles.add("input");
        cssFiles.add("photos");
        cssFiles.add("scrollbar");
        cssFiles.add("categoriesPanel");
        cssFiles.add("selectors");
        cssFiles.add("sellerDetails");

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
        cssFiles.add("auctionView");

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
