/*
 * The MIT License
 *
 * Copyright 2017 Muhammad.
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

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import static app.Partials.*;

public class InputField extends GridPane {

    private final String inputName;
    private final int inputType;
    private final String placeholder;
    
    private GridPane inputFieldContainer;
    private Label inputLabel;
    private TextField input;
    private Label errorMessage;

    public InputField(String inputName, int inputType, String placeholder) {
        this.inputName = inputName;
        this.inputType = inputType;
        this.placeholder = placeholder;

        this.render();
    }

    private void render() {
        //Input label
        inputLabel = new Label(inputName);
        inputLabel.getStyleClass().add("label");

        //Input field
        input = (inputType == TEXT) ? new TextField() : new PasswordField();
        input.setPromptText(placeholder);
        input.getStyleClass().add("input");

        input.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (!newValue)
                        onBlur();
                });

        //Error message
        errorMessage = new Label("Please enter a valid mail");
        errorMessage.getStyleClass().add("error-message");
        errorMessage.setWrapText(true);
        errorMessage.setVisible(false);

        //Adding to the grid pane
        inputFieldContainer = new GridPane();
        inputFieldContainer.getStyleClass().add("input-field");

        inputFieldContainer.setConstraints(inputLabel, 0, 0);
        inputFieldContainer.setMargin(inputLabel, new Insets(0, 0, 3, 0));

        inputFieldContainer.setConstraints(input, 0, 1);
        inputFieldContainer.setMargin(input, new Insets(0, 0, 3, 0));

        inputFieldContainer.setConstraints(errorMessage, 0, 2);
        inputFieldContainer.getChildren().addAll(inputLabel, input, errorMessage);
    }

    public GridPane getInputField() {
        return inputFieldContainer;
    }

    /*
     Returns the input's value
     */
    public String getValue() {
        return input.getText();
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
    private void markAsNormal() {
        inputFieldContainer.getStyleClass().remove("input-field--danger");
        errorMessage.setVisible(false);
    }

    /*
     When the user lose focus on an input it will validate the value
     */
    private void onBlur() {
        //TODO

        String message; //Call the validation message validate(getValue(), text)

        /*
         If there is an error message the input field will be highlighted
         else it will set it to normal
         */
        /*
        if (message != "true") {
            markAsDanger(message);
        } else {
            markAsNormal();
        }*/
    }
}
