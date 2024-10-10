package com.example.mazegamewithai

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mazeView: MazeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mazeView = findViewById(R.id.mazeView)

        // Hareket butonlarını tanımla
        val upButton: Button = findViewById(R.id.upButton)
        val downButton: Button = findViewById(R.id.downButton)
        val leftButton: Button = findViewById(R.id.leftButton)
        val rightButton: Button = findViewById(R.id.rightButton)

        // Oyuncunun hamleleri
        upButton.setOnClickListener {
            mazeView.movePlayer(0, -1)
        }
        downButton.setOnClickListener {
            mazeView.movePlayer(0, 1)
        }
        leftButton.setOnClickListener {
            mazeView.movePlayer(-1, 0)
        }
        rightButton.setOnClickListener {
            mazeView.movePlayer(1, 0)
        }

        // Oyunu başlat
        mazeView.startGame()
    }
}
