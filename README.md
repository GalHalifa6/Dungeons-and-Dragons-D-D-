# Dungeons & Dragons- CLI Game
A console-based (CLI) single-player, multi-level Dungeons & Dragons inspired game.  
Fight through dungeons, defeat monsters and traps, gain experience, level up, and survive until the end!

## How to Run
- **Default run:** Simply run the `main` method (the project’s entry point).  
  In VS Code: press ▶ Run on the `Main` class/file.  

No additional setup is required for the basic game.

## Using a Specific Map
If you want to run the game with a **specific map/level file**, update the configuration in your `launch.json`.

**Example – run with a levels directory:**
```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Run Main with Levels Dir",
      "request": "launch",
      "mainClass": "Main",
      "cwd": "${workspaceFolder}",
      "args": "src/levels_dir"
    }
  ]
}
```

**Example – run with a single map file:
```json
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
```

# Controls (Keyboard Commands)

- **w** – Move up  
- **s** – Move down  
- **a** – Move left  
- **d** – Move right  
- **e** – Cast special ability  
- **q** – Skip turn (do nothing)
- 

# Example Board
When running the game, you’ll see ASCII boards like this:



# Example Board
When running the game, you’ll see ASCII boards like this:


#################################################
#....s...#B#..........................#.........#
#........###....##..........##........#.........#
#........#......##..........##........#.........#
#........#............................#.........#
#........#............................#.........#
#........#......##..........##........#.........#
#........#......##s........k##........#.........#
#........#s.................##.......k#.........#
#@.............................................M#
#........#s.................##.......k#.........#
#........#......##s........k##........#.........#
#........#......##..........##........#.........#
#........#............................#.........#
#........#............................#.........#
#........#......##..........##........#.........#
#........###....##..........##........#.........#
#....s...#B#..........................#.........#
#################################################




**@** – Player  
**s, k, M, …** – Enemies  
**#** – Walls  
**.** – Free space  

Have fun!



