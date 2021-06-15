package com.jerry.jv

import android.graphics.Paint
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * Json列表适配器
 *
 * @author Jerry
 */
internal class JsonRecyclerAdapter : RecyclerView.Adapter<JsonRecyclerAdapter.JsonViewHolder>() {
    companion object {
        private const val TAG = "JsonRecyclerAdapter"
    }

    private val itemList = arrayListOf<JsonItem>()
    private var jsonRecyclerView: JsonRecyclerView? = null
    private val measurePaint = Paint()
    private val strBuilder = StringBuilder()

    init {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                if (jsonRecyclerView?.layoutParams?.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    measureMinWidth()
                } else {
                    jsonRecyclerView?.minimumWidth = 0
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JsonViewHolder =
        JsonViewHolder(JsonItemView(parent as JsonRecyclerView))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: JsonViewHolder, position: Int) {
        val curItem = itemList[position]
        holder.jsonItemView.setViewData(curItem)
        holder.jsonItemView.setOnClickListener {
            if (curItem.canShowSwitcher()) {
                // 如果可以展示开关
                if (curItem.expand) {
                    // 如果之前是展开的则收起
                    // 将这个item下的所有item都隐藏
                    var i = position + 1
                    while (itemList[i].value != curItem.value) {
                        itemList[i].hide = true
                        i++
                    }
                    // 将这个item的结尾隐藏
                    itemList[i].hide = true
                    // 将这个item的开头变为收起态（这里保留开头的原因是结尾没有key）
                    curItem.index = itemList[i].index
                    curItem.expand = false
                } else {
                    // 如果之前是收起的则展开
                    // 将这个item下所有未收起的item都显示
                    var i = position + 1
                    var keepHideValue: Any? = null
                    while (itemList[i].value != curItem.value) {
                        val item = itemList[i]
                        if (keepHideValue == null) {
                            if (item.canShowSwitcher() && item.expand.not()) {
                                // 如果item有开关且处于收起状态，则其下所有item保持隐藏
                                keepHideValue = item.value
                            }
                            item.hide = false
                        } else if (item.value == keepHideValue) {
                            // 保持隐藏结束
                            keepHideValue = null
                        }
                        i++
                    }
                    // 将这个item的结尾显示
                    itemList[i].hide = false
                    // 将这个item的开头变为展开态
                    curItem.index = -1
                    curItem.expand = true
                }
                // 刷新
                notifyDataSetChanged()
            }
        }
    }

    /**
     * 计算RecyclerView的最小宽度（相当于所有item的最大宽度）
     */
    private fun measureMinWidth() {
        jsonRecyclerView?.let {
            var itemMaxWidth = 0
            measurePaint.textSize = it.textSizePx.toFloat()
            for (jsonItem in itemList) {
                strBuilder.clear()
                for (i in 0 until (jsonItem.level * it.levelIndent)) {
                    strBuilder.append(' ')
                }
                strBuilder.append(jsonItem.getKeyStr()).append(": 开").append(jsonItem.getValueStr())
                    .append(",")
                val width =
                    measurePaint.measureText(strBuilder.toString()) + UIUtil.dp2px(it.context, 13f)
                if (width > itemMaxWidth) {
                    itemMaxWidth = width.toInt()
                }
            }
            it.minimumWidth = itemMaxWidth
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        jsonRecyclerView = recyclerView as JsonRecyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        jsonRecyclerView = null
    }

    /**
     * 填充数据源
     *
     * [JSONObject]或[JSONArray]或[String]类型
     */
    fun setData(data: Any?) {
        data ?: return

        // 清空
        itemList.clear()

        when (data) {
            is JSONObject -> handleJsonObject(data)
            is JSONArray -> handleJsonArray(data)
            is String -> try {
                when (val jsonData = JSONTokener(data).nextValue()) {
                    is JSONObject -> handleJsonObject(jsonData)
                    is JSONArray -> handleJsonArray(jsonData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            else -> Log.e(TAG, "setData: invalid data type")
        }

        // 刷新
        notifyDataSetChanged()
    }

    /**
     * 将数据处理为item
     *
     * @param value jsonValue
     * @param key jsonKey
     * @param level item所在的层级（处理缩进）
     * @param index item在所在层级的次序
     * @param size item所在层级的项的总数
     */
    private fun handleData(value: Any?, key: String, level: Int, index: Int = 0, size: Int) {
        when (value) {
            is JSONArray -> handleJsonArray(value, key, level, index, size)
            is JSONObject -> handleJsonObject(value, key, level, index, size)
            else -> handleOther(value, key, level, index, size)
        }
    }

    /**
     * 处理JsonArray
     *
     * @param jsonArray jsonValue
     * @param key jsonKey
     * @param level item所在的层级（处理缩进）
     * @param index item在所在层级的次序
     * @param size item所在层级的项的总数
     */
    private fun handleJsonArray(
        jsonArray: JSONArray,
        key: String = "",
        level: Int = 0,
        index: Int = 0,
        size: Int = 1
    ) {
        // jsonArray开始
        itemList.add(JsonItem(level, -1, size, key, jsonArray))
        val arraySize = jsonArray.length()
        for (i in 0 until arraySize) {
            // 处理每一项
            handleData(jsonArray.opt(i), "", level + 1, i, arraySize)
        }
        // jsonArray结束
        itemList.add(JsonItem(level, index, size, "", jsonArray))
    }

    /**
     * 处理JsonObject
     *
     * @param jsonObject jsonValue
     * @param key jsonKey
     * @param level item所在的层级（处理缩进）
     * @param index item在所在层级的次序
     * @param size item所在层级的项的总数
     */
    private fun handleJsonObject(
        jsonObject: JSONObject,
        key: String = "",
        level: Int = 0,
        index: Int = 0,
        size: Int = 1
    ) {
        // jsonObject开始
        itemList.add(JsonItem(level, -1, size, key, jsonObject))
        var i = 0
        val jsonSize = jsonObject.length()
        jsonObject.keys().forEach {
            // 处理每个键值对
            handleData(jsonObject[it], it, level + 1, i, jsonSize)
            i++
        }
        // jsonObject结束
        itemList.add(JsonItem(level, index, size, "", jsonObject))
    }

    /**
     * 处理除了JsonObject和JsonArray之外的
     *
     * @param value jsonValue
     * @param key jsonKey
     * @param level item所在的层级（处理缩进）
     * @param index item在所在层级的次序
     * @param size item所在层级的项的总数
     */
    private fun handleOther(
        value: Any?,
        key: String = "",
        level: Int = 0,
        index: Int = 0,
        size: Int = 1
    ) {
        itemList.add(JsonItem(level, index, size, key, value))
    }

    class JsonViewHolder(val jsonItemView: JsonItemView) : RecyclerView.ViewHolder(jsonItemView)
}