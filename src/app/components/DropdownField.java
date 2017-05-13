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

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

import static app.Partials.*;

public class DropdownField extends Input {

    private int inputSize = NORMAL;

    private ArrayList<String> items;
    private ComboBox input;
    private int index;

    public DropdownField(String inputName) {
        super(inputName, NORMAL);
        this.render();
    }

    public DropdownField(String inputName, ArrayList<String> items) {
        super(inputName, NORMAL);
        this.items = items;

        this.render();
    }

    public DropdownField(String inputName, int inputSize, Boolean hideErrorMessage, ArrayList<String> items) {
        super(inputName, hideErrorMessage);
        this.items = items;
        this.inputSize = inputSize;
        this.render();
    }

    public void render() {
        //Dropdown field
        input = new ComboBox();
        input.setVisibleRowCount(6);
        input.setMinWidth(inputSize);
        input.setMaxWidth(inputSize);

        //Placeholder styling
        input.setPromptText("Select");
        input.setButtonCell(new ListCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    // styled like -fx-prompt-text-fill:
                    setStyle("-fx-text-fill: -fx-medium-gray-color");
                } else {
                    setStyle("-fx-text-fill: #18181C");
                    setText(item.toString());
                }
            }
        });

        //Loading dropdown menu items
        if (items != null)
            this.addItems(items);

        //Checking if the user selecting an item or not
        this.input.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (!newValue)
                        onBlur();
                });

        input.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> markAsNormal());

        //Dropdown field container
        inputFieldContainer.getStyleClass().add("dropdown-field");

        GridPane.setConstraints(input, 0, 1);

        inputFieldContainer.getChildren().addAll(input);
    }

    private void onBlur() {
        String selectedItem = getValue();
        if (selectedItem == null) {
            markAsDanger("Please select an item");
        } else {
            markAsNormal();
        }
    }

    public String getValue() {
        return (String) input.getValue();
    }

    public void setValue(String value) {
        input.setValue(value);
    }

    public void clear() {
        input.setValue("");
    }

    public void disable(Boolean disable) {
        input.setDisable(disable);
    }

    public void setDefaultSelect() {
        input.getSelectionModel().selectFirst();
    }

    public void addItems(ArrayList<String> items) {
        this.clearItems();
        for (String item : items) {
            input.getItems().add(item);
        }
    }

    public int getSelectedItemIndex() {
        return input.getSelectionModel().getSelectedIndex();
    }

    public void clearItems() {
        input.getItems().clear();
    }

    public GridPane getDropdownField() {
        return inputFieldContainer;
    }
}
