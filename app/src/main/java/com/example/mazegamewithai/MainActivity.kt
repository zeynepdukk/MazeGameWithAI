package com.example.mazegamewithai

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mazeView: MazeView


    /*
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

     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mazeView = findViewById(R.id.mazeView)

        // Oyunu başlat
        mazeView.startGame()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_W -> {
                mazeView.movePlayer(0, -1) // Yukarı
                true
            }
            KeyEvent.KEYCODE_S -> {
                mazeView.movePlayer(0, 1) // Aşağı
                true
            }
            KeyEvent.KEYCODE_A -> {
                mazeView.movePlayer(-1, 0) // Sol
                true
            }
            KeyEvent.KEYCODE_D -> {
                mazeView.movePlayer(1, 0) // Sağ
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyUp(keyCode, event)
    }
}
