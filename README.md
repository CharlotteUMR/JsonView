# JsonView

一个用于展示 Json 数据的控件

## 接入方式

项目已经发布到 MavenCentral 可以直接在 `build.gradle`中添加如下代码

```groovy
implementation 'io.github.charlotteumr.jv:jsonview:1.1'
```

## 功能

1. JsonArray 和 JsonObject 可以折叠展开
2. 搜索高亮
3. 可以对 url 单独设置点击
4. 万向滚动

## API

### 属性

```xml
<!-- 整体文字大小 -->
<attr name="jv_text_size" format="dimension" />
<!-- 层级缩进 -->
<attr name="jv_level_indent" format="integer" />
<!-- 默认文字颜色 -->
<attr name="jv_default_text_color" format="color" />
<!-- jsonKey文字颜色 -->
<attr name="jv_key_text_color" format="color" />
<!-- jsonValue如果为String时文字颜色 -->
<attr name="jv_string_text_color" format="color" />
<!-- jsonValue如果为Url时文字颜色 -->
<attr name="jv_url_text_color" format="color" />
<!-- jsonValue如果为数字时文字颜色 -->
<attr name="jv_number_text_color" format="color" />
<!-- jsonValue如果为Boolean时文字颜色 -->
<attr name="jv_boolean_text_color" format="color" />
<!-- jsonValue如果为异常情况时文字颜色 -->
<attr name="jv_error_text_color" format="color" />
<!-- 搜索高亮背景色 -->
<attr name="jv_highlight_bg_color" format="color" />
```

### 方法

设置 Json （目前支持 `JSONObject`、`JSONArray`、`String` 类型）

```kotlin
/**
 * 填充数据源
 *
 * [JSONObject]或[JSONArray]或[String]类型
 */
fun setData(data: Any?)
```

搜索

```kotlin
/**
 * 搜索参数
 */
var searchParam: SearchParam? = null

/**
 * 搜索参数
 *
 * [searchKey] 搜索关键词
 *
 * [ignoreCase] 是否忽略大小写
 */
class SearchParam(val searchKey: String, val ignoreCase: Boolean = false)
```

url 点击

```kotlin
var onUrlClickListener: OnUrlClickListener? = null

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
```

## 示例

![](https://github.com/CharlotteUMR/JsonView/blob/version_1.X/example/JsonViewExample.gif?raw=true)
