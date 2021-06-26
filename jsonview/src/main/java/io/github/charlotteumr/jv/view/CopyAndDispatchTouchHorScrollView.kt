package io.github.charlotteumr.jv.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import androidx.core.view.children

/**
 * 复制并分发触摸事件给子控件的横向滚动控件
 *
 * [HorizontalScrollView]只有一个子控件
 *
 * @author Jerry
 */
internal class CopyAndDispatchTouchHorScrollView : HorizontalScrollView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        // 将事件传递给第一个子控件
        children.first().dispatchTouchEvent(MotionEvent.obtain(ev))
        return super.onTouchEvent(ev)
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        // 使禁止拦截触摸事件失效
    }
}