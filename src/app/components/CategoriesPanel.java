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

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CategoriesPanel {

    final private String categories[];

    private GridPane tabsContainer;
    private Label headline;
    private Label tabs[];

    public CategoriesPanel(String ...categories) {
        this.categories = categories.clone();
        this.render();
    }

    public void render() {
        //Headline label
        headline = new Label("Categories");
        headline.getStyleClass().add("headline--category");

        //CategoriesPanel Containers
        tabsContainer = new GridPane();
        tabsContainer.setHgap(5);
        tabsContainer.getStyleClass().add("tabs-container");

        //Generating tabs
        tabs = new Label[categories.length];

        int counter = 0;
        for (String category : categories) {
            tabs[counter] = new Label(category);
            tabs[counter].getStyleClass().add("tab--category");

            Label currentTab = tabs[counter];
            tabs[counter].setOnMouseClicked(e -> switchTab(currentTab));

            if (counter == 0) tabs[counter].getStyleClass().add("tab--category-active");
            GridPane.setConstraints(tabs[counter], counter + 1, 0);
            tabsContainer.getChildren().add(tabs[counter]);
            counter++;
        }

        tabsContainer.setAlignment(Pos.CENTER);
        GridPane.setConstraints(headline, 0, 0);
        tabsContainer.getChildren().add(headline);
    }

    public GridPane getCategoriesTabs() {
        return tabsContainer;
    }

    private void switchTab(Label label) {
        //Highlighting the active tab
        Label activeLabel = (Label) tabsContainer.lookup(".tab--category-active");
        activeLabel.getStyleClass().remove("tab--category-active");
        label.getStyleClass().add("tab--category-active");
    }
}
