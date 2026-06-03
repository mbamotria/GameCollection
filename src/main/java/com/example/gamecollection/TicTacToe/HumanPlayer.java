package com.example.gamecollection.TicTacToe;

//Inheritance Class
public class HumanPlayer extends Player {

    public HumanPlayer(char symbol) {
        super(symbol);
    }

    @Override
    public void makeMove(GameBoard board, int row, int col) {
        board.setCell(row, col, symbol);
    }
}
