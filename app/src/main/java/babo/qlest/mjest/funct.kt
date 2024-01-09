package babo.qlest.mjest

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

fun animClickView(view: View, context: Context){

    (AnimatorInflater.loadAnimator(
        context,
        R.animator.animpulse
    ) as AnimatorSet).apply {
        setTarget(view)
        start()
    }

}
fun showExitDialog(context: Context) {
    AlertDialog.Builder(context)
        .setMessage("Close Application?")
        .setCancelable(false)
        .setPositiveButton("Yes") { _, _ ->
            (context as? AppCompatActivity)?.finishAndRemoveTask()
        }
        .setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

fun pulseBtnAnimation(view: View) {
    val animScaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.95f, 1.1f)
    animScaleX.apply {
        duration = 700
        interpolator = AccelerateDecelerateInterpolator()
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.REVERSE
    }
    val animScaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.95f, 1.1f)
    animScaleY.apply {
        duration = 700
        interpolator = AccelerateDecelerateInterpolator()
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.REVERSE
    }
    animScaleX.start()
    animScaleY.start()
}
fun winAnim(view: View, context: Context){

    (AnimatorInflater.loadAnimator(
        context,
        R.animator.winanim
    ) as AnimatorSet).apply {
        setTarget(view)
        start()
    }

}
