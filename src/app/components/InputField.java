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

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Muhammad
 */
public class InputField {
    private final String inputName;
    private final String inputType;
    private final String placeholder;
    
    private GridPane inputFieldContainer;
    private Label inputLabel;
    private TextField input;
    private Label errorMessage;
    
    public InputField(String inputName, String inputType, String placeholder) {
        this.inputName = inputName;
        this.inputType = inputType;
        this.placeholder = placeholder;
    }
    
    public GridPane render() {
        inputLabel = new Label(inputName);
        inputLabel.getStyleClass().add("label");
        
        input = new TextField(placeholder);
        input.getStyleClass().add("input");
        
        errorMessage = new Label("Error Message");
        errorMessage.getStyleClass().add("error-message");
        
        inputFieldContainer = new GridPane();
        inputFieldContainer.getStyleClass().add("input-field");
        inputFieldContainer.setConstraints(inputLabel, 0, 0);
        inputFieldContainer.setConstraints(input, 0, 1);
        inputFieldContainer.setConstraints(errorMessage, 0, 2);
        inputFieldContainer.getChildren().addAll(inputLabel, input, errorMessage);
        
        return inputFieldContainer;
    }
    
    public String getValue() {
        //TODO
        return " ";  
    }
    
    private void markAsDanger() {
        //TODO
    }
    
    private void markAsNormal() {
        
    }
    
    private void onBlur() {
        
    }
}
