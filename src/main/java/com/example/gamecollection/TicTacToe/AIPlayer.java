package com.example.gamecollection.TicTacToe;

//Inherited Class

import java.util.Random;

public class AIPlayer extends Player {
    private Random random;

    public AIPlayer(char symbol) {
        super(symbol);
        this.random = new Random();
    }

    @Override
    public void makeMove(GameBoard board, int row, int col) {
        char[][] currentBoard = board.getBoard();
        // Find all empty cells
        int emptyCells = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j] == ' ') {
                    emptyCells++;
                }
            }
        }
        if (emptyCells == 0) return;

        int target = random.nextInt(emptyCells);
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j] == ' ') {
                    if (count == target) {
                        board.setCell(i, j, symbol);
                        return;
                    }
                    count++;
                }
            }
        }
    }
}