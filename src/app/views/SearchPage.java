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

package app.views;

import app.Navigator;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import static app.Partials.*;

public class SearchPage {

    private static SearchPage instance;

    private GridPane searchPageContainer;

    private TextField searchField;
    private Button searchButton;
    private TextFlow helpText;
    private Text firstPart;
    private Text enterKeyword;
    private Text secondPart;
    private Text escKeyword;
    private Text lastPart;

    private SearchPage() {
        this.render();
    }

    private void render() {
        //Search field
        searchField = new TextField();
        searchField.setPromptText("What's in your mind");
        searchField.getStyleClass().add("search-field");

        searchField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue) {
                helpText.setVisible(true);
            } else {
                helpText.setVisible(false);
            }
        });

        //Search button
        searchButton = new Button("Search");
        searchButton.getStyleClass().add("btn-primary");

        searchButton.setOnAction(e -> Navigator.viewPage(SEARCH_RESULTS_PAGE, "Search Results"));

        //Help text
        firstPart = new Text("Press");
        secondPart = new Text(" to make search\nPress");
        lastPart = new Text(" to cancel");

        enterKeyword = new Text(" Enter");
        enterKeyword.setStyle("-fx-font-weight: 700");

        escKeyword = new Text(" ESC");
        escKeyword.setStyle("-fx-font-weight: 700");

        firstPart.getStyleClass().add("text-color");
        secondPart.getStyleClass().add("text-color");
        lastPart.getStyleClass().add("text-color");
        enterKeyword.getStyleClass().add("text-color");
        escKeyword.getStyleClass().add("text-color");

        helpText = new TextFlow(firstPart, enterKeyword, secondPart, escKeyword, lastPart);
        helpText.getStyleClass().add("help-text");
        helpText.setVisible(false);

        //Search page container
        searchPageContainer = new GridPane();
        searchPageContainer.setPadding(new Insets(30, 30, 0, 100));

        ColumnConstraints leftPart = new ColumnConstraints();
        leftPart.setPercentWidth(95);

        ColumnConstraints rightPart = new ColumnConstraints();
        rightPart.setPercentWidth(5);
        rightPart.setHalignment(HPos.RIGHT);

        searchPageContainer.getColumnConstraints().add(leftPart);

        GridPane.setConstraints(searchField, 0, 0);
        GridPane.setMargin(searchField, new Insets(70, 0, 90, 0));

        GridPane.setConstraints(searchButton, 0, 1);
        GridPane.setMargin(searchButton, new Insets(0, 0, 90, 0));
        GridPane.setConstraints(helpText, 0, 2);

        searchPageContainer.getChildren().addAll(searchField, searchButton, helpText);

        //Keyboard shortcuts
        searchPageContainer.setOnKeyPressed(
                (final KeyEvent keyEvent) -> {
                    if (null != keyEvent.getCode()) {
                        switch (keyEvent.getCode()) {
                            case ENTER:
                                searchButton.fire();
                                searchField.setText("");
                                //Stop letting it do anything else
                                keyEvent.consume();
                                break;
                            case ESCAPE:
                                Navigator.hidePage();
                                searchField.setText("");
                                keyEvent.consume();
                                break;
                            default:
                                break;
                        }
                    }
                }
        );
    }

    public GridPane getSearchPage() {
        return searchPageContainer;
    }

    public static SearchPage getInstance() {
        if (instance == null) {
            instance = new SearchPage();
        }
        return instance;
    }
}
