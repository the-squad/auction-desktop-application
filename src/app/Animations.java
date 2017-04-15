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

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class Animations {

    static void fade(BorderPane parent, Region fromChild, Region toChild) {
        Timeline fadeAnimation = new Timeline();

        //Opacity values
        KeyValue fromChildOpacityStart = new KeyValue(fromChild.opacityProperty(), 1);
        KeyValue fromChildOpacityEnd = new KeyValue(fromChild.opacityProperty(), 0);

        KeyValue toChildOpacityStart = new KeyValue(toChild.opacityProperty(), 0);
        KeyValue toChildOpacityEnd = new KeyValue(toChild.opacityProperty(), 1);

        //Keyframes
        KeyFrame startFadeOut = new KeyFrame(Duration.ZERO, fromChildOpacityStart);
        KeyFrame finishFadeOut = new KeyFrame(Duration.millis(150), fromChildOpacityEnd);

        KeyFrame startFadeIn = new KeyFrame(Duration.millis(150), toChildOpacityStart);
        KeyFrame addingToCenter = new KeyFrame(Duration.millis(151), e -> {
            parent.setCenter(null);
            parent.setCenter(toChild);
        });
        KeyFrame finishFadeIn = new KeyFrame(Duration.millis(300), toChildOpacityEnd);

        //Gathering down key frames
        fadeAnimation.getKeyFrames().addAll(startFadeIn, finishFadeIn, startFadeOut, addingToCenter, finishFadeOut);
        fadeAnimation.play();
    }

    static void fadeOutThenSlideUp(BorderPane parent, Region tab, Region page) {
        Timeline fadeOutThenSlideUpAnimation = new Timeline();

        //Creating all key values for the animation
        KeyValue tabOpacityStart = new KeyValue(tab.opacityProperty(), 1);
        KeyValue tabChildOpacityEnd = new KeyValue(tab.opacityProperty(), 0);

        KeyValue pageOpacityStart = new KeyValue(page.opacityProperty(), 0);
        KeyValue pageOpacityEnd = new KeyValue(page.opacityProperty(), 1);

        KeyValue pageTranslateStart = new KeyValue(page.translateYProperty(), 20);
        KeyValue pageTranslateEnd = new KeyValue(page.translateYProperty(), 0, Interpolator.EASE_BOTH);

        //Hiding and moving fromChild
        KeyFrame startFadeOut = new KeyFrame(Duration.ZERO, tabOpacityStart);
        KeyFrame finishFadeOut = new KeyFrame(Duration.millis(125), tabChildOpacityEnd);

        //Clearing the setCenter
        KeyFrame clear = new KeyFrame(Duration.millis(151), e -> {
            parent.setCenter(null);
            parent.setCenter(page);
        });

        //Showing and moving toChild
        KeyFrame startFadeIn = new KeyFrame(Duration.millis(151), pageOpacityStart);
        KeyFrame startMoveIn = new KeyFrame(Duration.millis(151), pageTranslateStart);
        KeyFrame finishFadeIn = new KeyFrame(Duration.millis(276), pageOpacityEnd);
        KeyFrame finishMoveIn = new KeyFrame(Duration.millis(301), pageTranslateEnd);

        fadeOutThenSlideUpAnimation.getKeyFrames().addAll(startFadeOut, finishFadeOut, clear, startFadeIn, startMoveIn, finishFadeIn, finishMoveIn);
        fadeOutThenSlideUpAnimation.play();
    }

    static void slideDownThenFadeIn(BorderPane parent, Region page, Region tab) {
        Timeline slideDownThenFadeInAnimation = new Timeline();

        //Creating all key values for the animation
        KeyValue pageOpacityStart = new KeyValue(page.opacityProperty(), 1);
        KeyValue pageOpacityEnd = new KeyValue(page.opacityProperty(), 0);

        KeyValue pageTranslateStart = new KeyValue(page.translateYProperty(), 0);
        KeyValue pageTranslateEnd = new KeyValue(page.translateYProperty(), 20, Interpolator.EASE_BOTH);

        KeyValue tabOpacityStart = new KeyValue(tab.opacityProperty(), 0);
        KeyValue tabChildOpacityEnd = new KeyValue(tab.opacityProperty(), 1);

        //Creating the timeline keyframes
        KeyFrame startFadeIn = new KeyFrame(Duration.ZERO, pageOpacityStart);
        KeyFrame startMoveIn = new KeyFrame(Duration.ZERO, pageTranslateStart);
        KeyFrame finishFadeIn = new KeyFrame(Duration.millis(125), pageOpacityEnd);
        KeyFrame finishMoveIn = new KeyFrame(Duration.millis(150), pageTranslateEnd);

        //Clearing the setCenter
        KeyFrame clear = new KeyFrame(Duration.millis(151), e -> {
            parent.setCenter(null);
            parent.setCenter(tab);
        });

        //Showing and moving toChild
        KeyFrame startFadeOut = new KeyFrame(Duration.millis(151), tabOpacityStart);
        KeyFrame finishFadeOut = new KeyFrame(Duration.millis(276), tabChildOpacityEnd);

        slideDownThenFadeInAnimation.getKeyFrames().addAll(startFadeOut, finishFadeOut, clear, startFadeIn, startMoveIn, finishFadeIn, finishMoveIn);
        slideDownThenFadeInAnimation.play();
    }

    static void slideDownThenSlideUp(BorderPane parent, Region fromChild, Region toChild) {
        Timeline translateAnimation = new Timeline();

        //Creating all key values for the animation
        KeyValue fromChildOpacityStart = new KeyValue(fromChild.opacityProperty(), 1);
        KeyValue fromChildOpacityEnd = new KeyValue(fromChild.opacityProperty(), 0);

        KeyValue toChildOpacityStart = new KeyValue(toChild.opacityProperty(), 0);
        KeyValue toChildOpacityEnd = new KeyValue(toChild.opacityProperty(), 1);

        KeyValue fromChildTranslateStart = new KeyValue(fromChild.translateYProperty(), 0);
        KeyValue fromChildTranslateEnd = new KeyValue(fromChild.translateYProperty(), 20, Interpolator.EASE_BOTH);

        KeyValue toChildTranslateStart = new KeyValue(toChild.translateYProperty(), 20);
        KeyValue toChildTranslateEnd = new KeyValue(toChild.translateYProperty(), 0, Interpolator.EASE_BOTH);

        //Creating the timeline keyframes
        //Hiding and moving fromChild
        KeyFrame startMoveOut = new KeyFrame(Duration.ZERO, fromChildTranslateStart);
        KeyFrame startFadeOut = new KeyFrame(Duration.ZERO, fromChildOpacityStart);
        KeyFrame finishFadeOut = new KeyFrame(Duration.millis(125), fromChildOpacityEnd);
        KeyFrame finishMoveOut = new KeyFrame(Duration.millis(150), fromChildTranslateEnd);

        //Clearing the setCenter
        KeyFrame clear = new KeyFrame(Duration.millis(151), e -> {
            parent.setCenter(null);
            parent.setCenter(toChild);
        });

        //Showing and moving toChild
        KeyFrame startFadeIn = new KeyFrame(Duration.millis(151), toChildOpacityStart);
        KeyFrame startMoveIn = new KeyFrame(Duration.millis(151), toChildTranslateStart);
        KeyFrame finishFadeIn = new KeyFrame(Duration.millis(276), toChildOpacityEnd);
        KeyFrame finishMoveIn = new KeyFrame(Duration.millis(301), toChildTranslateEnd);

        translateAnimation.getKeyFrames().addAll(startFadeOut, startMoveOut, finishFadeOut, finishMoveOut, clear, startFadeIn, startMoveIn, finishFadeIn, finishMoveIn);
        translateAnimation.play();
    }
}
