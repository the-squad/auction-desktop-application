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

package app.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import static app.Partials.SHOW_ERROR_MESSAGE;

class Input {

    private final String inputName;
    private Boolean hideErrorMessage = SHOW_ERROR_MESSAGE;

    GridPane inputFieldContainer;
    private Label inputLabel;
    private Label errorMessage;


    Input(String inputName) {
        this.inputName = inputName;
        this.render();
    }

    Input(String inputName, Boolean hideErrorMessage) {
        this.inputName = inputName;
        this.hideErrorMessage = hideErrorMessage;
        this.render();
    }

    private void render() {
        //Input label
        inputLabel = new Label(inputName);
        inputLabel.getStyleClass().add("label");

        //Error message
        errorMessage = new Label("Please enter a valid mail");
        errorMessage.getStyleClass().add("error-message");
        errorMessage.setWrapText(true);
        errorMessage.setVisible(false);

        //Adding to the grid pane
        inputFieldContainer = new GridPane();
        inputFieldContainer.getStyleClass().add("input-field");

        GridPane.setConstraints(inputLabel, 0, 0);
        GridPane.setMargin(inputLabel, new Insets(0, 0, 3, 0));

        GridPane.setConstraints(errorMessage, 0, 2);
        inputFieldContainer.getChildren().add(inputLabel);
        if (!hideErrorMessage) inputFieldContainer.getChildren().add(errorMessage);
    }

    /*
    Change's input field to danger and shows an error message
    */
    public void markAsDanger(String error) {
        inputFieldContainer.getStyleClass().add("input-field--danger");
        errorMessage.setText(error);
        errorMessage.setVisible(true);
    }

    /*
     Removes the danger class and hide the error message
     */
    protected void markAsNormal() {
        inputFieldContainer.getStyleClass().remove("input-field--danger");
        errorMessage.setVisible(false);
    }
}
