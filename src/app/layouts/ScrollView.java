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

package app.layouts;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import static app.Partials.SCROLLING_SPEED;

public class ScrollView {

    private final Region content;
    private ScrollPane scrollPane;

    public ScrollView(Region content) {
        this.content = content;
        this.render();
    }

    private void render() {
        scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.getStyleClass().add("scrollbar");
        scrollPane.toBack();

        //Making the scrollbar faster
        if (content != null) {
            content.setOnScroll(event -> {
                double deltaY = event.getDeltaY() * SCROLLING_SPEED;
                double width = scrollPane.getContent().getBoundsInLocal().getWidth();
                double value = scrollPane.getVvalue();
                scrollPane.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
            });
        }
    }

    public ScrollPane getScrollView() {
        return scrollPane;
    }
}
