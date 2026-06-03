package com.example.gamecollection.TicTacToe;

//Created Objects of GameBoard,Player,Human Player,AI Player, Game Logic
//Implemented GameActions
//MakeMove throws my custom Exception
//MakeMove is polymorphism(idk how to write it)

import java.io.FileNotFoundException;
import java.io.IOException;

public class GameController implements GameActions{
    SaveAndLoad saveAndLoad = new SaveAndLoad();

    private GameBoard board;
    private Player currentPlayer;
    private HumanPlayer human;
    private AIPlayer computer;
    private GameLogic logic;

    public GameController(){
        board = new GameBoard();
        human = new HumanPlayer('X');
        computer = new AIPlayer('O');
        currentPlayer = human;
        logic = new GameLogic();
    }
    public GameBoard getBoard(){
        return this.board;
    }

    private void switchPlayer(){
        if(currentPlayer == human){
            currentPlayer = computer;
        }
        else{
            currentPlayer = human;
        }
    }

    public void makeMove(int row, int col) throws GameException {
        if(board.getBoard()[row][col]!=' '){
            throw new GameException("Invalid Move: Cell is already occupied");
        }
        currentPlayer.makeMove(board,row,col);
        switchPlayer();
    }


    @Override
    public void restartGame() {
        board.initializeBoard();
        currentPlayer = human;
    }

    @Override
    public void saveGame() throws IOException {
        saveAndLoad.saveGame(board.getBoard());
    }

    @Override
    public void loadGame() throws FileNotFoundException {
        char[][] loadedBoard = saveAndLoad.loadGame();
        board = new GameBoard();
        int xCount = 0;
        int oCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char cell = loadedBoard[i][j];
                board.setCell(i, j, cell);
                if (cell == 'X') {
                    xCount++;
                } else if (cell == 'O') {
                    oCount++;
                }
            }
        }

        currentPlayer = xCount > oCount ? computer : human;
    }
}
