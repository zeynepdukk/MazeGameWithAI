package com.example.mazegamewithai

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mazeView: MazeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mazeView = findViewById(R.id.mazeView)
        mazeView.startGame()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_W -> {
                mazeView.movePlayer(0, -1)
                true
            }
            KeyEvent.KEYCODE_S -> {
                mazeView.movePlayer(0, 1)
                true
            }
            KeyEvent.KEYCODE_A -> {
                mazeView.movePlayer(-1, 0)
                true
            }
            KeyEvent.KEYCODE_D -> {
                mazeView.movePlayer(1, 0)
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyUp(keyCode, event)
    }
}
