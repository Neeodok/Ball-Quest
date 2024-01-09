package babo.qlest.mjest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import babo.qlest.mjest.databinding.MenuScreenBinding


class MenuAct : AppCompatActivity() {

    private val binding by lazy { MenuScreenBinding.inflate(layoutInflater) }
    private val btnGame by lazy {binding.btnStart  }
    private val btnBack by lazy {binding.exit  }
    private val btnPolina by lazy {binding.btnPoli  }
    private val knopkaBonus by lazy { binding.btnBonus }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        btnGame.setOnClickListener {
            animClickView(it,this)
            val intent = Intent(this@MenuAct, GameViewActivity::class.java)
            startActivity(intent)
        }



        btnBack.setOnClickListener {
            animClickView(it,this)
            showExitDialog(this@MenuAct)
        }
        btnPolina.setOnClickListener {
            openWebPage("https://sites.google.com/view/ballquest/")
        }
        knopkaBonus.setOnClickListener {
            val intent = Intent(this@MenuAct, CardGame::class.java)
            startActivity(intent)
        }
    }

    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No web browser found", Toast.LENGTH_SHORT).show()
        }
    }
}
