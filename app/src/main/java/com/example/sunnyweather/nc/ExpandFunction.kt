package com.example.sunnyweather.nc

import android.content.Context
import com.example.sunnyweather.SunnyWeatherApplication

/**
 * kotlin 扩展方法
 */
class ExpandFunction {
    companion object {
        /**
         * 添加状态
         */
        fun Int.addState(state: Int): Int {
            return this or state
        }

        /**
         * 移除状态
         */
        fun Int.removeState(state: Int): Int {
            return this and state.inv()
        }

        /**
         * 判断是否包含某状态
         */
        fun Int.hasState(state: Int): Boolean {
            return (this and state) > 0
        }


        /**
         * 使用Html.fromHtml时无法识别\n换行符导致换行失败
         * 在此将 \n 替换为 <br>
         */
        fun String.forHtml(): String {
            return this.replace("\n", "<br>")
        }

        fun Float.dp2px(context: Context?): Int {
            return DensityUtil.dip2px(context?: SunnyWeatherApplication.context, this)
        }
        fun Float.px2dp(context: Context?): Int {
            return DensityUtil.px2dip(context?: SunnyWeatherApplication.context, this)
        }
        fun Float.sp2px(context: Context?): Int {
            return DensityUtil.sp2px(context?: SunnyWeatherApplication.context, this)
        }
        fun Float.px2sp(context: Context?): Int {
            return DensityUtil.px2sp(context?: SunnyWeatherApplication.context, this)
        }
    }
}