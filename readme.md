# Overview
BowlingScoreKeeper contains the backend logic to keep track of players' scores in the game of bowling. 
Interaction occurs through the GameController.

# Usage
To start up a game, create a new GameController and provide a name for each player:
```
List<String> names = new Arrays.asList("Billy", "Joe");
GameController gameController = new GameController(names);
```
Then scores can be added one by one. For example, here is how you would add a score of eight
```
List<Boolean> eightPins = Arrays.asList(true, true, false, false, false, false, false, false, false, false);
gameController.addScore(eightPins)
```
Finally, the players, along with their scores, can be retrieved
```
gameController.getPlayers()
```

until the game is over.
```
gameController.isGameOver()
```

# Limitations
+ Cannot add a new player once the game has started.
+ Cannot go back and modify a score or restart a frame

# Models and Their Relationships

BowlingScoreKeeper is made up of four models:
+ Frame
+ Scorecard
+ Player
+ Game

A scorecard has a list of frames, a player has a scorecard, and game has a list of players.


