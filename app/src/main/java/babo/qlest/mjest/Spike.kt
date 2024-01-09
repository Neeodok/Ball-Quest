package babo.qlest.mjest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import java.util.Random

class Spike(context: Context) {
    private var spike = Array<Bitmap?>(3) { null }
    var spikeFrame = 0
    var spikeX = 0
    var spikeY = 0
    var spikeVelocity = 0
    private var random = Random()

    init {
        spike[0] = BitmapFactory.decodeResource(context.resources, R.drawable.suricane)
        spike[1] = BitmapFactory.decodeResource(context.resources, R.drawable.suricane)
        spike[2] = BitmapFactory.decodeResource(context.resources, R.drawable.suricane)
        val newWidth = (spike[0]!!.width / 1.3).toInt()
        val newHeight = (spike[0]!!.height / 1.3).toInt()
        spike[0] = Bitmap.createScaledBitmap(spike[0]!!, newWidth, newHeight, false)
        spike[1] = Bitmap.createScaledBitmap(spike[1]!!, newWidth, newHeight, false)
        spike[2] = Bitmap.createScaledBitmap(spike[2]!!, newWidth, newHeight, false)
        resetPosition()
    }

    fun getSpike(spikeFrame: Int): Bitmap? {
        return spike[spikeFrame]
    }

    val spikeWidth: Int
        get() = spike[0]!!.width
    val spikeHeight: Int
        get() = spike[0]!!.height

    fun resetPosition() {
        spikeX = when {
            GameView.dWidth > spikeWidth -> random.nextInt(GameView.dWidth - spikeWidth)
            else -> 0
        }
        spikeY = -200 - random.nextInt(600)
        spikeVelocity = 20 + random.nextInt(10)
    }
}
