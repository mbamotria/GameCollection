package com.example.gamecollection;

import javafx.application.Application;
import javafx.stage.Stage;

public class GameLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setFullScreenExitHint("Press Esc to leave fullscreen");
        primaryStage.setFullScreen(true);
        SceneRouter.show(primaryStage, "Dashboard.fxml", "Game Collection", 1080, 720);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
