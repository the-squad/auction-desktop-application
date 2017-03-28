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

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Created by Muhammad on 28-Mar-17.
 */
public class animations {

        Timeline fadeAnimation = new Timeline();

        //Opacity values
        KeyValue fromChildOpacityStart = new KeyValue(fromChild.opacityProperty(), 1);
        KeyValue fromChildOpacityEnd = new KeyValue(fromChild.opacityProperty(), 0);

        KeyValue toChildOpacityStart = new KeyValue(toChild.opacityProperty(), 0);
        KeyValue toChildOpacityEnd = new KeyValue(toChild.opacityProperty(), 1);

        //Keyframes
        KeyFrame startFadeOut = new KeyFrame(Duration.ZERO, fromChildOpacityStart);
        KeyFrame finishFadeOut = new KeyFrame(Duration.millis(250), fromChildOpacityEnd);

        KeyFrame startFadeIn = new KeyFrame(Duration.millis(250), toChildOpacityStart);
        KeyFrame addingToCenter = new KeyFrame(Duration.millis(251), e -> {
            parent.setCenter(null);
            parent.setCenter(toChild);
        });
        KeyFrame finishFadeIn = new KeyFrame(Duration.millis(500), toChildOpacityEnd);

        //Gathering down key frames
        fadeAnimation.getKeyFrames().addAll(startFadeIn, finishFadeIn, startFadeOut, addingToCenter, finishFadeOut);
        fadeAnimation.play();
    }
}
