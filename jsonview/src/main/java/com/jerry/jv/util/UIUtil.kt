package com.jerry.jv.util

import android.content.Context
import android.util.TypedValue

/**
 * UI工具类
 *
 * @author Jerry
 */
internal class UIUtil {
    companion object {
        private const val TAG = "UIUtil"

        /**
         * dp 转 px 工具
         *
         * @param context 上下文
         * @param dipValue dp 值
         * @return px 值
         */
        fun dp2px(context: Context, dipValue: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dipValue,
                context.resources.displayMetrics
            ).toInt()
        }
    }
}