# Dungeons & Dragons- CLI Game
A console-based (CLI) single-player, multi-level Dungeons & Dragons inspired game.
Fight through dungeons, defeat monsters and traps, gain experience, level up, and survive until the end!

# How to Run
Default run: Simply run the main method (the project’s entry point).
In VS Code: press ▶ Run on the Main class/file.
No additional setup is required for the basic game.

# Using a Specific Map
If you want to run the game with a specific map/level file, update the configuration in your launch.json.
Example – run with a single map file:
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Run Main with Single Map",
      "request": "launch",
      "mainClass": "Main",
      "cwd": "${workspaceFolder}",
      "args": "src/levels_dir/level1.txt"
    }
  ]
}

Just replace the path (src/levels_dir/...) with the actual location of your map files.

# Controls
w – Move up
s – Move down
a – Move left
d – Move right
e – Cast special ability
q – Skip turn (do nothing)

