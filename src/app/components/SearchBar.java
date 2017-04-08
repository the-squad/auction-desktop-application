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

import javafx.scene.control.TextField;

public class SearchBar extends TextField {

    private static SearchBar instance;

    private TextField searchbar;

    private SearchBar() {
        this.render();
    }

    private void render() {
        //Searchbar
        searchbar = new TextField();
        searchbar.getStyleClass().add("searchbar");
        searchbar.setPromptText("Search");

        //Making a search query when pressing enter
        searchbar.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    onSearch(); //Making the search
                    clear(); //Clearing the field
                    break;
            }
        });

        //When typing a search
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            onTyping(getValue());
        });
    }

    public TextField getSearchbar() {
        return searchbar;
    }

    public void clear() {
        searchbar.setText("");
    }

    private void onSearch() {
        //TODO
    }

    private void onTyping(String currentSearch) {
        //TODO
    }

    private String getValue() {
        return searchbar.getText();
    }

    public static SearchBar getInstance() {
        if (instance == null) {
            instance = new SearchBar();
        }
        return instance;
    }
}
