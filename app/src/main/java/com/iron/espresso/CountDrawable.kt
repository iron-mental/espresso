package com.iron.espresso

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import kotlin.math.max

class CountDrawable(context: Context) : Drawable() {
    private val badgePaint: Paint
    private val textPaint: Paint
    private val textRect = Rect()
    private var count = ""
    private var willDraw = false

    init {
        val mTextSize = context.resources.getDimension(R.dimen.badge_count_textsize)
        badgePaint = Paint()
        badgePaint.color =
            ContextCompat.getColor(context, R.color.ffeb5545)
        badgePaint.isAntiAlias = true
        badgePaint.style = Paint.Style.FILL
        textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.typeface = Typeface.DEFAULT
        textPaint.textSize = mTextSize
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        if (!willDraw) {
            return
        }
        val bounds = bounds
        val width = (bounds.right - bounds.left).toFloat()
        val height = (bounds.bottom - bounds.top).toFloat()

        // Position the badge in the top-right quadrant of the icon.

        /*Using Math.max rather than Math.min */
        val radius = max(width, height) / 2 / 2
        val centerX = width - radius - 1 + 5
        val centerY = radius - 5
        if (count.length <= 2) {
            // Draw badge circle.
            canvas.drawCircle(centerX, centerY, (radius + 5.5f), badgePaint)
        } else {
            canvas.drawCircle(centerX, centerY, (radius + 6.5f), badgePaint)
        }
        // Draw badge count text inside the circle.
        textPaint.getTextBounds(count, 0, count.length, textRect)
        val textHeight = (textRect.bottom - textRect.top).toFloat()
        val textY = centerY + textHeight / 2f
        if (count.length > 2) canvas.drawText(
            "99+",
            centerX,
            textY,
            textPaint
        ) else canvas.drawText(count, centerX, textY, textPaint)
    }

    /*
    Sets the count (i.e notifications) to display.
     */
    fun setCount(count: String) {
        this.count = count

        // Only draw a badge if there are notifications.
        willDraw = !count.equals("0", ignoreCase = true)
        invalidateSelf()
    }

    override fun setAlpha(alpha: Int) {
        // do nothing
    }

    override fun setColorFilter(cf: ColorFilter?) {
        // do nothing
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }
}