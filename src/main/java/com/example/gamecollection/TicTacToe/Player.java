package com.example.gamecollection.TicTacToe;

//Abstract Class
public abstract class Player {
    protected char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public abstract void makeMove(GameBoard board, int row, int col);
}
