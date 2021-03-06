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

package app.views;

import app.Navigator;
import app.components.Header;
import app.tabs.ExploreTab;
import app.tabs.InventoryTab;
import javafx.scene.layout.BorderPane;

import static app.Partials.*;

public class HomePage extends BorderPane {

    private static HomePage instance;

    private BorderPane homePageContainer;
    private Header header;

    private ExploreTab explore;
    private InventoryTab inventory;

    private HomePage() {
        this.render();
    }

    private void render() {
        //Importing the header component
        header = Header.getInstance();

        //Home page container
        homePageContainer = new BorderPane();
        homePageContainer.setTop(header.getHeader());
        homePageContainer.getStyleClass().add("home-page");

        if (userType == BUYER) {
            //Creating the explore tab
            explore = ExploreTab.getInstance();
            homePageContainer.setCenter(explore.getExploreTab());
            Navigator.setCurrentTab(EXPLORE_TAB);
        } else if (userType == SELLER) {
            //Creating the inventory page
            inventory = InventoryTab.getInstance();
            homePageContainer.setCenter(inventory.getInventoryTab());
            Navigator.setCurrentTab(INVENTORY_TAB);
        }
    }

    public void destory() {
        instance = null;
    }

    public BorderPane getHomePage() {
        return homePageContainer;
    }

    public static HomePage getInstance() {
        if (instance == null) {
            instance = new HomePage();
        }
        return instance;
    }
}
