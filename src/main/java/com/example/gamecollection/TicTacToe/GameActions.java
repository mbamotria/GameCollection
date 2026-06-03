package com.example.gamecollection.TicTacToe;

//Interface class

import java.io.FileNotFoundException;
import java.io.IOException;

public interface GameActions {
    void restartGame();
    void saveGame() throws IOException;
    void loadGame() throws FileNotFoundException;
}
