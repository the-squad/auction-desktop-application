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
import javafx.scene.layout.GridPane;

/**
 *
 * @author Muhammad
 */
public class Tabs {

    private GridPane tabsContainer;
    private Label tabs[];

    public GridPane render(String ...categories) {
        //Tabs Containers
        tabsContainer = new GridPane();

        //Generating tabs
        tabs = new Label[categories.length];

        int counter = 0;
        for (String category : categories) {
            tabs[counter] = new Label(category);
            tabs[counter].getStyleClass().add("tab--category");

            Label currentTab = tabs[counter];
            tabs[counter].setOnMouseClicked(e -> switchTab(currentTab));

            if (counter == 0) tabs[counter].getStyleClass().add("tab--category-active");
            tabsContainer.setConstraints(tabs[counter], counter, 0);
            tabsContainer.getChildren().add(tabs[counter]);
            counter++;
        }

        return tabsContainer;
    }

    private void switchTab(Label label) {
        //Highlighting the active tab
        Label activeLabel = (Label) tabsContainer.lookup(".tab--category-active");
        activeLabel.getStyleClass().remove("tab--category-active");
        label.getStyleClass().add("tab--category-active");
    }
}
