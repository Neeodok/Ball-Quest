package babo.qlest.mjest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Random

class GoldBag(context: Context) {
    private var bagGold: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ball_1)
    var bagX = 0
    var bagY = 0
    var bagVelocity = 0
    private var random = Random()

    init {
        resetPosition()
    }

    fun resetPosition() {
        if (GameView.dWidth > bagGold.width) {
            bagX = random.nextInt(GameView.dWidth - bagGold.width)
        } else {
            bagX = 0
        }
        bagY = -200 - random.nextInt(600)
        bagVelocity = 30 + random.nextInt(12)
    }

    fun getBagGold(): Bitmap {
        return bagGold
    }

    val bagWidth: Int
        get() = bagGold.width
    val bagHeight: Int
        get() = bagGold.height
}
