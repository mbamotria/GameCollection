# Game Collection

A clean JavaFX arcade launcher with multiple mini-games in one fullscreen app.

## Games Included

- Tic Tac Toe
- Number Guess
- Rock Paper Scissors
- Reaction Time

## Requirements

- Java 17 or higher
- Maven Wrapper is included with the project

## How to Run

### Linux / macOS

```bash
chmod +x mvnw
./mvnw clean javafx:run
```

### Windows

```powershell
.\mvnw.cmd clean javafx:run
```

## Controls

- Tic Tac Toe: click the grid to place your mark.
- Number Guess: enter a number and click Guess.
- Rock Paper Scissors: choose Rock, Paper, or Scissors.
- Reaction Time: start a round, then click the target as fast as possible.
- Restart: start a new round or game.
- Home: return to the dashboard.
- Exit: close the current game window.
- `Esc`: leave fullscreen.

## Build the Project

### Linux / macOS

```bash
./mvnw clean package
```

### Windows

```powershell
.\mvnw.cmd clean package
```

This verifies the project builds and places compiled artifacts in the `target/` folder.

## Project Structure

```text
GameCollection/
├── src/main/java/com/example/gamecollection/
│   ├── GameLauncher.java
│   ├── SceneRouter.java
│   ├── DashboardController.java
│   ├── RockPaperScissorsController.java
│   ├── ReactionTimeController.java
│   ├── NumberGuess/
│   └── TicTacToe/
├── src/main/resources/com/example/gamecollection/
│   ├── Dashboard.fxml
│   ├── TicTacToe.fxml
│   ├── NumberGuess.fxml
│   ├── RockPaperScissors.fxml
│   ├── ReactionTime.fxml
│   └── app.css
├── mvnw & mvnw.cmd
├── pom.xml
└── README.md
```

## Notes

- The app starts in fullscreen by default.
- Scene changes reuse a shared router and the same stylesheet.
- Tic Tac Toe includes save/load support.
- The UI uses a dark arcade-style theme defined in `app.css`.
