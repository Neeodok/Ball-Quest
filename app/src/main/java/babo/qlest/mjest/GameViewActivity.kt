package babo.qlest.mjest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GameViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameView = GameView(this@GameViewActivity, null)
        setContentView(gameView)
    }
}
