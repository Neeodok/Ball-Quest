package babo.qlest.mjest


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import babo.qlest.mjest.databinding.SpScreenBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadAct:AppCompatActivity() {
    private val binding by lazy { SpScreenBinding.inflate(layoutInflater) }
    private val splashDuration: Long = 3000

    private lateinit var firebaseAnalytics: FirebaseAnalytics


   private val animationSplash by lazy { binding.loadd}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        firebaseAnalytics = Firebase.analytics
        animationSplash.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            delay(splashDuration)
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val mainIntent = Intent(this@LoadAct, MenuAct::class.java)
        startActivity(mainIntent)
        animationSplash.pauseAnimation()
        finish()
    }
}