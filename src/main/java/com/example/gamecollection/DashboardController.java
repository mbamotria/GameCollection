package com.example.gamecollection;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button tttBtn;
    @FXML
    private Button ngBtn;
    @FXML
    private Button rpsBtn;
    @FXML
    private Button reactionBtn;
    @FXML
    private Button exitBtn;

    @FXML
    public void initialize() {
        styleCard(tttBtn);
        styleCard(ngBtn);
        styleCard(rpsBtn);
        styleCard(reactionBtn);
        exitBtn.getStyleClass().add("secondary-action");

        animateCard(tttBtn, 0);
        animateCard(ngBtn, 70);
        animateCard(rpsBtn, 140);
        animateCard(reactionBtn, 210);
        animateButton(exitBtn, 280);
    }

    private void styleCard(Button button) {
        button.getStyleClass().add("card-button");
        button.setWrapText(true);
    }

    private void animateCard(Node node, double delayMillis) {
        animateButton(node, delayMillis);
    }

    private void animateButton(Node node, double delayMillis) {
        node.setOpacity(0.0);
        node.setTranslateY(18.0);
        node.setScaleX(0.97);
        node.setScaleY(0.97);

        FadeTransition fade = new FadeTransition(Duration.millis(260), node);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);

        TranslateTransition translate = new TranslateTransition(Duration.millis(260), node);
        translate.setFromY(18.0);
        translate.setToY(0.0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(260), node);
        scale.setFromX(0.97);
        scale.setFromY(0.97);
        scale.setToX(1.0);
        scale.setToY(1.0);

        PauseTransition delay = new PauseTransition(Duration.millis(delayMillis));
        new SequentialTransition(delay, new ParallelTransition(fade, translate, scale)).play();
    }

    private void openGame(ActionEvent event, String fxml, String title, double width, double height) {
        try {
            SceneRouter.show((Node) event.getSource(), fxml, title, width, height);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to open " + title, e);
        }
    }

    @FXML
    private void launchTicTacToe(ActionEvent event) {
        openGame(event, "TicTacToe.fxml", "Tic Tac Toe", 760, 860);
    }

    @FXML
    private void launchNumberGuess(ActionEvent event) {
        openGame(event, "NumberGuess.fxml", "Number Guess", 760, 560);
    }

    @FXML
    private void launchRockPaperScissors(ActionEvent event) {
        openGame(event, "RockPaperScissors.fxml", "Rock Paper Scissors", 760, 560);
    }

    @FXML
    private void launchReactionTime(ActionEvent event) {
        openGame(event, "ReactionTime.fxml", "Reaction Time", 760, 560);
    }

    @FXML
    private void exitApp(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
