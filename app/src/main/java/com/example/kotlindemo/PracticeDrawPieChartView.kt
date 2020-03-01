package com.example.kotlindemo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

//如果主构造中有N个参数，在类名后主构造前加@JvmOverloads注解就可以让编译器自动生成参数个数为1~N-1的构造
class PracticeDrawPieChartView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    fun View.dip2Px(dpValue: Float): Float {
        val scale = this.context.resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }

    private val radiusX = dip2Px(150f)
    private val radiusY = dip2Px(150f)
    private val radius = dip2Px(100f)
    private val oval = RectF(dip2Px(50f), dip2Px(50f), dip2Px(250f), dip2Px(250f))
    private val ta =
        context.obtainStyledAttributes(attributeSet, R.styleable.PracticeDrawPieChartView)
    private val mColor =
        ta.getColor(R.styleable.PracticeDrawPieChartView_pie_background, Color.parseColor("#333333"))


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(mColor)    //绘制背景
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.save()               //保存画笔初始状态
        canvas.translate(-dip2Px(2f), dip2Px(2f))   //将画布的坐标原点向右方向移动x，向下方向移动y
        paint.color = Color.BLUE
        canvas.drawArc(oval, 80f, 105f, true, paint)
        canvas.restore()        //恢复之前画笔的状态
        canvas.save()           //再将开始的状态保存
        canvas.translate(-dip2Px(2f), -dip2Px(2f))
        paint.color = Color.RED
        canvas.drawArc(oval, 185f, 120f, true, paint)
        canvas.restore()
        canvas.save()
        paint.color = Color.YELLOW
        canvas.drawArc(oval, 305f, 55f, true, paint)
        canvas.restore()
        canvas.save()
        canvas.translate(dip2Px(1f), dip2Px(1f))
        paint.color = Color.GRAY
        canvas.drawArc(oval, 0f, 10f, true, paint)
        canvas.restore()
        canvas.save()
        paint.color = Color.DKGRAY
        canvas.drawArc(oval, 10f, 10f, true, paint)
        canvas.restore()
        canvas.save()
        canvas.translate(dip2Px(2f), dip2Px(2f))
        paint.color = Color.LTGRAY
        canvas.drawArc(oval, 20f, 60f, true, paint)
        canvas.restore()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defWidth = 800
        val defHeight = 800
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defWidth, defHeight)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defWidth, heightSpecSize)
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, defHeight)
        }
    }


}