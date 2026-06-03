package com.example.gamecollection;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class ReactionTimeController {
    @FXML private Button startBtn;
    @FXML private Button targetBtn;
    @FXML private Button restartBtn;
    @FXML private Button homeBtn;
    @FXML private Button exitBtn;
    @FXML private Label statusLabel;
    @FXML private Label reactionLabel;
    @FXML private Label bestLabel;
    @FXML private Label roundsLabel;

    private final Random random = new Random();
    private final PauseTransition waitTimer = new PauseTransition();
    private boolean roundActive;
    private long targetShownAtNanos;
    private long bestReactionNanos = Long.MAX_VALUE;
    private int roundsPlayed;

    @FXML
    private void initialize() {
        styleButtons();
        resetGame();
    }

    private void styleButtons() {
        startBtn.getStyleClass().add("primary-action");
        targetBtn.getStyleClass().add("target-button");
        restartBtn.getStyleClass().add("secondary-action");
        homeBtn.getStyleClass().add("secondary-action");
        exitBtn.getStyleClass().add("secondary-action");
    }

    @FXML
    private void handleStart(ActionEvent event) {
        if (roundActive) {
            return;
        }

        roundActive = true;
        startBtn.setDisable(true);
        targetBtn.setVisible(false);
        targetBtn.setManaged(false);
        statusLabel.setText("Wait for the flash.");
        reactionLabel.setText("Stay ready.");

        int delayMillis = 1200 + random.nextInt(2600);
        waitTimer.stop();
        waitTimer.setDuration(Duration.millis(delayMillis));
        waitTimer.setOnFinished(e -> revealTarget());
        waitTimer.playFromStart();
    }

    @FXML
    private void handleTarget(ActionEvent event) {
        if (!roundActive || !targetBtn.isVisible()) {
            return;
        }

        long reactionNanos = System.nanoTime() - targetShownAtNanos;
        roundsPlayed++;
        bestReactionNanos = Math.min(bestReactionNanos, reactionNanos);

        reactionLabel.setText("Last reaction: " + formatMillis(reactionNanos) + " ms");
        bestLabel.setText("Best: " + formatBest());
        roundsLabel.setText("Rounds: " + roundsPlayed);
        statusLabel.setText("Nice. Press Start for the next round.");

        endRound();
    }

    @FXML
    private void handleRestart(ActionEvent event) {
        resetGame();
    }

    @FXML
    private void handleHome(ActionEvent event) {
        waitTimer.stop();
        try {
            SceneRouter.show((Node) event.getSource(), "Dashboard.fxml", "Game Collection", 1080, 720);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to return to dashboard", e);
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        waitTimer.stop();
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    private void revealTarget() {
        if (!roundActive) {
            return;
        }

        targetShownAtNanos = System.nanoTime();
        targetBtn.setVisible(true);
        targetBtn.setManaged(true);
        targetBtn.setScaleX(0.6);
        targetBtn.setScaleY(0.6);
        targetBtn.setOpacity(0.0);
        targetBtn.requestFocus();
        statusLabel.setText("Click the target now.");

        FadeTransition fade = new FadeTransition(javafx.util.Duration.millis(180), targetBtn);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);

        ScaleTransition scale = new ScaleTransition(javafx.util.Duration.millis(180), targetBtn);
        scale.setFromX(0.6);
        scale.setFromY(0.6);
        scale.setToX(1.0);
        scale.setToY(1.0);

        new ParallelTransition(fade, scale).play();
    }

    private void endRound() {
        roundActive = false;
        startBtn.setDisable(false);
        targetBtn.setVisible(false);
        targetBtn.setManaged(false);
        targetBtn.setOpacity(1.0);
        targetBtn.setScaleX(1.0);
        targetBtn.setScaleY(1.0);
    }

    private void resetGame() {
        waitTimer.stop();
        roundActive = false;
        roundsPlayed = 0;
        bestReactionNanos = Long.MAX_VALUE;
        startBtn.setDisable(false);
        targetBtn.setVisible(false);
        targetBtn.setManaged(false);
        targetBtn.setOpacity(1.0);
        targetBtn.setScaleX(1.0);
        targetBtn.setScaleY(1.0);
        statusLabel.setText("Press Start to begin.");
        reactionLabel.setText("Last reaction: --");
        bestLabel.setText("Best: --");
        roundsLabel.setText("Rounds: 0");
    }

    private String formatMillis(long nanos) {
        return String.valueOf(Math.round(nanos / 1_000_000.0));
    }

    private String formatBest() {
        if (bestReactionNanos == Long.MAX_VALUE) {
            return "--";
        }
        return formatMillis(bestReactionNanos) + " ms";
    }
}
