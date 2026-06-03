package com.example.gamecollection.TicTacToe;

//Creates the structure of the Boaard
public class GameBoard {
    private char[][] board;

    public GameBoard() {
        board = new char[3][3];
    }

    public void initializeBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public void setCell(int row, int col, char symbol) {
        board[row][col] = symbol;
    }
}
