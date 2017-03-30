# JavaFX-Game---Jumping-Frog
It's a JavaFX game made in 2015 and the idea comes from the Chinese Jinshan Dazi Game (金山打字游戏) which is meant to train your typing skills via playing game. You need to install the e(fx)clipse tool and Scene Builder to run this application. Besides, you need to fill in your own server address, username and password in the database.properties and make the corresponding tables in your own database.

This project has used Javafx API for making an interractive UI.

Initially, it was a game running locally. Then, it was extended to online game. Player needs to create an account in a remote database, all the words from different levels are stored in the database as well. Besides, the game record is also stored in the database. Player can check his best score in history and the ranking list.

Mediaplayer is used to play music in the background. Java.awt.event.ActionListener is used to pause and play the game.

MVC idea is first used in this project. Runnable interface is used to create the Thread to start the game.

The game algorithm and logic are well designed in src/ui/PanelGame.java
