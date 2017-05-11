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

import app.tabs.ExploreTab;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import models.Category;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriesPanel {

    private final ArrayList<Category> categories;

    private GridPane tabsContainer;
    private Label headline;
    private ArrayList<Label> tabs;
    private HashMap<Label, Category> tabsTable;

    private MouseEvent switchEvent;

    public CategoriesPanel(ArrayList<Category> categories) {
        this.categories = categories;
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
        tabsTable = new HashMap<>(categories.size());
        tabs = new ArrayList<>(categories.size());

        int counter = 0;
        for (Category category : categories) {
            Label tab = new Label(category.getName());
            tab.getStyleClass().add("tab--category");
            tabs.add(tab);

            tabsTable.put(tab, category);

            tab.setOnMouseClicked(e -> {
                switchTab(tab);
            });

            if (counter == 0) {
                tab.getStyleClass().add("tab--category-active");
            }
            GridPane.setConstraints(tab, counter + 1, 0);
            tabsContainer.getChildren().add(tab);
            counter++;
        }

        tabsContainer.setAlignment(Pos.CENTER);
        GridPane.setConstraints(headline, 0, 0);
        tabsContainer.getChildren().add(headline);
    }

    public GridPane getCategoriesTabs() {
        return tabsContainer;
    }

    private void switchTab(Label tab) {
        //Highlighting the active tab
        Label activeLabel = (Label) tabsContainer.lookup(".tab--category-active");
        activeLabel.getStyleClass().remove("tab--category-active");
        tab.getStyleClass().add("tab--category-active");

        ExploreTab.getInstance().loadCards(tabsTable.get(tab));
    }
}
