package com.example.gamecollection.NumberGuess;

import com.example.gamecollection.SceneRouter;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class NumberGuessController {
    @FXML private TextField numberBox;
    @FXML private Button guessBtn;
    @FXML private Label statusLabel;
    @FXML private Label attemptsLabel;
    @FXML private Button restartBtn;
    @FXML private Button exitBtn;
    @FXML private Button homeBtn;

    private final Random random = new Random();
    private int randomNumber;
    private int attempts;
    private boolean gameOver;

    @FXML
    public void initialize() {
        styleButtons();
        startNewGame();
        numberBox.setOnAction(this::handleGuess);
    }

    private void styleButtons() {
        guessBtn.getStyleClass().add("primary-action");
        restartBtn.getStyleClass().add("secondary-action");
        exitBtn.getStyleClass().add("secondary-action");
        homeBtn.getStyleClass().add("secondary-action");
    }

    private void startNewGame() {
        randomNumber = random.nextInt(100) + 1;
        attempts = 0;
        gameOver = false;
        attemptsLabel.setText("Attempts: 0 / 10");
        statusLabel.setText("Guess the number");
        numberBox.clear();
        numberBox.setDisable(false);
        guessBtn.setDisable(false);
        numberBox.requestFocus();
    }

    private void finishGame(String message) {
        gameOver = true;
        statusLabel.setText(message);
        pulseStatus();
        numberBox.setDisable(true);
        guessBtn.setDisable(true);
    }

    @FXML
    private void handleGuess(ActionEvent event) {
        if (gameOver) {
            return;
        }

        String input = numberBox.getText().trim();
        if (input.isEmpty()) {
            statusLabel.setText("Enter a number from 1 to 100.");
            pulseStatus();
            return;
        }

        try {
            int guess = Integer.parseInt(input);
            if (guess < 1 || guess > 100) {
                statusLabel.setText("Use a number between 1 and 100.");
                pulseStatus();
                return;
            }

            attempts++;
            attemptsLabel.setText("Attempts: " + attempts + " / 10");

            if (guess == randomNumber) {
                finishGame("Correct. You found it.");
            } else if (attempts >= 10) {
                finishGame("Game over. The number was " + randomNumber + ".");
            } else if (guess < randomNumber) {
                statusLabel.setText("Too low. Try a higher number.");
                pulseStatus();
            } else {
                statusLabel.setText("Too high. Try a lower number.");
                pulseStatus();
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Only whole numbers work here.");
            pulseStatus();
        } finally {
            numberBox.clear();
        }
    }

    @FXML
    public void handleRestart(ActionEvent event) {
        startNewGame();
    }

    @FXML
    public void handleHome(ActionEvent event) {
        try {
            SceneRouter.show((Node) event.getSource(), "Dashboard.fxml", "Game Collection", 1080, 720);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to return to dashboard", e);
        }
    }

    @FXML
    public void handleExit(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    private void pulseStatus() {
        statusLabel.setScaleX(0.98);
        statusLabel.setScaleY(0.98);

        ScaleTransition scaleUp = new ScaleTransition(javafx.util.Duration.millis(160), statusLabel);
        scaleUp.setFromX(0.98);
        scaleUp.setFromY(0.98);
        scaleUp.setToX(1.0);
        scaleUp.setToY(1.0);

        FadeTransition fade = new FadeTransition(javafx.util.Duration.millis(160), statusLabel);
        fade.setFromValue(0.72);
        fade.setToValue(1.0);

        new ParallelTransition(scaleUp, fade).play();
    }
}
