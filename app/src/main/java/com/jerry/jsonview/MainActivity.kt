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
            "[\n" +
                    "    {\n" +
                    "        \"name\":\"张国立\",\n" +
                    "        \"sex\":\"男\",\n" +
                    "        \"email\":\"zhangguoli@123.com\",\n" +
                    "        \"url\":\"./img/1.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"张铁林\",\n" +
                    "        \"sex\":\"男\",\n" +
                    "        \"email\":\"zhangtieli@123.com\",\n" +
                    "        \"url\":\"./img/2.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"邓婕\",\n" +
                    "        \"sex\":\"女\",\n" +
                    "        \"email\":\"zhenjie@123.com\",\n" +
                    "        \"url\":\"./img/3.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"张国立\",\n" +
                    "        \"sex\":\"男\",\n" +
                    "        \"email\":\"zhangguoli@123.com\",\n" +
                    "        \"url\":\"./img/4.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"张铁林\",\n" +
                    "        \"sex\":\"男\",\n" +
                    "        \"email\":\"zhangtieli@123.com\",\n" +
                    "        \"url\":\"./img/5.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"邓婕\",\n" +
                    "        \"sex\":\"女\",\n" +
                    "        \"email\":\"zhenjie@123.com\",\n" +
                    "        \"url\":\"./img/6.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"邓婕\",\n" +
                    "        \"sex\":\"女\",\n" +
                    "        \"email\":\"zhenjie@123.com\",\n" +
                    "        \"url\":\"./img/6.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"邓婕\",\n" +
                    "        \"sex\":\"女\",\n" +
                    "        \"email\":\"zhenjie@123.com\",\n" +
                    "        \"url\":\"./img/6.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"邓婕\",\n" +
                    "        \"sex\":\"女\",\n" +
                    "        \"email\":\"zhenjie@123.com\",\n" +
                    "        \"url\":\"./img/6.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"邓婕\",\n" +
                    "        \"sex\":\"女\",\n" +
                    "        \"email\":\"zhenjie@123.com\",\n" +
                    "        \"url\":\"./img/6.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"邓婕\",\n" +
                    "        \"sex\":\"女\",\n" +
                    "        \"email\":\"zhenjie@123.com\",\n" +
                    "        \"url\":\"./img/6.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"邓婕\",\n" +
                    "        \"sex\":\"女\",\n" +
                    "        \"email\":\"zhenjie@123.com\",\n" +
                    "        \"url\":\"./img/6.jpg\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\":\"邓婕\",\n" +
                    "        \"sex\":\"女\",\n" +
                    "        \"email\":\"zhenjie@123.com\",\n" +
                    "        \"url\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa./img/6.jpg\"\n" +
                    "    }\n" +
                    "]"
        )
    }
}