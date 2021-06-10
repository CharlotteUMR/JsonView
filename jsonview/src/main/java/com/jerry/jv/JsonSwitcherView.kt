package com.jerry.jv

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px

/**
 * Json展开收起开关View
 *
 * @author Jerry
 */
internal class JsonSwitcherView : View {
    companion object {
        private const val TAG = "JsonSwitcherView"
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     * View的大小
     */
    private val viewRect = RectF()

    /**
     * 绘制开关的画笔
     */
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var onOrOff = false

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.color = Color.BLACK
    }

    fun setColor(@ColorInt color: Int) {
        paint.color = color
    }

    fun setStrokeWidth(@Px width: Int) {
        paint.strokeWidth = width.toFloat()
    }

    /**
     * 展开
     */
    fun expand() {
        if (onOrOff.not()) {
            onOrOff = true
            postInvalidate()
        }
    }

    /**
     * 收起
     */
    fun collapse() {
        if (onOrOff) {
            onOrOff = false
            postInvalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            // 绘制边框
            viewRect.set(0f, 0f, width.toFloat(), height.toFloat())
            viewRect.inset(paint.strokeWidth / 2, paint.strokeWidth / 2)
            drawRect(viewRect, paint)

            // 绘制横线
            viewRect.inset(viewRect.width() / 4, viewRect.height() / 4)
            drawLine(viewRect.left, viewRect.centerY(), viewRect.right, viewRect.centerY(), paint)
            if (onOrOff.not()) {
                // 如果收起状态，绘制竖线
                drawLine(
                    viewRect.centerX(),
                    viewRect.top,
                    viewRect.centerX(),
                    viewRect.bottom,
                    paint
                )
            }
        }
    }
}