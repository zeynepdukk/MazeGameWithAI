package com.example.mazegamewithai

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class MazeView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val maze = arrayOf(
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 0, 0, 1, 0, 0, 1, 0, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 1, 0, 1),
        arrayOf(1, 0, 1, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 0, 1, 0, 1, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 1),
        arrayOf(1, 1, 0, 1, 1, 1, 1, 0, 1, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 0, 1, 1),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 0, 1, 1),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 1, 0, 1),
        arrayOf(1, 0, 1, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(0, 0, 1, 0, 1, 1, 1, 0, 0, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 0, 1, 1)
    )


    private var playerPosition = Pair(2, 1)  // Oyuncunun başlangıç konumu
    private var aiPosition = Pair(2, 1)       // AI'nın başlangıç konumu
    private val targetPosition = Pair(5, 5)   // Hedef konumu

    private val playerPaint = Paint().apply { color = Color.RED }
    private val aiPaint = Paint().apply { color = Color.BLUE }
    private val wallPaint = Paint().apply { color = Color.BLACK }
    private val pathPaint = Paint().apply { color = Color.WHITE }

    private val playerRadius = 30f // Oyuncu yarıçapı
    private val aiRadius = 30f      // AI yarıçapı

    private var aiSteps: List<Pair<Int, Int>> = emptyList()
    private var currentAiStepIndex = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawMaze(canvas)
        drawPlayer(canvas)
        drawAI(canvas)
    }

    private fun drawMaze(canvas: Canvas) {
        val cellSize = width / maze[0].size
        for (i in maze.indices) {
            for (j in maze[i].indices) {
                val left = j * cellSize
                val top = i * cellSize
                val right = left + cellSize
                val bottom = top + cellSize
                val paint = if (maze[i][j] == 1) wallPaint else pathPaint
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
            }
        }
    }

    private fun drawPlayer(canvas: Canvas) {
        val cellSize = width / maze[0].size
        val cx = (playerPosition.second * cellSize + cellSize / 2).toFloat()
        val cy = (playerPosition.first * cellSize + cellSize / 2).toFloat()
        canvas.drawCircle(cx, cy, playerRadius, playerPaint)
    }

    private fun drawAI(canvas: Canvas) {
        val cellSize = width / maze[0].size
        val cx = (aiPosition.second * cellSize + cellSize / 2).toFloat()
        val cy = (aiPosition.first * cellSize + cellSize / 2).toFloat()
        canvas.drawCircle(cx, cy, aiRadius, aiPaint)
    }

    fun movePlayer(dx: Int, dy: Int) {
        val newPos = Pair(playerPosition.first + dy, playerPosition.second + dx)
        if (isValidMove(newPos)) {
            playerPosition = newPos
            Log.d("MazeGame", "Player moved to: $newPos")
            invalidate()  // Ekranı yeniden çiz
            moveAI()  // Oyuncudan sonra AI'nin hamlesini yap
        } else {
            Log.d("MazeGame", "Invalid player move: $newPos is a wall")
        }
    }

    fun startGame() {
        Log.d("MazeGame", "Game started")
        findPathToTarget()
        if (aiSteps.isNotEmpty()) {
            aiPosition = aiSteps[currentAiStepIndex] // AI'nin ilk adımını ayarla
            Log.d("MazeGame", "AI starts at: $aiPosition")
            invalidate()  // Ekranı yeniden çiz
        } else {
            Log.d("MazeGame", "No path found for AI to target.")
        }
    }


    private fun moveAI() {
        if (aiSteps.isNotEmpty() && currentAiStepIndex < aiSteps.size) {
            // AI'nin hamlesinden önce 0.5 saniye bekle
            postDelayed({
                aiPosition = aiSteps[currentAiStepIndex]
                currentAiStepIndex++
                Log.d("MazeGame", "AI moved to: $aiPosition")
                invalidate()  // Ekranı yeniden çiz
            }, 500) // 500 ms gecikme
        } else {
            Log.d("MazeGame", "AI reached the target or has no moves left")
        }
    }

    private fun findPathToTarget() {
        val visited = mutableSetOf<Pair<Int, Int>>()
        val path = mutableListOf<Pair<Int, Int>>()
        if (dfs(aiPosition, visited, path)) {
            aiSteps = path.reversed()  // Hedefe ulaşma yolunu ters çevir
            Log.d("MazeGame", "Path to target: $aiSteps")
        } else {
            Log.d("MazeGame", "No path found to the target.")
        }
    }

    private fun dfs(position: Pair<Int, Int>, visited: MutableSet<Pair<Int, Int>>, path: MutableList<Pair<Int, Int>>): Boolean {
        if (position == targetPosition) {
            path.add(position)
            return true
        }

        val (x, y) = position
        if (!isValidMove(position) || visited.contains(position)) {
            return false
        }

        visited.add(position)
        path.add(position)

        val directions = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))
        for (direction in directions) {
            val newPos = Pair(x + direction.first, y + direction.second)
            if (dfs(newPos, visited, path)) {
                return true
            }
        }

        path.removeAt(path.size - 1) // Geri dönme
        return false
    }


    private fun isValidMove(position: Pair<Int, Int>): Boolean {
        val (x, y) = position
        return x in maze.indices && y in maze[0].indices && maze[x][y] == 0
    }
}
