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

package app;

import models.Buyer;
import models.Seller;
import models.User;

public class Partials {

    public static int TEXT = 0;
    public static int EMAIL = 1;
    public static int PASSWORD = 2;
    public static int DATE = 3;
    public static int TIME = 4;
    public static int PHONE_NUMBER = 5;
    public static int INTEGER_NUMBER = 6;
    public static int DECIMAL_NUMBER = 7;
    public static int FREE_TEXT = 8;

    public static int BUYER = 3;
    public static int SELLER = 2;
    public static int ADMIN = 1;

    public static int userType;

    //Pages and tabs
    public static int LANDING_PAGE = 0;
    public static int HOME_PAGE = 1;
    public static int PROFILE_PAGE = 2;
    public static int NOTIFICATIONS_PAGE = 3;
    public static int SEARCH_PAGE = 4;
    public static int AUCTION_VIEW = 5;
    public static int EXPLORE_TAB = 6;
    public static int AUCTIONS_TAB = 7;
    public static int FEED_TAB = 8;
    public static int INVENTORY_TAB = 9;
    public static int ACCOUNT_SETTINGS = 10;
    public static int SEARCH_RESULTS_PAGE = 11;
    public static int ADDITION_PAGE = 12;
    public static int ITEM_DETAILS = 13;
    public static int AUCTION_DETAILS = 14;

    //Input field sizes
    public static int SHORT = 175;
    public static int NORMAL = 300;
    public static int LONG = 400;

    //True or false
    public static Boolean HIDE_ERROR_MESSAGE = Boolean.TRUE;
    public static Boolean SHOW_ERROR_MESSAGE = Boolean.FALSE;

    public static double SCROLLING_SPEED = 3;

    //Border radius
    public static int FIT_CONTAINER = 0;
    public static int FIT_DATA = 1;

    //Global users actors
    public static User currentUser;
    public static Buyer currentBuyer;
    public static Seller currentSeller;

    //Card props
    public static int RIGHT_LEFT = 50;
    public static int TOP_DOWN = 35;
    public static int CARD_WIDTH = 550;

    //View mode
    public static int VIEW_MODE = 0;
    public static int EDIT_MODE = 1;
}
