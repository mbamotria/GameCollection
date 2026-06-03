package com.example.gamecollection;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class RockPaperScissorsController {
    private enum Choice {
        ROCK("Rock"),
        PAPER("Paper"),
        SCISSORS("Scissors");

        private final String label;

        Choice(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }
    }

    @FXML private Button rockBtn;
    @FXML private Button paperBtn;
    @FXML private Button scissorsBtn;
    @FXML private Button restartBtn;
    @FXML private Button homeBtn;
    @FXML private Button exitBtn;
    @FXML private Label statusLabel;
    @FXML private Label playerScoreLabel;
    @FXML private Label cpuScoreLabel;
    @FXML private Label roundsLabel;

    private final Random random = new Random();
    private int playerScore;
    private int cpuScore;
    private int roundsPlayed;
    private boolean matchOver;

    @FXML
    private void initialize() {
        styleButtons();
        startNewMatch();
    }

    private void styleButtons() {
        rockBtn.getStyleClass().add("choice-button");
        paperBtn.getStyleClass().add("choice-button");
        scissorsBtn.getStyleClass().add("choice-button");
        restartBtn.getStyleClass().add("secondary-action");
        homeBtn.getStyleClass().add("secondary-action");
        exitBtn.getStyleClass().add("secondary-action");
    }

    @FXML
    private void handleRock(ActionEvent event) {
        playRound(Choice.ROCK);
    }

    @FXML
    private void handlePaper(ActionEvent event) {
        playRound(Choice.PAPER);
    }

    @FXML
    private void handleScissors(ActionEvent event) {
        playRound(Choice.SCISSORS);
    }

    @FXML
    private void handleRestart(ActionEvent event) {
        startNewMatch();
    }

    @FXML
    private void handleHome(ActionEvent event) {
        try {
            SceneRouter.show((Node) event.getSource(), "Dashboard.fxml", "Game Collection", 1080, 720);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to return to dashboard", e);
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    private void startNewMatch() {
        playerScore = 0;
        cpuScore = 0;
        roundsPlayed = 0;
        matchOver = false;
        statusLabel.setText("Choose your throw. First to 5 wins.");
        updateScoreboard();
        setChoiceButtonsDisabled(false);
    }

    private void playRound(Choice playerChoice) {
        if (matchOver) {
            return;
        }

        Choice cpuChoice = Choice.values()[random.nextInt(Choice.values().length)];
        roundsPlayed++;

        if (playerChoice == cpuChoice) {
            statusLabel.setText("You chose " + playerChoice.label() + ". CPU chose "
                    + cpuChoice.label() + ". It is a draw.");
        } else if (beats(playerChoice, cpuChoice)) {
            playerScore++;
            statusLabel.setText("You chose " + playerChoice.label() + ". CPU chose "
                    + cpuChoice.label() + ". You win the round.");
        } else {
            cpuScore++;
            statusLabel.setText("You chose " + playerChoice.label() + ". CPU chose "
                    + cpuChoice.label() + ". CPU wins the round.");
        }

        updateScoreboard();
        if (playerScore >= 5 || cpuScore >= 5) {
            matchOver = true;
            setChoiceButtonsDisabled(true);
            statusLabel.setText(playerScore > cpuScore
                    ? "Match won. Press Restart for another best-of-five."
                    : "Match lost. Press Restart for another best-of-five.");
            pulseStatus();
        }
    }

    private boolean beats(Choice playerChoice, Choice cpuChoice) {
        return (playerChoice == Choice.ROCK && cpuChoice == Choice.SCISSORS)
                || (playerChoice == Choice.PAPER && cpuChoice == Choice.ROCK)
                || (playerChoice == Choice.SCISSORS && cpuChoice == Choice.PAPER);
    }

    private void updateScoreboard() {
        playerScoreLabel.setText("You: " + playerScore);
        cpuScoreLabel.setText("CPU: " + cpuScore);
        roundsLabel.setText("Rounds: " + roundsPlayed);
        pulseStatus();
    }

    private void setChoiceButtonsDisabled(boolean disabled) {
        rockBtn.setDisable(disabled);
        paperBtn.setDisable(disabled);
        scissorsBtn.setDisable(disabled);
    }

    private void pulseStatus() {
        statusLabel.setScaleX(0.98);
        statusLabel.setScaleY(0.98);

        ScaleTransition scaleUp = new ScaleTransition(javafx.util.Duration.millis(170), statusLabel);
        scaleUp.setToX(1.0);
        scaleUp.setToY(1.0);

        FadeTransition fade = new FadeTransition(javafx.util.Duration.millis(170), statusLabel);
        fade.setFromValue(0.75);
        fade.setToValue(1.0);

        new ParallelTransition(scaleUp, fade).play();
    }
}
