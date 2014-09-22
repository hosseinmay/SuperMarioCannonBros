Super Mario Cannon Bros
====================
Lawyers are on standby.

Game written in Java using AWT and Swing with the aesthetics of the Super Mario Bros series of games by Nintendo. Written by myself and another developer (Github: gbleaney) a while back.  We even wrote some documentation!: 

Game Overview
=============
This program is a basic cannon game that functions primarily through file I/O. You load custom 
environments through the settings menu, then play on them by pressing play from the main menu. You 
fire projectiles at the boss to win the level. Some levels contain destructible objects that can be 
destroyed, but don’t have to be to win the level. Each projectile has a blast radius proportionate to the 
cost to buy it in the shop. Because of this, even if you hit the ground or a destructible object, you may 
still damage the boss. When testing this game use the cheat codes Hossein, Graham, powder, and $$$ to
gain money power and score(all capitalised as you see here). Try to use the super star of death from the 
shop for maximum fun.

Description of Classes:

Boss: Contains all information for a specific boss such as Image or health

FraSubmitScore: Separate popup window for score submitting

FraWindow: Frame that creates all of the "Main" panels (Play, Help, High Score, Settings)

Main: Simply creates the FraWindow

Mario: Contains all info for mario (X,Y, Image)

Player: Class for managing player high score information

PlotProjectile: Physics engine that models the projectile movements based on wind etc

PnlHelp: Simply displays RTFM (we were to lazy to port our extensive manual over)

PnlHighScores: Manages the high scores (uses the player class)

PnlMainMenu: Panel that contains all the main menu buttons seen in FraWindow (Play, Settings etc.)

PnlPlay: Handles the entire Game portion, including creation of the Shop, triggering firing, etc.

PnlPlayButtons: Contains all the buttons, sliders, and some game information, Located to the south in 
panel play

PnlPlayDrawMovements: Draws all actions that happen within the game. Located in the center of panel 
play

PnlPlayShop: Shop panel of panel play. Allows buying powder and upgrades

PnlSettings: Allows the user input of user created worlds --DOES NOT handle the information, passes it to panel play

Projectile: Handles all the information for drawing the current projectile (image), and the information
such as power

World: Contains all information from a world or .env file. Includes boss(es), Destructable Objects, 
Backgrounds, wind, mario, etc.

DestructableObject: a class much like the Boss class, minus a few variables

SoundPlayer: a class of mainly static methods so it you don’t need to create an instance of to use. It 
plays once or loops sound

Bugs
====
This game is fairly robust, and thanks to methods of limiting user input in game (such as sliders), 
its pretty hard to crash. It does have a weak point though; file I/O. The Game is fairly forgiving, and can 
even forgive the deletion of every text file you will find in the root of the project folder. When 
“GameSettings.txt” is missing, you simply lose any extras such as sound, and the game will mock you 
through a SOPL. When “Scores.txt” is missing, the game will generate a new one as soon as there is 
content for it. When “worlds.txt” is missing, the game will make a new one, and populate it with a 
pointer to “MushroomKingdom.env”. 

There are really only two ways to cause any sort of problem you would notice in game (The 
console will sometimes output errors for debugging, but maintain playability). The first was is to have 
bad content in one of the various input files. Bad content includes a number of things. It could be a 
pointer to a file that doesn’t exist, such as a nonexistent image referenced in an .env file. It could also be
a typo in the tags before each line of input such as “BossImage:” which are used to identify the following
input. Another form of bad input would be out of order lines. For example, after the program see’s the 
tag “BossImage:” it assumes the following lines will provide the X and Y coordinates, so if these are 
strings, there will be an input mismatch exception.

The only other way (that we know of) to crash the program is to have a missing 
“MushroomKingdom.env” file. This is the one file that the program defaults to when all else fails. If this 
file is missing, and your “worlds.txt” file is missing, you’re going to receive blank play screen.

Notes to Future Programmers
===========================
1) Make the I/O more robust

Currently the I/O can handle a lot of user mistakes, but more can be done. To make sure any .env or txt 
file can be deleted, hard code the creation of the “MushroomKingdom.env” file, so if it is missing, a new 
one is generated to replace it. Also make the program more forgiving to out of order information by 
checking the tag every time, so anything can go in any order. You could even go a step further with this 
and take in each piece of data as a string, attempt a parse with a try catch, and substitute hardcoded 
data if it fails.

2) Organise the code

The code has gotten a little out of control, and before expanding the game any more, it could use a little 
combing over. How to do this would be completely up to you.

3) Allow importing indestructible objects

Currently the ground only changes based on the gravity, this causes a problem if you want a barrier on 
the top and bottom of the screen, or if you want the gravity making the bullet go away from the ground.
Indestructible objects could remedy this, because ground, platforms, and anything else you can’t shoot 
through or destroy could be imported (instead of being a part of the background image as they are 
now).

4) Create a way to lose

This is an arcade style game, so we never had an intention to have a way to win, but a way to lose (other
than quitting to the menu would be nice). Deciding what makes a loss is the hardest part. Once you 
decide, simply use the same code that the “Main Menu” buttons in the shop and game panels use. This 
will bring up the Submit Score frame, and process their score.

5) Input code from .env files

This would allow you to execute code when a destructible object was destroyed, increasing score, 
powder, money, or perhaps something more complex. The .env files are already equipped to handle this
with the DestructableSpecial: tags, you just need to figure out how to process it. Stack overflow has 
some good information on this, try there.

6) Allow Custom Projectiles

Everything else can be user created, why not the projectiles? The code can handle this fairly easily, and 
you can just add a button to pnlPlayShop to accommodate its purchase.

7) Background Sound:
All you need is a compatible sound file; the GameSettings.txt file is already equipped with the 
BackgroundSound: tag. All you need to do is add the location of the sound file and it will play, I just 
couldn’t find something compatible.

8) AI

AI would be a whole different area of the game where the boss could shoot back at the player. This 
would require extensive work, but its where I see the game going next.

9) Efficiency

Although the game doesn’t lag, I have a bit of an idea for better efficiency. This is sort of like double 
buffering, but only with the images that are static (unmoving). Things like Mario and the Boss could be 
painted on to one background image, when would be updated from the original background whenever a
boss dies etc. This would reduce the amount of code in the overloaded paintComponent() method.

10) Aesthetics
A few things in the game could be cleaned up visually. Custom buttons would make it look a bit more 
polished. Also, when the projectiles fire, the image always looks the same, It would be significantly 
better if the projectile realistically rotated along the same path as its traveling
