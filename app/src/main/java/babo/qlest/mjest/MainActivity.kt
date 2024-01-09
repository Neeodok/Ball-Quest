package babo.qlest.mjest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import babo.qlest.mjest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
        private val bindi by lazy { ActivityMainBinding.inflate(layoutInflater) }
        private val knopka by lazy { bindi.startGameButton }
        private val knopkaBonus by lazy { bindi.btnBonus }
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindi.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        pulseBtnAnimation(knopka)

        knopka.setOnClickListener {
            val intent = Intent(this@MainActivity, GameViewActivity::class.java).addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            startActivity(intent)
        }
            knopkaBonus.setOnClickListener {
                val intent = Intent(this@MainActivity, CardGame::class.java).addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                            or Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
                startActivity(intent)
            }
    }

    fun startGame(view: View?) {
        val intent = Intent(this@MainActivity, GameViewActivity::class.java)
        startActivity(intent)
    }
   
}
