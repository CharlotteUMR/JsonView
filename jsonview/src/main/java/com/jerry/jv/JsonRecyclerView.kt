package com.jerry.jv

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

/**
 * 用于展示Json的View
 *
 * @author Jerry
 */
class JsonRecyclerView : RecyclerView {
    companion object {
        private const val TAG = "JsonView"
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     * 控制整体文本大小
     */
    var textSizePx: Int = 50
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    /**
     * 控制层级缩进
     */
    var levelIndent: Int = 4
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    /**
     * 各种文本颜色
     */
    @ColorInt
    var defaultTextColorInt: Int = 0xFF333333.toInt()
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    @ColorInt
    var keyTextColorInt: Int = 0xFF92278F.toInt()
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    @ColorInt
    var stringTextColorInt: Int = 0xFF3AB54A.toInt()
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    @ColorInt
    var urlTextColorInt: Int = 0xFF61D2D6.toInt()
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    @ColorInt
    var numberTextColorInt: Int = 0xFF25AAE2.toInt()
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    @ColorInt
    var booleanTextColorInt: Int = 0xFFF98280.toInt()
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    @ColorInt
    var errorTextColorInt: Int = 0xFFF1592A.toInt()
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    @ColorInt
    var highlightBgColorInt: Int = 0xFFFFFF00.toInt()
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    /**
     * 搜索参数
     */
    var searchParam: SearchParam? = null
        set(value) {
            field = value
            adapter.notifyDataSetChanged()
        }

    private val adapter = JsonRecyclerAdapter()

    init {
        layoutManager = LinearLayoutManager(context)
        setAdapter(adapter)
    }

    /**
     * 填充数据源
     *
     * [JSONObject]或[JSONArray]或[String]类型
     */
    fun setData(data: Any?) {
        adapter.setData(data)
    }

    /**
     * 搜索参数
     *
     * [searchKey] 搜索关键词
     *
     * [ignoreCase] 是否忽略大小写
     */
    class SearchParam(val searchKey: String, val ignoreCase: Boolean = false)
}