package com.example.mazegamewithai

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast

class MazeView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    val maze = arrayOf(
        arrayOf(1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1),
        arrayOf(1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1),
        arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1),
        arrayOf(1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1),
        arrayOf(1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1),
    )


    private val wallBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pinkbr)
    private val playerIcon = BitmapFactory.decodeResource(context.resources, R.drawable.humanicon)
    private val aiIcon = BitmapFactory.decodeResource(context.resources, R.drawable.aiicon)

    private val targetPaint = Paint().apply { color = Color.GREEN }
    private val targetRadius = 30f

    private var playerPosition = Pair(0, 1)
    private var aiPosition = Pair(0, 13)
    private val targetPosition = Pair(22, 7)

    private val pathPaint = Paint().apply { color = Color.WHITE }


    private var aiSteps: List<Pair<Int, Int>> = emptyList()
    private var currentAiStepIndex = 0

    private var playerTurn = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawMaze(canvas)
        drawPlayer(canvas)
        drawAI(canvas)
        drawTarget(canvas)
    }

    private fun drawMaze(canvas: Canvas) {
        val cellSize = width / maze[0].size
        for (i in maze.indices) {
            for (j in maze[i].indices) {
                val left = j * cellSize
                val top = i * cellSize
                val right = left + cellSize
                val bottom = top + cellSize

                if (maze[i][j] == 1) {
                    val scaledBitmap =
                        Bitmap.createScaledBitmap(wallBitmap, cellSize, cellSize, false)
                    canvas.drawBitmap(scaledBitmap, left.toFloat(), top.toFloat(), null)
                } else {
                    canvas.drawRect(
                        left.toFloat(),
                        top.toFloat(),
                        right.toFloat(),
                        bottom.toFloat(),
                        pathPaint
                    )
                }
            }
        }
    }

    private fun resizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false)
    }
    private fun drawPlayer(canvas: Canvas) {
        val cellSize = width / maze[0].size
        val cx = (playerPosition.second * cellSize).toFloat()
        val cy = (playerPosition.first * cellSize).toFloat()
        val resizedPlayerIcon = resizeBitmap(playerIcon, cellSize.toInt(), cellSize.toInt())
        canvas.drawBitmap(resizedPlayerIcon, cx, cy, null)
    }

    private fun drawAI(canvas: Canvas) {
        val cellSize = width / maze[0].size
        val cx = (aiPosition.second * cellSize).toFloat()
        val cy = (aiPosition.first * cellSize).toFloat()
        val resizedAIIcon = resizeBitmap(aiIcon, cellSize.toInt(), cellSize.toInt())
        canvas.drawBitmap(resizedAIIcon, cx, cy, null)
    }

    private fun drawTarget(canvas: Canvas) {
        val cellSize = width / maze[0].size
        val cx = (targetPosition.second * cellSize + cellSize / 2).toFloat()
        val cy = (targetPosition.first * cellSize + cellSize / 2).toFloat()
        canvas.drawCircle(cx, cy, targetRadius, targetPaint)
    }
    fun movePlayer(dx: Int, dy: Int) {
        if (playerTurn) {
            val newPos = Pair(playerPosition.first + dy, playerPosition.second + dx)
            if (isValidMove(newPos)) {
                playerPosition = newPos
                Log.d("MazeGame", "Player moved to: $newPos")
                invalidate()  // Redraw the screen
                playerTurn = false
                // Check for winner
                if (playerPosition == targetPosition) {
                    showToast("PLAYER WON")
                } else {
                    moveAI()
                }
            } else {
                Log.d("MazeGame", "Invalid player move: $newPos is a wall")
            }
        }
    }
    fun startGame() {
        Log.d("MazeGame", "Game started")
        findPathToTarget()
        if (aiSteps.isNotEmpty()) {
            aiPosition = aiSteps[currentAiStepIndex]
            Log.d("MazeGame", "AI starts at: $aiPosition")
            invalidate()
            moveAI()
        } else {
            Log.d("MazeGame", "No path found for AI to target.")
        }
    }
    private fun moveAI() {
        if (!playerTurn && aiSteps.isNotEmpty() && currentAiStepIndex < aiSteps.size) {
            postDelayed({
                aiPosition = aiSteps[currentAiStepIndex]
                currentAiStepIndex++
                Log.d("MazeGame", "AI moved to: $aiPosition")
                invalidate()
                playerTurn = true
                if (aiPosition == targetPosition) {
                    showToast("AI WON")
                } else {
                    moveAI()
                }
            }, 500)

        } else {
            Log.d("MazeGame", "AI reached the target or has no moves left")
        }
    }
    private fun findPathToTarget() {
        val visited = mutableSetOf<Pair<Int, Int>>()
        val bestPath = mutableListOf<Pair<Int, Int>>()
        dfs(aiPosition, visited, mutableListOf(), bestPath)
        aiSteps = bestPath
        Log.d("MazeGame", "Best path to target: $aiSteps")
    }

    private fun dfs(position: Pair<Int, Int>, visited: MutableSet<Pair<Int, Int>>, path: MutableList<Pair<Int, Int>>, bestPath: MutableList<Pair<Int, Int>>): Boolean {
        val (x, y) = position

        if (position == targetPosition) {
            path.add(position)
            if (bestPath.isEmpty() || path.size < bestPath.size) {
                bestPath.clear()
                bestPath.addAll(path)
                Log.d("MazeGame", "New best path found: $bestPath")
            }
            path.removeAt(path.size - 1)
            return true
        }

        if (!isValidMove(position) || visited.contains(position)) {
            return false
        }

        visited.add(position)
        path.add(position)

        val directions = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))
        for (direction in directions) {
            val newPos = Pair(x + direction.first, y + direction.second)
            dfs(newPos, visited, path, bestPath)
        }
        visited.remove(position)
        path.removeAt(path.size - 1)

        return false
    }
    private fun isValidMove(position: Pair<Int, Int>): Boolean {
        val (x, y) = position
        return x in maze.indices && y in maze[0].indices && maze[x][y] == 0
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
