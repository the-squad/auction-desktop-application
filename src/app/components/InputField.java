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

import app.Validation;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import static app.Partials.*;

public class InputField extends Input {

    private final int inputType;
    private int inputSize = NORMAL;

    private TextField input;

    public InputField(String inputName, int inputType) {
        super(inputName);
        this.inputType = inputType;
        this.render();
    }

    public InputField(String inputName, int inputType, int inputSize) {
        super(inputName);
        this.inputType = inputType;
        this.inputSize = inputSize;
        this.render();
    }

    public InputField(String inputName, int inputType, int inputSize, Boolean hideErrorMessage) {
        super(inputName, hideErrorMessage);
        this.inputType = inputType;
        this.inputSize = inputSize;
        this.render();
    }

    private void render() {
        //Input field
        input = (inputType == PASSWORD) ? new PasswordField() : new TextField();
        input.getStyleClass().add("input");
        input.setMinWidth(inputSize);
        input.setMaxWidth(inputSize);

        input.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (!newValue)
                        onBlur();
                });

        //Adding to the grid pane
        GridPane.setConstraints(input, 0, 1);
        GridPane.setMargin(input, new Insets(0, 0, 3, 0));
        inputFieldContainer.getChildren().add(input);
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

    public void setValue(String value) {
        input.setText(value);
    }

    /*
     When the user lose focus on an input it will validate the value
     */
    private void onBlur() {
        Boolean validationResult = true;
        String errorMessage = "";
        if (this.getValue().length() == 0) {
            validationResult = false;
            errorMessage = "Field is required";
        }

        if (validationResult) {
            if (inputType == TEXT) {
                validationResult = Validation.validateText(this.getValue());
                errorMessage = "Name shouldn't contain numbers of special characters";
            } else if (inputType == PASSWORD) {
                validationResult = Validation.validatePassword(this.getValue());
                errorMessage = "Password should contain small and capital letters and numbers";
            } else if (inputType == EMAIL) {
                validationResult = Validation.validateEmail(this.getValue());
                errorMessage = "Email isn't valid";
            } else if (inputType == DATE) {
                validationResult = Validation.validateDate(this.getValue());
                errorMessage = "Date format should be ..."; //TODO
            } else if (inputType == TIME) {
                validationResult = Validation.validateTime(this.getValue());
                errorMessage = "Time format should be ..."; //TODO
            } else {
                validationResult = Validation.validateNumber(this.getValue());
                errorMessage = "Letters and special characters aren't allowed";
            }
        }

        if (!validationResult) {
            markAsDanger(errorMessage);
        } else {
            markAsNormal();
            errorMessage = "";
        }
    }

    public void clear() {
        input.setText("");
        markAsNormal();
    }

    public void focus() {
        input.requestFocus();
    }
}
