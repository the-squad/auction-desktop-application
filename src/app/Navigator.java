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

package app;

import app.components.Header;
import app.controllers.AccountSettings;
import app.pages.*;
import app.tabs.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import static app.Partials.ACCOUNT_SETTINGS;

public class Navigator {

    private static Region currentPage;
    private static Region requestedPage;
    private static Region previousTab;
    private static Region currentTab;
    private static Region requestedTab;

    private static BorderPane appContainer;
    private static HomePage homePage;
    private static Header header;

    private static Region selectTabOrPage(int tabOrPage) {
        switch (tabOrPage) {
            case 0:
                return LandingPage.getInstance().getLandingPage();
            case 1:
                return HomePage.getInstance().getHomePage();
            case 2:
                return ProfilePage.getInstance().getProfilePage();
            case 3:
                return SearchPage.getInstance().getSearchPage();
            case 4:
                return NotificationsPage.getInstance().getNotificationsPage();
            case 5:
                return AuctionView.getInstance().getAuctionView();
            case 6:
                return ExploreTab.getInstance().getExploreTab();
            case 7:
                return AuctionsTab.getInstance().getAuctionsTab();
            case 8:
                return FeedTab.getInstance().getFeedTab();
            case 9:
                return InventoryTab.getInstance().getInventoryTab();
            case 10:
                return AccountSettings.getInstance().getAccountSettingsPage();
        }
        return null;
    }

    public static void setCurrentTab(int currentTabId) {
        currentTab = selectTabOrPage(currentTabId);
    }

    public static void switchPage(int currentPageId, int requestedPageId) {
        appContainer = App.getMainContainer();
        currentPage = selectTabOrPage(currentPageId);
        requestedPage = selectTabOrPage(requestedPageId);

        Animations.fade(appContainer, currentPage, requestedPage);
    }

    public static void switchTab(int requestTabId) {
        requestedTab = selectTabOrPage(requestTabId);
        if (requestedTab == currentTab) return;

        homePage = HomePage.getInstance();
        Animations.slideDownThenSlideUp(homePage.getHomePage(), currentTab, requestedTab);
        setCurrentTab(requestTabId);
    }

    public static void viewPage(int requestedPageId, String pageTitle) {
        requestedPage = selectTabOrPage(requestedPageId);
        if (requestedPage == currentTab) return;
        homePage = HomePage.getInstance();

        Animations.fadeOutThenSlideUp(homePage.getHomePage(), currentTab, requestedPage);
        previousTab = currentTab;
        setCurrentTab(requestedPageId);

        header = Header.getInstance();
        header.setPageTitle(pageTitle);
        header.showPageTitle();
    }

    public static void hidePage() {
        homePage = HomePage.getInstance();

        Animations.slideDownThenFadeIn(homePage.getHomePage(), currentTab, previousTab);
        currentTab = previousTab;
        previousTab = null;

        header = Header.getInstance();
        header.hidePageTitle();
    }
}
