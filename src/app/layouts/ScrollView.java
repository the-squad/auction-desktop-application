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
        content.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * SCROLLING_SPEED;
            double width = scrollPane.getContent().getBoundsInLocal().getWidth();
            double value = scrollPane.getVvalue();
            scrollPane.setVvalue(value + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
    }

    public ScrollPane getScrollView() {
        return scrollPane;
    }
}
