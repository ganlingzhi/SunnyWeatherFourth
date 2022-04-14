package com.example.sunnyweather.nc

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 * 在指定位置挖洞的背景图片
 * 用于新手引导某位置高亮
 *
 *
 * @author noobYang
 */
class HighLightBgDrawable(private val strategy: HighLightTypeStrategy) : Drawable() {

    private val paint = Paint()

    lateinit var mCanvas: Canvas

    private val mDefaultBgColor: Int by lazy {
        Color.parseColor("#b2000000")
    }

    init {
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
    }

    override fun draw(canvas: Canvas) {

        val saveCount =
            canvas.saveLayer(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), paint)

        canvas.drawColor(mDefaultBgColor)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        strategy.drawHighLight(canvas, paint)

        paint.xfermode = null

        canvas.restoreToCount(saveCount)

    }

    override fun setAlpha(alpha: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    interface HighLightTypeStrategy {
        fun drawHighLight(canvas: Canvas, paint: Paint)
    }

    /**
     * 圆形挖孔
     */
    class CircleStrategy(
        private var circleCenterX: Float,
        private var circleCenterY: Float,
        private var radius: Float
    ) : HighLightTypeStrategy {
        override fun drawHighLight(canvas: Canvas, paint: Paint) {
            canvas.drawCircle(circleCenterX, circleCenterY, radius, paint)
        }
    }

    /**
     * 方形挖孔
     */
    class RectangleStrategy(
        private var left: Int,
        private var top: Int,
        private var right: Int,
        private var bottom: Int,
        private var radius: Float
    ) : HighLightTypeStrategy {
        override fun drawHighLight(canvas: Canvas, paint: Paint) {
            canvas.drawRoundRect(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                radius,
                radius,
                paint
            )
        }

    }
}