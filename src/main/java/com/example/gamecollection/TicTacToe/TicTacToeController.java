package com.example.gamecollection.TicTacToe;

import com.example.gamecollection.SceneRouter;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToeController {
    private boolean gameOver = false;
    private final Random random = new Random();
    private final GameController gameController = new GameController();
    private final GameLogic gameLogic = new GameLogic();
    private final String[][] renderedBoard = {
            {"", "", ""},
            {"", "", ""},
            {"", "", ""}
    };

    @FXML private Button Btn00, Btn01, Btn02, Btn10, Btn11, Btn12, Btn20, Btn21, Btn22;
    @FXML private Label statusLabel;
    @FXML private Button saveBtn, loadBtn, restartBtn, exitBtn, homeBtn;

    @FXML
    private void initialize() {
        styleButtons();
        gameController.getBoard().initializeBoard();
        updateBoard();
        statusLabel.setText("Your turn (X)");
    }

    private void styleButtons() {
        Button[] boardButtons = {
                Btn00, Btn01, Btn02,
                Btn10, Btn11, Btn12,
                Btn20, Btn21, Btn22
        };
        for (Button button : boardButtons) {
            button.getStyleClass().add("board-cell");
        }

        saveBtn.getStyleClass().add("secondary-action");
        loadBtn.getStyleClass().add("secondary-action");
        restartBtn.getStyleClass().add("secondary-action");
        exitBtn.getStyleClass().add("secondary-action");
        homeBtn.getStyleClass().add("secondary-action");
    }

    @FXML
    public void handleEvent(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String id = clickedButton.getId();

        int row = Integer.parseInt(id.substring(3, 4));
        int col = Integer.parseInt(id.substring(4, 5));

        try {
            gameController.makeMove(row, col);
            updateBoard();

            if (isGameOver()) {
                return;
            }

            disableAllButtons();
            Platform.runLater(() -> {
                try {
                    makeAIMove();
                    updateBoard();
                    isGameOver();
                } catch (GameException e) {
                    statusLabel.setText("Error: " + e.getMessage());
                }
            });
        } catch (GameException e) {
            statusLabel.setText(e.getMessage());
        }
    }

    private boolean isGameOver() {
        char[][] board = gameController.getBoard().getBoard();
        if (gameLogic.checkWin(board, 'X')) {
            statusLabel.setText("You win!");
            gameOver = true;
            disableAllButtons();
            return true;
        }
        if (gameLogic.checkWin(board, 'O')) {
            statusLabel.setText("You lose!");
            gameOver = true;
            disableAllButtons();
            return true;
        }
        if (gameLogic.isBoardFull(board)) {
            statusLabel.setText("It's a tie!");
            gameOver = true;
            disableAllButtons();
            return true;
        }
        return false;
    }

    private void updateBoard() {
        char[][] board = gameController.getBoard().getBoard();
        Button[][] buttons = {
                {Btn00, Btn01, Btn02},
                {Btn10, Btn11, Btn12},
                {Btn20, Btn21, Btn22}
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = buttons[i][j];
                char cell = board[i][j];
                String renderedValue = cell == ' ' ? "" : String.valueOf(cell);
                String previousValue = renderedBoard[i][j];
                button.setText(renderedValue);
                button.setDisable(gameOver || cell != ' ');
                if (!renderedValue.isEmpty() && !renderedValue.equals(previousValue)) {
                    animateCell(button);
                }
                renderedBoard[i][j] = renderedValue;
            }
        }
    }

    private void makeAIMove() throws GameException {
        char[][] board = gameController.getBoard().getBoard();
        List<int[]> emptyCells = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            int[] move = emptyCells.get(random.nextInt(emptyCells.size()));
            gameController.makeMove(move[0], move[1]);
        }
    }

    private void disableAllButtons() {
        Button[] buttons = {
                Btn00, Btn01, Btn02,
                Btn10, Btn11, Btn12,
                Btn20, Btn21, Btn22
        };

        for (Button button : buttons) {
            button.setDisable(true);
        }
    }

    @FXML
    public void handleRestart() {
        gameController.restartGame();
        gameOver = false;
        statusLabel.setText("Your turn (X)");
        resetRenderedBoard();
        updateBoard();
    }

    @FXML
    public void handleSave() {
        try {
            gameController.saveGame();
            statusLabel.setText("Game saved successfully");
        } catch (IOException e) {
            statusLabel.setText("Error saving game: " + e.getMessage());
        }
    }

    @FXML
    public void handleLoad() {
        try {
            gameController.loadGame();
            gameOver = false;
            resetRenderedBoard();
            updateBoard();
            if (!isGameOver()) {
                statusLabel.setText("Game loaded successfully. Your turn (X).");
            }
        } catch (FileNotFoundException e) {
            statusLabel.setText("Error loading game: " + e.getMessage());
        }
    }

    @FXML
    public void handleHome(javafx.event.ActionEvent event) {
        try {
            SceneRouter.show((Node) event.getSource(), "Dashboard.fxml", "Game Collection", 1080, 720);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to return to dashboard", e);
        }
    }

    @FXML
    public void handleExit() {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    private void animateCell(Button button) {
        button.setScaleX(0.82);
        button.setScaleY(0.82);
        button.setOpacity(0.0);

        FadeTransition fade = new FadeTransition(javafx.util.Duration.millis(180), button);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);

        ScaleTransition scale = new ScaleTransition(javafx.util.Duration.millis(180), button);
        scale.setFromX(0.82);
        scale.setFromY(0.82);
        scale.setToX(1.0);
        scale.setToY(1.0);

        new ParallelTransition(fade, scale).play();
    }

    private void resetRenderedBoard() {
        for (int i = 0; i < renderedBoard.length; i++) {
            for (int j = 0; j < renderedBoard[i].length; j++) {
                renderedBoard[i][j] = "";
            }
        }
    }
}
