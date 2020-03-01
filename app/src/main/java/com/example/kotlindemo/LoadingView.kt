package com.example.kotlindemo

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.properties.Delegates


class LoadingView @JvmOverloads constructor( //在主构造方法前加@JvmOverloads constructor会自动生成其余参数较少的构造
    context: Context,
    attrs: AttributeSet? = null,    //这里和下面注意必须指定初值，否则通过构造方法无法加载布局
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mColor by Delegates.notNull<Int>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mWidth by Delegates.notNull<Int>()
    private var mHeight by Delegates.notNull<Int>()
    private var mCenterX by Delegates.notNull<Float>()
    private var mCenterY by Delegates.notNull<Float>()
    private var mCircleInterval = 50f
    private var mExpandCircleRadius = 20f
    private val mDuration: Long = 1000
    private lateinit var mAlphaAnimator: ValueAnimator
    private lateinit var mSizeAnimator: ValueAnimator
    private var mCircleAlpha = 150
    private var mCircleRadius = 10f

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mWidth = width
        mHeight = height
        mCenterX = (width / 2).toFloat()
        mCenterY = (height / 2).toFloat()
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        mColor = ta.getColor(0, R.styleable.LoadingView_color)
        ta.recycle()    //将typedArray清空，循环给下一个调用者使用
    }

    override fun onDraw(canvas: Canvas?) {
        paint.color = mColor
        canvas?.let {
            paint.alpha = mCircleAlpha
            it.drawCircle(mCenterX, mCenterY, mCircleRadius, paint)
            it.drawCircle(mCenterX - mCircleInterval, mCenterY, mCircleRadius, paint)
            it.drawCircle(mCenterX + mCircleInterval, mCenterY, mCircleRadius, paint)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mAlphaAnimator = ValueAnimator.ofFloat(mCircleAlpha.toFloat(), 255f, 50f)
        mSizeAnimator = ValueAnimator.ofFloat(mCircleRadius, mExpandCircleRadius, mCircleRadius)
        startAnim()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val mWidth = measuredWidth
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, 60) //wrap_content下默认高度为60，宽度为match_parent
        }
    }

    private val mAlphaUpdateListener = ValueAnimator.AnimatorUpdateListener { mAlphaAnimator ->
        mCircleAlpha = (mAlphaAnimator.animatedValue as Float).toInt()
        invalidate()
    }

    private val mSizeUpdateListener = ValueAnimator.AnimatorUpdateListener { mSizeAnimator ->
        mCircleRadius = mSizeAnimator.animatedValue as Float
        invalidate()
    }

    private fun startAnim() {
        with(mAlphaAnimator) {
            duration = mDuration
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener(mAlphaUpdateListener)
            interpolator = LinearInterpolator()
            start()
        }

        with(mSizeAnimator) {
            duration = mDuration
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener(mSizeUpdateListener)
            interpolator = LinearInterpolator()
            start()
        }
    }

    private fun stopAnim() {
        mAlphaAnimator.removeUpdateListener { mAlphaUpdateListener }
        mAlphaAnimator.removeAllUpdateListeners()
        mAlphaAnimator.cancel()
        mSizeAnimator.removeUpdateListener { mSizeUpdateListener }
        mSizeAnimator.removeAllUpdateListeners()
        mSizeAnimator.cancel()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnim()
    }
}



