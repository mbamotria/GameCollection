package com.example.gamecollection.TicTacToe;

//File I/O

import java.io.*;

public class SaveAndLoad {
    private final String fileName = "TicTacToe.txt";

    public void saveGame(char[][] board) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int i = 0; i < 3; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < 3; j++) {
                    char symbol = board[i][j] == ' ' ? '_' : board[i][j];
                    line.append(symbol);
                }
                writer.println(line.toString());
            }
        } catch (IOException e) {
            throw new IOException("Failed to save game: " + e.getMessage());
        }
    }

    public char[][] loadGame() throws FileNotFoundException {
        char[][] board = new char[3][3];

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            for (int i = 0; i < 3; i++) {
                String line = br.readLine();
                if (line == null || line.length() < 3) {
                    throw new FileNotFoundException("Save file is corrupted or incomplete");
                }

                for (int j = 0; j < 3; j++) {
                    board[i][j] = line.charAt(j) == '_' ? ' ' : line.charAt(j);
                }
            }
            return board;

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Save file not found");
        } catch (IOException e) {
            throw new FileNotFoundException("Error reading save file: " + e.getMessage());
        }
    }
}