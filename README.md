# Minesweeper

This game is the traditional Minesweeper game. It does not require any additional modules. The main function is located in the file Minesweeper.java

An executable .jar file can be found at: 
`./Minesweeper/Minesweeper.jar`

There is also a backup .jar file with an older version of the project. This backup can be found at:
`./Minesweeper/Minesweeper_Backup.jar`

## Resources
The image resources were all created by me

The sound files that used in the project are from Free Sound Effects, and a link to their licence agreement can be found here:
    https://www.freesoundeffects.com/licence.php

The high score data is saved and loaded via serialization. These can be reset in-game

## Let's Play!
### Setup
Under the Game menu, you can choose to start a new game, or set the difficulty which starts a new game with that difficulty. 

The difficulties are as follows:
- Beginner: 8x8, 10 mines 
- Intermediate: 16x16, 40 mines
- Expert: 24x24, 99 mines
- Custom: set by user (max: 50x50, 500 mines)

You can also start a new game by pressing the smiley face at the top!

### How to play
The goal of this game is to correctly open all squares not containing a mine. If you open a mine, you die! You click a square to open it, and upon being opened, if there is no mine in the square, it will display a number saying the number of adjacent (all eight squares touching a side or corner) mines. If there are 0 adjacent mines, it will chain open all adjacent squares. You can right click a square to flag it as possibly having a mine under it. Another right click (given that the marks setting is toggled on) gives it a question mark which says you're unsure, and another right click toggles it back to un-marked.

### Some fun features
- High Score list that persists between games
- Colored and black and white versions of the images
- Sound effects

### Future scope
- Expert controls (chording, keyboard controls, etc)
- Number of clicks for high score list
