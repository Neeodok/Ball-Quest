package babo.qlest.mjest

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import babo.qlest.mjest.databinding.CardSceennsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class CardGame: AppCompatActivity() {
    private val binding by lazy { CardSceennsBinding.inflate(layoutInflater) }
    private val cards =
        listOf(
            R.drawable.card_1,
            R.drawable.card_2,
            R.drawable.card_3,
            R.drawable.card_4,
        )

    private val counts = listOf(
        R.drawable.number1,
        R.drawable.number2,
        R.drawable.number3,

    )
    private var bank = 1000
    private var bet = 50
    private val tvBank by lazy { binding.tvBank }
    private val tvBet by lazy { binding.tvBet }
    private val btnBetUp by lazy { binding.btnBetUp }
    private val btnBetDown by lazy { binding.btnBetDown }
    private val animLottie by lazy { binding.animationWinLottie }
    private var isClickable = false
    private var cardId = 0
    private val famousCard by lazy { binding.famousCard }
    private val cardsListUp by lazy { binding.btnCardsListUp }
    private val cardsListDown by lazy { binding.btnCardsListDown }
    private val btnstart by lazy { binding.btnStart }
    private val tvCount by lazy { binding.tvCount }

    private val winningCardIndices = mutableListOf<Int>()

    private val iconFront by lazy {
        listOf(
            binding.unknownCard,
            binding.unknownCard1,
            binding.unknownCard2
        )
    }

    private val iconBack by lazy { listOf(binding.card, binding.card1, binding.card2) }
    private val indexes = mutableListOf<Int>(0, 0, 0)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tvBank.text = bank.toString()
        tvBet.text = bet.toString()



        cardsListUp.setOnClickListener {

            if (cardId < cards.size - 1) {
                cardId++
            } else {
                cardId = 0
            }
            famousCard.setBackgroundResource(cards[cardId])
            animClickView(it, this)

        }
        cardsListDown.setOnClickListener {
            if (cardId == 0) {
                cardId = cards.size - 1
            } else {
                cardId--
            }
            famousCard.setBackgroundResource(cards[cardId])
            animClickView(it, this)

        }
        btnBetUp.setOnClickListener {
            if (bet<bank){
                bet += 50
                tvBet.text=bet.toString()
                animClickView(it, this)


            }
        }

        btnBetDown.setOnClickListener {
            if (bet>50){
                bet -= 50
                tvBet.text=bet.toString()
                animClickView(it, this)


            }
        }



        btnstart.setOnClickListener {
            onOffButtons()
            CoroutineScope(Dispatchers.Main).launch {
                tvCount.visibility = View.VISIBLE
                for (i in counts.size - 1  downTo 0) {
                    tvCount.setBackgroundResource(counts[i])
                    delay(1000)
                }
                tvCount.visibility = View.INVISIBLE

                winningCardIndices.clear()

                for (i in 0..2) {
                    val id = Random.nextInt(cards.size)
                    indexes[i] = id
                    iconFront[i].setBackgroundResource(cards[id])
                    flipCard(this@CardGame, iconFront[i], iconBack[i])

                    winningCardIndices.add(id)

                    delay(3000)
                    if (winningCardIndices.contains(cardId)) {
                        if (cardId == id) {
                            showWin()
                        } else {
                            showLose()
                        }
                    }
                }


                delay(2000)
                for (i in 0..2) {


                    flipCard(this@CardGame, iconBack[i], iconFront[i])

                    delay(1000)

                }
                onOffButtons()

            }
        }


    }


    private fun showLose() {

    }

    private fun showWin() {
        CoroutineScope(Dispatchers.Main).launch {
            bank += bet * 5
            tvBank.text = bank.toString()
            animClickView(tvBank, this@CardGame)
            animLottie.visibility = View.VISIBLE
            delay(5000)
            animLottie.visibility = View.INVISIBLE
        }
    }


    private fun onOffButtons() {
        cardsListUp.isClickable = isClickable
        cardsListDown.isClickable = isClickable
        btnstart.isClickable = isClickable
        binding.btnBetDown.isClickable = isClickable
        binding.btnBetUp.isClickable = isClickable
        isClickable = !isClickable
    }
    private fun flipCard(context: Context, visibleView: View, inVisibleView: View) {
        try {

            val scale = context.resources.displayMetrics.density
            val cameraDist = 8000 * scale
            visibleView.cameraDistance = cameraDist
            inVisibleView.cameraDistance = cameraDist

            val flipOutAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_out
                ) as AnimatorSet
            flipOutAnimatorSet.setTarget(inVisibleView)

            val flipInAnimationSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_in
                ) as AnimatorSet
            flipInAnimationSet.setTarget(visibleView)

            flipOutAnimatorSet.start()
            flipInAnimationSet.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
