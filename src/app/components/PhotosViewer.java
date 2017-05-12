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

import app.layouts.ScrollView;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import models.Image;
import models.ImageUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static app.Partials.*;

public class PhotosViewer {

    private final int viewMode;
    private static int SIZE = 70;

    private BorderPane photosViewerContainer;
    private Rectangle currentPhotoViewer;
    private ScrollView photosScroll;
    private GridPane photosContainer;
    private HashMap<Photo, javafx.scene.image.Image> photosMap = new HashMap<>();
    private ArrayList<Photo> photoViewers = new ArrayList<>();
    private Button addPhoto;
    private Label errorMessage;

    private static Thread photosLoadingThread = null;

    public PhotosViewer(int viewMode) {
        this.viewMode = viewMode;
        this.render();
    }

    private void render() {
        //Current photo viewer
        currentPhotoViewer = new Rectangle(375, 250);
        currentPhotoViewer.setArcHeight(5);
        currentPhotoViewer.setArcWidth(5);
        currentPhotoViewer.setFill(Color.GREY);

        //Add photo button
        if (viewMode == EDIT_MODE) {
            addPhoto = new Button("+");
            addPhoto.getStyleClass().add("add-btn");
            addPhoto.setMinWidth(SIZE);
            addPhoto.setMaxWidth(SIZE);
            addPhoto.setMinHeight(SIZE);
            addPhoto.setMaxHeight(SIZE);

            addPhoto.setOnAction(e -> this.addPhoto());

            errorMessage = new Label("Please upload at least 1 photo");
            errorMessage.getStyleClass().add("error-message");
            errorMessage.setVisible(false);
        }

        //PhotosViewer container
        photosContainer = new GridPane();
        photosContainer.setHgap(5);
        if (viewMode == EDIT_MODE) {
            GridPane.setConstraints(addPhoto, 0 ,0);
            GridPane.setConstraints(errorMessage, 1, 0);
            photosContainer.getChildren().addAll(addPhoto, errorMessage);
        }

        //PhotosViewer scroll
        photosScroll = new ScrollView(photosContainer);
        photosScroll.getScrollView().setPadding(new Insets(2, 0, 2, 0));
        photosScroll.getScrollView().setMinWidth(375);
        photosScroll.getScrollView().setMaxWidth(375);
        photosScroll.getScrollView().setMinHeight(SIZE + 15);
        photosScroll.getScrollView().setMaxHeight(SIZE + 15);
        photosScroll.getScrollView().setTranslateY(5);

        //Photo container
        photosViewerContainer = new BorderPane();
        photosViewerContainer.setStyle("-fx-max-height: 250px");
        photosViewerContainer.setCenter(currentPhotoViewer);
        photosViewerContainer.setBottom(photosScroll.getScrollView());
    }

    public void setPhotos(ArrayList<Image> images) {
        photoViewers.clear();
        photosContainer.getChildren().clear();

        //Loading photos
        Task<String> loadingPhotos = new Task<String>() {
            @Override
            protected String call() throws Exception {
                for (Image image : images) {
                    Photo photo = new Photo(SIZE);
                    photo.setPhoto(ImageUtils.cropAndConvertImage(image.getImage(), SIZE * 2, SIZE * 2));

                    photosMap.put(photo, ImageUtils.cropAndConvertImage(image.getImage(), 375, 250));

                    photo.getPhotoView().setOnMouseClicked(e -> {
                        currentPhotoViewer.setFill(new ImagePattern(photosMap.get(photo)));
                    });
                    photoViewers.add(photo);
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                int counter =0;
                for (Photo photo : photoViewers) {
                    GridPane.setConstraints(photo.getPhotoView(), counter, 0);
                    photosContainer.getChildren().add(photo.getPhotoView());

                    if (counter == 0)
                        currentPhotoViewer.setFill(new ImagePattern(photosMap.get(photo)));
                    counter++;
                }

                if (viewMode == EDIT_MODE)
                    GridPane.setConstraints(addPhoto, counter + 1, 0);
            }
        };

        if (photosLoadingThread == null || !photosLoadingThread.isAlive()) {
            photosLoadingThread = new Thread(loadingPhotos);
            photosLoadingThread.start();
        }
    }

    private void setMainPhoto() {
        // TODO
    }

    private void addPhoto() {
        photosContainer.getChildren().remove(errorMessage);
        Photo newPhoto = new Photo(SIZE);
        photoViewers.add(newPhoto);
        GridPane.setConstraints(newPhoto.getPhotoView(), photoViewers.size(), 0);
        GridPane.setConstraints(addPhoto, photoViewers.size() + 1, 0);
        photosContainer.getChildren().add(newPhoto.getPhotoView());
    }

    public void markAsDanger() {
        errorMessage.setVisible(true);
    }

    public BorderPane getPhotos() {
        return photosViewerContainer;
    }
}
