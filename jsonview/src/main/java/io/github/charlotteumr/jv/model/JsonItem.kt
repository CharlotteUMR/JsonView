package io.github.charlotteumr.jv.model

import androidx.annotation.IntDef
import androidx.core.util.Predicate
import io.github.charlotteumr.jv.model.JsonItem.ValueType.Companion.TYPE_BOOLEAN
import io.github.charlotteumr.jv.model.JsonItem.ValueType.Companion.TYPE_JSON_ARRAY
import io.github.charlotteumr.jv.model.JsonItem.ValueType.Companion.TYPE_JSON_OBJECT
import io.github.charlotteumr.jv.model.JsonItem.ValueType.Companion.TYPE_NUMBER
import io.github.charlotteumr.jv.model.JsonItem.ValueType.Companion.TYPE_STRING
import io.github.charlotteumr.jv.model.JsonItem.ValueType.Companion.TYPE_UNKNOWN
import io.github.charlotteumr.jv.model.JsonItem.ValueType.Companion.TYPE_URL
import io.github.charlotteumr.jv.util.UrlUtil
import io.github.charlotteumr.jv.view.JsonItemView
import org.json.JSONArray
import org.json.JSONObject

/**
 * json项
 *
 * [level] 所在层级
 *
 * [index] 层级中的次序
 *
 * [size] 层级元素个数
 *
 * [key] jsonKey
 *
 * [value] jsonValue
 *
 * [extraUrlChecker] 额外的url检查器
 */
internal class JsonItem(
    val level: Int,
    var index: Int,
    private val size: Int,
    private val key: String,
    val value: Any?,
    private val extraUrlChecker: Predicate<String>
) : JsonItemView.IViewData {
    /**
     * 是否展开
     */
    var expand: Boolean = true

    /**
     * 收起的子元素
     */
    private val packedChildren = arrayListOf<JsonItem>()

    fun addPackedChild(child: JsonItem) {
        packedChildren.add(child)
    }

    fun popAllChildren(): ArrayList<JsonItem> {
        val clone = packedChildren.toList() as ArrayList<JsonItem>
        packedChildren.clear()
        return clone
    }

    override fun getIndentLevel(): Int = level

    override fun getKeyStr(): String = if (key.isNotBlank()) "\"$key\"" else ""

    override fun canShowSwitcher(): Boolean =
        ((value is JSONArray) or (value is JSONObject)) and (expand.not() or (index < 0))

    override fun isExpand(): Boolean = expand

    override fun getValueType(): Int {
        return when (value) {
            is JSONObject -> TYPE_JSON_OBJECT
            is JSONArray -> TYPE_JSON_ARRAY
            is String -> if (UrlUtil.urlPattern.matcher(value).matches()
                or (extraUrlChecker.test(value))
            ) TYPE_URL else TYPE_STRING
            is Number -> TYPE_NUMBER
            is Boolean -> TYPE_BOOLEAN
            else -> TYPE_UNKNOWN
        }
    }

    override fun getValueStr(): String {
        return when (value) {
            is JSONObject -> if (expand) if (index < 0) "{" else "}" else "Object{...}"
            is JSONArray -> if (expand) if (index < 0) "[" else "]" else "Array[${value.length()}]"
            is String -> "\"$value\""
            else -> "$value"
        }
    }

    override fun getChildCount(): Int {
        return when (value) {
            is JSONObject -> value.length()
            is JSONArray -> value.length()
            else -> 0
        }
    }

    override fun canShowConnector(): Boolean = (index >= 0) and (index < size - 1)

    /**
     * value类型
     *
     * [TYPE_JSON_OBJECT] [JSONObject]类型
     *
     * [TYPE_JSON_ARRAY] [JSONArray]类型
     *
     * [TYPE_STRING] [String]类型
     *
     * [TYPE_URL] url
     *
     * [TYPE_NUMBER] [Number]类型
     *
     * [TYPE_BOOLEAN] [Boolean]类型
     *
     * [TYPE_UNKNOWN] 未知类型
     */
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(
        TYPE_JSON_OBJECT,
        TYPE_JSON_ARRAY,
        TYPE_STRING,
        TYPE_URL,
        TYPE_NUMBER,
        TYPE_BOOLEAN,
        TYPE_UNKNOWN
    )
    annotation class ValueType {
        companion object {
            const val TYPE_JSON_OBJECT = 1
            const val TYPE_JSON_ARRAY = 2
            const val TYPE_STRING = 3
            const val TYPE_URL = 4
            const val TYPE_NUMBER = 5
            const val TYPE_BOOLEAN = 6
            const val TYPE_UNKNOWN = 7
        }
    }
}