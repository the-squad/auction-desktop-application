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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Muhammad
 */
public class InputField {
    
    private GridPane inputFieldContainer;
    private Label inputLabel;
    private TextField input;
    private Label errorMessage;

    
    public GridPane render(String inputName, String inputType, String placeholder) {
        //Input label
        this.inputLabel = new Label(inputName);
        this.inputLabel.getStyleClass().add("label");

        //Input field
        this.input = new TextField();
        this.input.setPromptText(placeholder);
        this.input.getStyleClass().add("input");
        
        this.input.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (!newValue)
                    onBlur();
                });

        //Error message
        this.errorMessage = new Label("Please enter a valid mail");
        this.errorMessage.getStyleClass().add("error-message");
        this.errorMessage.setWrapText(true);
        this.errorMessage.setVisible(false);

        //Adding to the grid pane
        this.inputFieldContainer = new GridPane();
        this.inputFieldContainer.getStyleClass().add("input-field");
        
        this.inputFieldContainer.setConstraints(inputLabel, 0, 0);
        this.inputFieldContainer.setMargin(inputLabel, new Insets(0, 0, 3, 0));
        
        this.inputFieldContainer.setConstraints(input, 0, 1);
        this.inputFieldContainer.setMargin(input, new Insets(0, 0, 3, 0));
        
        this.inputFieldContainer.setConstraints(errorMessage, 0, 2);
        this.inputFieldContainer.getChildren().addAll(inputLabel, input, errorMessage);
        
        return this.inputFieldContainer;
    }

    /*
     Returns the input's value
     */
    public String getValue() {
        return this.input.getText();
    }

    /*
     Change's input field to danger and shows an error message
     */
    private void markAsDanger(String error) {
        this.inputFieldContainer.getStyleClass().add("input-field--danger");
        this.errorMessage.setText(error);
        this.errorMessage.setVisible(true);
    }

    /*
     Removes the danger class and hide the error message
     */
    private void markAsNormal() {
        this.inputFieldContainer.getStyleClass().remove("input-field--danger");
        this.errorMessage.setVisible(false);
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
