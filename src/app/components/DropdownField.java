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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;


/**
 *
 * @author Muhammad
 */
public class DropdownField extends GridPane{

    private final String inputName;
    private String []items;

    private GridPane dropdownFieldContainer;
    private Label dropdownLabel;
    private ComboBox input;

    public DropdownField(String inputName, String ...items) {
        this.inputName = inputName;
        this.items = items.clone();
    }

    public GridPane render() {
        //Dropdown label
        dropdownLabel = new Label(inputName);
        dropdownLabel.getStyleClass().add("label");

        //Dropdown field
        input = new ComboBox();
        input.setVisibleRowCount(6);

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
        for (String item : items) {
            input.getItems().add(item);
        }

        //Checking if the user selecting an item or not
        this.input.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (!newValue)
                        onBlur();
                });

        input.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> markAsNormal());

        //Dropdown field container
        dropdownFieldContainer = new GridPane();
        dropdownFieldContainer.getStyleClass().add("dropdown-field");

        dropdownFieldContainer.setConstraints(dropdownLabel, 0, 0);
        dropdownFieldContainer.setMargin(dropdownLabel, new Insets(0, 0, 3, 0));

        dropdownFieldContainer.setConstraints(input, 0, 1);

        dropdownFieldContainer.getChildren().addAll(dropdownLabel, input);

        return dropdownFieldContainer;
    }


    private void onBlur() {
        String selectedItem = getValue();
        if (selectedItem == null) {
            markAsDanger();
        } else {
            markAsNormal();
        }
    }

    private void markAsDanger() {
        dropdownFieldContainer.getStyleClass().add("dropdown-field--danger");
    }

    private void markAsNormal() {
        dropdownFieldContainer.getStyleClass().remove("dropdown-field--danger");
    }

    private String getValue() {
        return (String) input.getValue();
    }

    public void clearItems() {
        input.getItems().clear();
    }
}
