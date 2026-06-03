# Game Collection

A JavaFX arcade-style launcher that bundles a few quick games into one fullscreen app.

## Games

- Tic Tac Toe
- Number Guess
- Rock Paper Scissors
- Reaction Time

## Requirements

- JDK 23
- Maven Wrapper (`./mvnw`)
- JavaFX dependencies are resolved through Maven

## Run

```bash
./mvnw clean javafx:run
```

On Windows, use:

```powershell
.\mvnw.cmd clean javafx:run
```

## Controls

- `Esc` leaves fullscreen.
- `Home` returns to the dashboard from each game.
- `Exit` closes the app.

## Project Notes

- The app starts in fullscreen by default.
- Scene changes reuse a shared router and stylesheet.
- The dashboard and games use the same dark arcade theme.

## Build

```bash
./mvnw clean package
```

## Structure

- `src/main/java/com/example/gamecollection` contains the launcher, shared router, and standalone mini-game controllers.
- `src/main/resources/com/example/gamecollection` contains the FXML views and shared CSS.
