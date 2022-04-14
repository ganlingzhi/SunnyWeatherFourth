package com.example.sunnyweather.nc

data class NCCommonChooseListItem(
    var name: String,
    val value: Any,
    //是否展示弱化灰色文字
    var weak: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return other != null && other is NCCommonChooseListItem && other.name == name && other.value == value
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}