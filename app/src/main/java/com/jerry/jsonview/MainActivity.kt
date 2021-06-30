package com.jerry.jsonview

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import io.github.charlotteumr.jv.JsonView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jv_test.setData(
            "{\n" +
                    "    \"string\":\"string\",\n" +
                    "    \"number\":100,\n" +
                    "    \"boolean\":true,\n" +
                    "    \"url\":\"https://github.com/CharlotteUMR/JsonView\",\n" +
                    "    \"JSONObject\":{\n" +
                    "        \"string\":\"string\",\n" +
                    "        \"number\":100,\n" +
                    "        \"boolean\":true\n" +
                    "    },\n" +
                    "    \"JSONArray\":[\n" +
                    "        {\n" +
                    "            \"string\":\"string\",\n" +
                    "            \"number\":100,\n" +
                    "            \"boolean\":true\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"string\":\"string\",\n" +
                    "            \"number\":100,\n" +
                    "            \"boolean\":true\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"string\":\"string\",\n" +
                    "            \"number\":100,\n" +
                    "            \"boolean\":true\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"string\":\"string\",\n" +
                    "            \"number\":100,\n" +
                    "            \"boolean\":true\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"string\":\"string\",\n" +
                    "            \"number\":100,\n" +
                    "            \"boolean\":true\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"string\":\"string\",\n" +
                    "            \"number\":100,\n" +
                    "            \"boolean\":true\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"string\":\"string\",\n" +
                    "            \"number\":100,\n" +
                    "            \"boolean\":true\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"string\":\"string\",\n" +
                    "            \"number\":100,\n" +
                    "            \"boolean\":true\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"string\":\"stringsssssssssssssssssssssssssssssssssssssssssssssssssss\",\n" +
                    "            \"number\":100,\n" +
                    "            \"boolean\":true\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}"
        )
        et_search_key.addTextChangedListener {
            jv_test.searchParam =
                JsonView.SearchParam(it.toString(), cb_ignore_case.isChecked)
        }
        cb_ignore_case.setOnCheckedChangeListener { _, isChecked ->
            jv_test.searchParam =
                JsonView.SearchParam(et_search_key.text.toString(), isChecked)
        }
        jv_test.onUrlClickListener = object : JsonView.OnUrlClickListener {
            override fun onUrlClick(url: String) {
                Toast.makeText(this@MainActivity, url, Toast.LENGTH_SHORT).show()
            }
        }
    }
}