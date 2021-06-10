package com.jerry.jv

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
    private val itemList = arrayListOf<JsonItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JsonViewHolder =
        JsonViewHolder(JsonItemView(parent as JsonRecyclerView))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: JsonViewHolder, position: Int) {
        holder.jsonItemView.setViewData(itemList[position])
    }

    class JsonViewHolder(val jsonItemView: JsonItemView) : RecyclerView.ViewHolder(jsonItemView)

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
        }

        // 刷新
        notifyDataSetChanged()
    }

    private fun handleData(data: Any?, key: String, level: Int, index: Int = 0, size: Int) {
        when (data) {
            is JSONArray -> handleJsonArray(data, key, level, index, size)
            is JSONObject -> handleJsonObject(data, key, level, index, size)
            else -> handleOther(data, key, level, index, size)
        }
    }

    /**
     * 处理JsonArray
     */
    private fun handleJsonArray(
        jsonArray: JSONArray,
        key: String = "",
        level: Int = 0,
        index: Int = 0,
        size: Int = 1
    ) {
        itemList.add(JsonItem(level, -1, size, key, jsonArray))
        val arraySize = jsonArray.length()
        for (i in 0 until arraySize) {
            handleData(jsonArray.opt(i), "", level + 1, i, arraySize)
        }
        itemList.add(JsonItem(level, index, size, "", "]"))
    }

    /**
     * 处理JsonObject
     */
    private fun handleJsonObject(
        jsonObject: JSONObject,
        key: String = "",
        level: Int = 0,
        index: Int = 0,
        size: Int = 1
    ) {
        itemList.add(JsonItem(level, -1, size, key, jsonObject))
        var i = 0
        val jsonSize = jsonObject.length()
        jsonObject.keys().forEach {
            handleData(jsonObject[it], it, level + 1, i, jsonSize)
            i++
        }
        itemList.add(JsonItem(level, index, size, "", "}"))
    }

    /**
     * 处理除了JsonObject和JsonArray之外的
     */
    private fun handleOther(
        data: Any?,
        key: String = "",
        level: Int = 0,
        index: Int = 0,
        size: Int = 1
    ) {
        itemList.add(JsonItem(level, index, size, key, data))
    }
}