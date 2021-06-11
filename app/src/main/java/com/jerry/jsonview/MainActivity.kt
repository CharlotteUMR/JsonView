package com.jerry.jsonview

import android.app.Activity
import android.os.Bundle
import com.jerry.jv.JsonRecyclerView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonRecyclerView = findViewById<JsonRecyclerView>(R.id.jv_test)
        jsonRecyclerView.setData(
            "{\n" +
                    "    \"string\":\"string\",\n" +
                    "    \"number\":100,\n" +
                    "    \"boolean\":true,\n" +
                    "    \"url\":\"https://github.com/Jerry-Mr-Xu/JsonView\",\n" +
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
    }
}