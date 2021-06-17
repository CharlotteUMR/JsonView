package com.jerry.jv

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.text.color
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
    private val spanStrBuilder = SpannableStringBuilder()

    init {
        View.inflate(context, R.layout.item_json_view, this)
        layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    fun setViewData(viewData: IViewData) {
        if (viewData.canShow().not()) {
            layoutParams = RecyclerView.LayoutParams(0, 0)
            visibility = GONE
            return
        }
        layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        visibility = VISIBLE

        val textSizeFloat = container.textSizePx.toFloat()
        val searchKey =
            container.searchParam?.searchKey?.let { if (it.isNotBlank()) it else null }
        val ignoreCase = container.searchParam?.ignoreCase ?: false

        // 缩进
        tv_indent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeFloat)
        strBuilder.clear()
        for (i in 0 until viewData.getIndentLevel().times(container.levelIndent)) {
            strBuilder.append(' ')
        }
        tv_indent.text = strBuilder.toString()

        // key
        spanStrBuilder.clear()
        if (viewData.getKeyStr().isNotBlank()) {
            tv_key.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeFloat)
            tv_key.setTextColor(container.keyTextColorInt)
            findAndSpanStr(
                spanStrBuilder.append(viewData.getKeyStr()),
                searchKey,
                ignoreCase,
                BackgroundColorSpan(container.highlightBgColorInt)
            )
            tv_key.text = spanStrBuilder
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
        val valueTextColor = when (viewData.getValueType()) {
            ValueType.TYPE_JSON_ARRAY, ValueType.TYPE_JSON_OBJECT -> container.defaultTextColorInt
            ValueType.TYPE_STRING -> container.stringTextColorInt
            ValueType.TYPE_URL -> container.urlTextColorInt
            ValueType.TYPE_NUMBER -> container.numberTextColorInt
            ValueType.TYPE_BOOLEAN -> container.booleanTextColorInt
            else -> container.errorTextColorInt
        }
        spanStrBuilder.clear()
        spanStrBuilder.color(valueTextColor) {
            append(viewData.getValueStr())
            findAndSpanStr(
                this,
                searchKey,
                ignoreCase,
                BackgroundColorSpan(container.highlightBgColorInt)
            )
        }

        if (viewData.isExpand().not() && viewData.getValueType() == ValueType.TYPE_JSON_ARRAY) {
            // 如果是JsonArray并且收起，则数字高亮
            val childCountStr = viewData.getChildCount().toString()
            findAndSpanStr(
                spanStrBuilder,
                childCountStr,
                ignoreCase,
                ForegroundColorSpan(container.numberTextColorInt)
            )
        }

        if (viewData.canShowEnd()) {
            // 增加逗号
            spanStrBuilder.color(container.defaultTextColorInt) { append(",") }
        }
        tv_value.text = spanStrBuilder

        ConstraintSet().apply {
            clone(this@JsonItemView)
            val lineHeight = tv_value.lineHeight
            constrainWidth(R.id.jsv_switcher, lineHeight / 2)
            constrainHeight(R.id.jsv_switcher, lineHeight / 2)
            applyTo(this@JsonItemView)
        }
    }

    /**
     * 在[source]中查找[key]并设置[span]
     *
     * @param source 源数据
     * @param key 要查找的文本
     * @param span 要给查找到文本设置的效果
     *
     * @return 设置效果后的文本
     */
    private fun findAndSpanStr(
        source: SpannableStringBuilder,
        key: String? = null,
        ignoreCase: Boolean = false,
        span: CharacterStyle
    ) {
        key ?: return
        var startIndex = source.indexOf(string = key, ignoreCase = ignoreCase)
        while (startIndex >= 0) {
            source.setSpan(
                CharacterStyle.wrap(span),
                startIndex,
                startIndex.plus(key.length),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            startIndex = source.indexOf(key, startIndex + 1, ignoreCase)
        }
    }

    interface IViewData {
        fun canShow(): Boolean
        fun getIndentLevel(): Int
        fun getKeyStr(): String
        fun canShowSwitcher(): Boolean
        fun isExpand(): Boolean

        @ValueType
        fun getValueType(): Int
        fun getValueStr(): String
        fun getChildCount(): Int
        fun canShowEnd(): Boolean
    }
}