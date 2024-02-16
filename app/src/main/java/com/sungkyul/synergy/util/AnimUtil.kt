package com.sungkyul.synergy.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.ScaleAnimation
import androidx.core.view.updateLayoutParams

class AnimUtil {
    companion object {
        // (duration)ms 동안 drawable의 알파 값이 startAlpha에서 endAlpha로 바뀌는 함수
        // startAlpha, endAlpha의 범위는 [0, 255]이다.
        fun startAlphaAnimation(
            drawable: Drawable,
            duration: Long,
            startAlpha: Int,
            endAlpha: Int
        ) {
            val alphaAnimator = ObjectAnimator.ofInt(
                drawable,
                "alpha",
                startAlpha,
                endAlpha
            )
            alphaAnimator.duration = duration
            alphaAnimator.start()
        }

        // (duration×2)ms 동안 drawable의 알파 값이 startAlpha → endAlpha → startAlpha로 바뀌는 함수
        // startAlpha, endAlpha의 범위는 [0, 255]이다.
        fun startAlphaAnimation2(
            drawable: Drawable,
            duration: Long,
            startAlpha: Int,
            endAlpha: Int
        ) {
            val animator1 = ObjectAnimator.ofInt(
                drawable,
                "alpha",
                startAlpha,
                endAlpha
            )
            val animator2 = ObjectAnimator.ofInt(
                drawable,
                "alpha",
                endAlpha,
                startAlpha
            )

            animator1.duration = duration
            animator2.duration = duration

            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(animator1, animator2)
            animatorSet.start()
        }

        // (duration)ms 동안 view의 스케일이 startScale에서 endScale로 바뀌는 함수
        // startScale, endScale의 범위는 [0.0f, 1.0f]이다.
        fun startScaleAnimation(
            view: View,
            duration: Long,
            startScale: Float,
            endScale: Float
        ) {
            // Ease-Out
            val interpolator = DecelerateInterpolator()

            val animation = ScaleAnimation(
                startScale, endScale,
                startScale, endScale,
                Animation.RELATIVE_TO_SELF, 0.5f, // 중심점 X
                Animation.RELATIVE_TO_SELF, 0.5f // 중심점 Y
            )
            animation.duration = duration
            animation.interpolator = interpolator
            //animation.fillAfter = true // 애니메이션 종료 후에도 유지시킨다. (주의: View.GONE을 해도 안 사라지는 문제점이 있다.)
            view.startAnimation(animation)
        }

        // (duration)ms 동안 view의 layout_height가 startHeight에서 endHeight로 바뀌는 함수
        // startScale, endScale의 범위는 [0.0f, 1.0f]이다.
        fun startHeightAnimation(
            view: View,
            duration: Long,
            startHeight: Int,
            endHeight: Int
        ) {
            // Ease-Out
            val interpolator = DecelerateInterpolator()

            val animator = ValueAnimator.ofInt(startHeight, endHeight)
            animator.addUpdateListener {
                view.updateLayoutParams {
                    height = it.animatedValue as Int
                }
            }

            animator.duration = duration
            animator.interpolator = interpolator
            animator.start()
        }

        // ObjectAnimator.ofFloat를 편하게 사용하려고 만든 함수
        fun startObjectAnimatorOfFloat(view: View, target: String, value1: Float, value2: Float, duration: Long, interpolator: Interpolator) {
            ObjectAnimator.ofFloat(view, target, value1, value2).apply {
                this.duration = duration
                this.interpolator = interpolator
                start()
            }
        }

        // ValueAnimator.ofFloat를 편하게 사용하려고 만든 함수
        fun startValueAnimatorOfFloat(update: (Float) -> Unit, value1: Float, value2: Float, duration: Long, interpolator: Interpolator) {
            val animator = ValueAnimator.ofFloat(value1, value2)
            animator.addUpdateListener {
                update(it.animatedValue as Float)
            }
            animator.duration = duration
            animator.interpolator = interpolator
            animator.start()
        }

        // dp에서 px로 바꾸는 함수
        fun dpToPx(context: Context, dp: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
        }

        fun getScreenDimensions(context: Context): Pair<Int, Int> {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()

            // 현재 활성화된 디스플레이의 메트릭스 정보 가져오기
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            // 가로와 세로 길이를 px 단위로 반환
            val screenWidthPx = displayMetrics.widthPixels
            val screenHeightPx = displayMetrics.heightPixels

            return Pair(screenWidthPx, screenHeightPx)
        }
    }
}
