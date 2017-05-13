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
import javafx.stage.FileChooser;
import models.Image;
import models.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private ArrayList<BufferedImage> uploadedImages = new ArrayList<>();
    private Button addPhoto;
    private Label errorMessage;
    private FileChooser fileChooser;
    private File choosenFile;

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
        currentPhotoViewer.setFill(Color.rgb(245,248,250));

        //Add photo button
        if (viewMode == EDIT_MODE) {
            addPhoto = new Button("+");
            addPhoto.getStyleClass().add("add-btn");
            addPhoto.setMinWidth(SIZE);
            addPhoto.setMaxWidth(SIZE);
            addPhoto.setMinHeight(SIZE);
            addPhoto.setMaxHeight(SIZE);

            addPhoto.setOnAction(e -> {
                try {
                    this.addPhoto();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

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

        //File  choose
        if (viewMode == EDIT_MODE) {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Upload new photo!");

            FileChooser.ExtensionFilter fileExtensions =
                    new FileChooser.ExtensionFilter(
                            "Photos", "*.png", "*.jpg", "*.jpeg", "*.gif");

            fileChooser.getExtensionFilters().add(fileExtensions);
        }
    }

    public void setPhotos(ArrayList<Image> images) {
        photosContainer.getChildren().remove(errorMessage);
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

    public ArrayList<BufferedImage> getUploadedImages() {
        return uploadedImages;
    }

    public void resetPhotoView(int mode) {
        currentPhotoViewer.setFill(Color.rgb(245,248,250));
        photoViewers.clear();
        uploadedImages.clear();
        photosContainer.getChildren().clear();
        if (mode == EDIT_MODE) {
            GridPane.setConstraints(addPhoto, 0, 0);
            GridPane.setConstraints(errorMessage, 1, 0);
            errorMessage.setVisible(false);
            photosContainer.getChildren().addAll(addPhoto, errorMessage);
        }
    }

    private void addPhoto() throws IOException {
        choosenFile = fileChooser.showOpenDialog(null);

        if (choosenFile != null) {
            BufferedImage currentImage = ImageIO.read(choosenFile);
            Photo newPhoto = new Photo(SIZE);

            float ratio;
            if (currentImage.getWidth() > 8000 || currentImage.getHeight() > 8000) {
                ratio = 8;
            } else if (currentImage.getWidth() > 4000 || currentImage.getHeight() > 4000) {
                ratio = 3;
            } else if (currentImage.getWidth() > 2000 || currentImage.getHeight() > 2000) {
                ratio = 2;
            } else {
                ratio = 1;
            }

            currentImage = ImageUtils.scale(currentImage, (int)(currentImage.getWidth() / ratio),
                    (int) (currentImage.getHeight() / ratio),
                    1 / ratio,
                    1 / ratio);

            newPhoto.setPhoto(ImageUtils.cropAndConvertImage(currentImage, SIZE, SIZE));

            photoViewers.add(newPhoto);
            photosMap.put(newPhoto, ImageUtils.cropAndConvertImage(currentImage, 375, 250));
            currentPhotoViewer.setFill(new ImagePattern(photosMap.get(newPhoto)));
            uploadedImages.add(currentImage);

            newPhoto.getPhotoView().setOnMouseClicked(e -> {
                currentPhotoViewer.setFill(new ImagePattern(photosMap.get(newPhoto)));
            });

            GridPane.setConstraints(newPhoto.getPhotoView(), photoViewers.size(), 0);
            GridPane.setConstraints(addPhoto, photoViewers.size() + 1, 0);
            photosContainer.getChildren().add(newPhoto.getPhotoView());
            photosContainer.getChildren().remove(errorMessage);
        }
    }

    public void markAsDanger() {
        errorMessage.setVisible(true);
    }

    public BorderPane getPhotos() {
        return photosViewerContainer;
    }
}
