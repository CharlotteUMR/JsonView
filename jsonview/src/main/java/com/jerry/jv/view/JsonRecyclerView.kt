package com.jerry.jv.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jerry.jv.viewmodel.JsonRecyclerAdapter
import com.jerry.jv.JsonView
import org.json.JSONArray
import org.json.JSONObject

/**
 * 用于展示Json的View
 *
 * @author Jerry
 */
internal class JsonRecyclerView : RecyclerView {
    companion object {
        private const val TAG = "JsonRecyclerView"
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var root: JsonView? = null
    private val adapter = JsonRecyclerAdapter()

    init {
        layoutManager = LinearLayoutManager(context)
        setAdapter(adapter)
    }

    fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    /**
     * 填充数据源
     *
     * [JSONObject]或[JSONArray]或[String]类型
     */
    fun setData(data: Any?) {
        adapter.setData(data)
    }
}