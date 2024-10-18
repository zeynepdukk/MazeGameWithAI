# AI Maze Game

## Description
The AI Maze Game is an Android application developed in Kotlin that features a player and an AI navigating through a maze using the Depth-First Search (DFS) algorithm. The objective is for both the player and the AI to reach a designated target location, with each taking turns to make their moves.
## Features
- **Maze Generation**: A challenging maze layout that players can navigate.
- **Player and AI**: The player and AI (controlled by DFS) start at specific positions in the maze.
- **Turn-Based Gameplay**: The player and AI take turns to move toward the target.
- **Custom Graphics**: Retro-style pixel art for the maze and characters.
- **Keyboard Controls**: Players can control movement using the arrow keys.

## Technologies Used
- Kotlin
- Android SDK
- Android Studio
- Canvas for custom drawing
- Depth-First Search (DFS) algorithm for AI pathfinding

## Usage 
- Use the asdw keys to control the player's movement.
- Observe the AI as it navigates the maze using the DFS algorithm.
- The game ends when either the player or the AI reaches the target(green) position.

## Algorithm: Depth-First Search (DFS)
### Overview
The AI in this maze game utilizes the Depth-First Search (DFS) algorithm to navigate through the maze from its starting position to the target. DFS is a fundamental algorithm used for traversing or searching tree or graph data structures. It explores all possible paths by going as deep as possible before backtracking, making it suitable for maze-solving tasks.

### How It Works
#### Initialization: The algorithm begins at the AI's starting position within the maze.
#### Exploration: DFS explores each potential move from the current position in a depth-first manner, moving to one of the adjacent cells (up, down, left, or right) that are valid (i.e., not walls).
#### Backtracking: When a dead end is reached (a cell with no valid adjacent cells), the algorithm backtracks to the last valid position and tries the next available direction.
#### Target Check: During the exploration, the algorithm continuously checks if the current position matches the target coordinates. If a match is found, the path taken to reach this point is recorded.
#### Path Storage: The path to the target is stored in a list, which the AI will follow to reach its destination once the exploration is complete.

## Screenshots
![mazeai](https://github.com/user-attachments/assets/1ae72c96-01ac-4a4c-aaf8-21ad90e74b37)
