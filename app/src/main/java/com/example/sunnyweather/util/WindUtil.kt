package com.example.sunnyweather.util

object WindUtil {
    fun getWind(speed: Float, direction: Float): Wind {
        val directionDescription = when (direction) {
            in 0f..11.25f -> "北风"
            in 11.24f..33.75f -> "北偏东风"
            in 33.74f..56.25f -> "东北风"
            in 56.24f..78.75f -> "东偏北风"
            in 78.74f..101.25f -> "东风"
            in 101.24f..123.75f -> "东偏南风"
            in 123.74f..146.25f -> "东南风"
            in 146.24f..168.75f -> "南偏东风"
            in 168.74f..191.25f -> "南风"
            in 191.24f..213.75f -> "南偏西风"
            in 213.74f..236.25f -> "西南风"
            in 236.24f..258.75f -> "西偏南风"
            in 258.74f..281.25f -> "西风"
            in 281.24f..303.75f -> "西偏北风"
            in 303.74f..326.25f -> "西北风"
            in 326.24f..348.75f -> "北偏西风"
            else -> "北风"
        }
        when (speed) {
            in 0f..1f -> return Wind("无风", speed, "静，烟直上", directionDescription)
            in 1f..5f -> return Wind("软风", speed, "烟示风向", directionDescription)
            in 5f..11f -> return Wind("软风", speed, "感觉有风", directionDescription)
            in 11f..19f -> return Wind("软风", speed, "旌旗展开", directionDescription)
            in 19f..28f -> return Wind("软风", speed, "吹起尘土", directionDescription)
            in 28f..38f -> return Wind("软风", speed, "小树摇摆", directionDescription)
            in 38f..49f -> return Wind("软风", speed, "电线有声", directionDescription)
            in 49f..61f -> return Wind("软风", speed, "步行困难", directionDescription)
            in 61f..74f -> return Wind("软风", speed, "折毁树枝", directionDescription)
            in 74f..88f -> return Wind("软风", speed, "小损房屋", directionDescription)
            in 88f..102f -> return Wind("软风", speed, "拔起树木", directionDescription)
            in 102f..117f -> return Wind("暴风", speed, "损毁重大", directionDescription)
            in 117f..134f -> return Wind("台风（一级飓风）", speed, "摧毁极大", directionDescription)
            in 134f..149f -> return Wind("台风（一级飓风）", speed, "摧毁极大", directionDescription)
            in 149f..166f -> return Wind("强台风（二级飓风）", speed, "摧毁极大", directionDescription)
            in 166f..183f -> return Wind("强台风（三级飓风）", speed, "摧毁极大", directionDescription)
            in 183f..201f -> return Wind("超强台风（三级飓风）", speed, "摧毁极大", directionDescription)
            in 201f..221f -> return Wind("超强台风（四级飓风）", speed, "摧毁极大", directionDescription)
            else
            -> return Wind("超级台风", speed, "摧毁极大", directionDescription)
        }
    }

}

class Wind(val level: String, val speed: Float, val description: String, val direction: String)
