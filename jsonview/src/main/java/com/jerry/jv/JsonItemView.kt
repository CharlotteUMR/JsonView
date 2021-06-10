package com.jerry.jv

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jerry.jv.JsonItem.ValueType
import kotlinx.android.synthetic.main.item_json_view.view.*

/**
 * 用于展示每一行Json的View
 *
 * @author Jerry
 */
@SuppressLint("ViewConstructor")
internal class JsonItemView(private val container: JsonRecyclerView) :
    ConstraintLayout(container.context) {
    companion object {
        private const val TAG = "JsonItemView"
    }

    private val strBuilder = StringBuilder()

    init {
        View.inflate(context, R.layout.item_json_view, this)
        layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    fun setViewData(viewData: IViewData) {
        val textSizeFloat = container.textSizePx.toFloat()

        // 缩进
        tv_indent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeFloat)
        strBuilder.clear()
        for (i in 0 until viewData.getIndentLevel().times(container.levelIndent)) {
            strBuilder.append(' ')
        }
        tv_indent.text = strBuilder.toString()

        // key
        if (viewData.getKeyStr().isNotBlank()) {
            tv_key.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeFloat)
            tv_key.setTextColor(container.keyTextColorInt)
            tv_key.text = viewData.getKeyStr()
            // 冒号
            tv_joiner.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeFloat)
            tv_joiner.setTextColor(container.defaultTextColorInt)
            group_key.visibility = View.VISIBLE
        } else {
            group_key.visibility = View.GONE
        }

        // 收起展开开关
        if (viewData.canShowSwitcher()) {
            if (viewData.isExpand()) {
                jsv_switcher.expand()
            } else {
                jsv_switcher.collapse()
            }
            jsv_switcher.visibility = View.VISIBLE
        } else {
            jsv_switcher.visibility = View.GONE
        }

        // value
        tv_value.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeFloat)
        tv_value.setTextColor(
            when (viewData.getValueType()) {
                ValueType.TYPE_JSON_ARRAY, ValueType.TYPE_JSON_OBJECT -> container.defaultTextColorInt
                ValueType.TYPE_STRING -> container.stringTextColorInt
                ValueType.TYPE_URL -> container.urlTextColorInt
                ValueType.TYPE_NUMBER -> container.numberTextColorInt
                ValueType.TYPE_BOOLEAN -> container.booleanTextColorInt
                else -> container.errorTextColorInt
            }
        )
        tv_value.text = viewData.getValueStr()

        // 逗号
        if (viewData.canShowEnd()) {
            tv_end.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeFloat)
            tv_end.setTextColor(container.defaultTextColorInt)
            tv_end.visibility = View.VISIBLE
        } else {
            tv_end.visibility = View.GONE
        }
    }

    interface IViewData {
        fun getIndentLevel(): Int
        fun getKeyStr(): String
        fun canShowSwitcher(): Boolean
        fun isExpand(): Boolean

        @ValueType
        fun getValueType(): Int
        fun getValueStr(): String
        fun canShowEnd(): Boolean
    }
}