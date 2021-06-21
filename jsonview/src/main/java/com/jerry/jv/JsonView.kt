package com.jerry.jv

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.layout_json_view.view.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * 用于展示json的控件
 *
 * @author Jerry
 */
class JsonView : ConstraintLayout {
    companion object {
        private const val TAG = "JsonView"
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context, attrs, defStyleAttr)
    }

    /**
     * 控制整体文字大小
     */
    var textSizePx: Int = 50
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    /**
     * 控制层级缩进
     */
    var levelIndent: Int = 4
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    /**
     * 默认文字颜色
     */
    @ColorInt
    var defaultTextColorInt: Int = 0xFF333333.toInt()
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    /**
     * jsonKey文字颜色
     */
    @ColorInt
    var keyTextColorInt: Int = 0xFF92278F.toInt()
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    /**
     * jsonValue如果为String时文字颜色
     */
    @ColorInt
    var stringTextColorInt: Int = 0xFF3AB54A.toInt()
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    /**
     * jsonValue如果为Url时文字颜色
     */
    @ColorInt
    var urlTextColorInt: Int = 0xFF61D2D6.toInt()
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    /**
     * jsonValue如果为数字时文字颜色
     */
    @ColorInt
    var numberTextColorInt: Int = 0xFF25AAE2.toInt()
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    /**
     * jsonValue如果为Boolean时文字颜色
     */
    @ColorInt
    var booleanTextColorInt: Int = 0xFFF98280.toInt()
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    /**
     * jsonValue如果为异常情况时文字颜色
     */
    @ColorInt
    var errorTextColorInt: Int = 0xFFF1592A.toInt()
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    /**
     * 搜索高亮背景色
     */
    @ColorInt
    var highlightBgColorInt: Int = 0xFFFFFF00.toInt()
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    /**
     * 搜索参数
     */
    var searchParam: SearchParam? = null
        set(value) {
            field = value
            jrv_interface.notifyDataSetChanged()
        }

    var onUrlClickListener: OnUrlClickListener? = null

    /**
     * 填充数据源
     *
     * [JSONObject]或[JSONArray]或[String]类型
     */
    fun setData(data: Any?) {
        jrv_interface.setData(data)
    }

    init {
        View.inflate(context, R.layout.layout_json_view, this)
        jrv_interface.root = this
    }

    /**
     * 初始化属性
     */
    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.JsonView)
        textSizePx =
            typedArray.getDimensionPixelSize(R.styleable.JsonView_jrv_text_size, textSizePx)
        levelIndent = typedArray.getInt(R.styleable.JsonView_jrv_level_indent, levelIndent)
        defaultTextColorInt = typedArray.getColor(
            R.styleable.JsonView_jrv_default_text_color,
            defaultTextColorInt
        )
        keyTextColorInt =
            typedArray.getColor(R.styleable.JsonView_jrv_key_text_color, keyTextColorInt)
        stringTextColorInt = typedArray.getColor(
            R.styleable.JsonView_jrv_string_text_color,
            stringTextColorInt
        )
        urlTextColorInt =
            typedArray.getColor(R.styleable.JsonView_jrv_url_text_color, urlTextColorInt)
        numberTextColorInt = typedArray.getColor(
            R.styleable.JsonView_jrv_number_text_color,
            numberTextColorInt
        )
        booleanTextColorInt = typedArray.getColor(
            R.styleable.JsonView_jrv_boolean_text_color,
            booleanTextColorInt
        )
        errorTextColorInt = typedArray.getColor(
            R.styleable.JsonView_jrv_error_text_color,
            errorTextColorInt
        )
        highlightBgColorInt = typedArray.getColor(
            R.styleable.JsonView_jrv_highlight_bg_color,
            highlightBgColorInt
        )
        typedArray.recycle()
    }

    /**
     * 搜索参数
     *
     * [searchKey] 搜索关键词
     *
     * [ignoreCase] 是否忽略大小写
     */
    class SearchParam(val searchKey: String, val ignoreCase: Boolean = false)

    /**
     * url点击事件监听
     */
    interface OnUrlClickListener {
        /**
         * 点击url的回调
         *
         * @param url 点击的url
         */
        fun onUrlClick(url: String)
    }
}