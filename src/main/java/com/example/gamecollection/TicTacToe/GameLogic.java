package com.example.gamecollection.TicTacToe;

public class GameLogic {
    public boolean checkWin(char[][] board, char symbol) {
        for (int i = 0; i < 3; i++) {
            if(board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if(board[0][j] == symbol && board[1][j] == symbol && board[2][j] == symbol) {
                return true;
            }
        }
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol)|| (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    public boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
