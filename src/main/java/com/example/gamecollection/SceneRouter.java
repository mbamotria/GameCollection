package com.example.gamecollection;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public final class SceneRouter {
    private static final String STYLESHEET = "app.css";

    private SceneRouter() {
    }

    public static void show(Stage stage, String fxml, String title, double width, double height) throws IOException {
        Parent root = loadRoot(fxml);
        Scene scene = new Scene(root, width, height);
        addStylesheet(scene);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setFullScreen(true);

        playIntroAnimation(root);
    }

    public static void show(Node source, String fxml, String title, double width, double height) throws IOException {
        Stage stage = (Stage) source.getScene().getWindow();
        show(stage, fxml, title, width, height);
    }

    private static Parent loadRoot(String fxml) throws IOException {
        URL resource = SceneRouter.class.getResource(fxml);
        if (resource == null) {
            throw new IOException("Unable to load view: " + fxml);
        }
        return FXMLLoader.load(resource);
    }

    private static void addStylesheet(Scene scene) {
        URL stylesheet = SceneRouter.class.getResource(STYLESHEET);
        if (stylesheet != null) {
            scene.getStylesheets().add(stylesheet.toExternalForm());
        }
    }

    private static void playIntroAnimation(Parent root) {
        root.setOpacity(0.0);
        root.setScaleX(0.985);
        root.setScaleY(0.985);

        FadeTransition fade = new FadeTransition(Duration.millis(220), root);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(220), root);
        scale.setFromX(0.985);
        scale.setFromY(0.985);
        scale.setToX(1.0);
        scale.setToY(1.0);

        new ParallelTransition(fade, scale).play();
    }
}
