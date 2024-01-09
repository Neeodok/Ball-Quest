package babo.qlest.mjest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import java.util.Random

class GameView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var background: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bg_game)
    private var ground: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.square_grass)
    private var tiga: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.game_character)
    private var rectBackground: Rect = Rect(0, 0, 0, 0)
    private var rectGround: Rect = Rect(0, 0, 0, 0)
    private var handler: Handler = Handler()
    private val UPDATE_MILLIS: Long = 30
    private var runnable: Runnable = Runnable { invalidate() }
    private var textPaint = Paint().apply {
        color = Color.WHITE
        textSize = TEXT_SIZE
        textAlign = Paint.Align.LEFT
        typeface = ResourcesCompat.getFont(context, R.font.fintik)
    }
    private var healthPaint = Paint().apply {
        color = Color.GREEN
    }
    private var points = 0
    private var life = 6
    private var random = Random()
    private var tigerX: Float = 0f
    private var tigerY: Float = 0f
    private var oldX = 0f
    private var oldTigerX = 0f
    private var suricane = ArrayList<Spike>()
    private var goldBags = ArrayList<GoldBag>()
    private var scoreToExtraLife = 200
    init {
        initScreenSize()
        initSpikes()
        initGoldBags()
    }

    private fun initGoldBags() {
        goldBags = ArrayList()
        repeat(3) {
            goldBags.add(GoldBag(context))
        }
    }

    private fun initScreenSize() {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        dWidth = size.x
        dHeight = size.y
        rectBackground = Rect(0, 0, dWidth, dHeight)
        rectGround = Rect(0, dHeight - ground.height, dWidth, dHeight)
        tigerX = (dWidth / 2 - tiga.width / 2).toFloat()
        tigerY = (dHeight - ground.height - tiga.height).toFloat()
    }

    private fun initSpikes() {
        suricane = ArrayList()
        repeat(2) {
            suricane.add(Spike(context))
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(background, null, rectBackground, null)
        canvas.drawBitmap(ground, null, rectGround, null)
        canvas.drawBitmap(tiga, tigerX, tigerY, null)

        suricane.forEach { spike ->
            canvas.drawBitmap(
                spike.getSpike(spike.spikeFrame)!!,
                spike.spikeX.toFloat(),
                spike.spikeY.toFloat(),
                null
            )
            spike.spikeFrame++
            if (spike.spikeFrame > 2) {
                spike.spikeFrame = 0
            }
            spike.spikeY += spike.spikeVelocity
            if (spike.spikeY + spike.spikeHeight >= dHeight - ground.height) {
                //points += 10
                scoreToExtraLife -= 10
                spike.resetPosition()
            }
        }

        goldBags.forEach { goldBag ->
            canvas.drawBitmap(
                goldBag.getBagGold(),
                goldBag.bagX.toFloat(),
                goldBag.bagY.toFloat(),
                null
            )

            goldBag.bagY += goldBag.bagVelocity
            if (goldBag.bagY + goldBag.bagHeight >= dHeight - ground.height) {
                goldBag.resetPosition()
            }

            if (isGoldBagCollision(goldBag, tigerX, tigerY, tiga.width, tiga.height)) {
                points += 10
                goldBag.resetPosition()
            }
        }

        suricane.forEach { spike ->
            if (isSpikeCollision(spike, tigerX, tigerY, tiga.width, tiga.height)) {
                life--
                spike.resetPosition()

                if (life == 0) {
                    val intent = Intent(context, GameOver::class.java)
                    intent.putExtra("points", points)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            }
        }
        if (scoreToExtraLife <= 0) {
            life++
            scoreToExtraLife = 200
        }


        when (life) {
            3 -> healthPaint.color = Color.YELLOW
            2 -> healthPaint.color = Color.RED
        }

        val healthBarWidth = 40 * life
        val healthBarLeft = dWidth - 200 - 100
        val healthBarTop = 30f + 20f
        val healthBarRight = healthBarLeft + healthBarWidth
        val healthBarBottom = 80f

        canvas.drawRect(
            healthBarLeft.toFloat(),
            healthBarTop,
            healthBarRight.toFloat(),
            healthBarBottom,
            healthPaint
        )


        val textPaintTopMargin = 50f
        val textPaintLeftMargin = 50f
        canvas.drawText("" + points, 20f + textPaintLeftMargin, TEXT_SIZE + textPaintTopMargin, textPaint)
        handler.postDelayed(runnable, UPDATE_MILLIS)

        goldBags.forEach { goldBag ->
            if (goldBag.bagY + goldBag.bagHeight >= dHeight - ground.height) {
                goldBag.resetPosition()
            }
        }
    }

    private fun isGoldBagCollision(goldBag: GoldBag, tigerX: Float, tigerY: Float, tigerWidth: Int, tigerHeight: Int): Boolean {
        val goldBagLeft = goldBag.bagX
        val goldBagRight = goldBagLeft + goldBag.bagWidth
        val goldBagTop = goldBag.bagY
        val goldBagBottom = goldBagTop + goldBag.bagHeight

        return tigerX < goldBagRight &&
                (tigerX + tigerWidth) > goldBagLeft &&
                tigerY < goldBagBottom &&
                (tigerY + tigerHeight) > goldBagTop
    }

    private fun isSpikeCollision(spike: Spike, tigerX: Float, tigerY: Float, tigerWidth: Int, tigerHeight: Int): Boolean {
        val spikeLeft = spike.spikeX
        val spikeRight = spikeLeft + spike.spikeWidth
        val spikeTop = spike.spikeY
        val spikeBottom = spikeTop + spike.spikeHeight

        return tigerX < spikeRight &&
                (tigerX + tigerWidth) > spikeLeft &&
                tigerY < spikeBottom &&
                (tigerY + tigerHeight) > spikeTop
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        if (touchY >= tigerY) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    oldX = event.x
                    oldTigerX = tigerX
                }
                MotionEvent.ACTION_MOVE -> {
                    val shift = oldX - touchX
                    val newRabbitX = oldTigerX - shift
                    tigerX = when {
                        newRabbitX <= 0 -> 0f
                        newRabbitX >= dWidth - tiga.width -> (dWidth - tiga.width).toFloat()
                        else -> newRabbitX
                    }
                }
            }
        }
        return true
    }

    companion object {
        const val TEXT_SIZE = 30f
        var dWidth: Int = 0
        var dHeight: Int = 0
    }
}
