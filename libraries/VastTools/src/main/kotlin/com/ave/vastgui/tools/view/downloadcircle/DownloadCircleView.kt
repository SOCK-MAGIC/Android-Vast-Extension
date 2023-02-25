/*
 * Copyright 2022 VastGui guihy2019@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ave.vastgui.tools.view.downloadcircle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.ave.vastgui.tools.R
import com.ave.vastgui.tools.utils.ColorUtils
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.cos
import kotlin.math.sin

// Author: Vast Gui 
// Email: guihy2019@gmail.com
// Date: 2022/4/17 19:55
// Description: DownloadCircleView is a circular download progress bar.
// Documentation: [DownloadCircleView](https://sakurajimamaii.github.io/VastDocs/document/en/DownloadCircleView.html)

/**
 * DownloadCircleView.
 *
 * Here is an example in xml:
 * ```xml
 * <com.gcode.vasttools.view.downloadcircle.DownloadCircleView
 *      android:id="@+id/downloadCv"
 *      android:layout_width="200dp"
 *      android:layout_height="200dp"
 *      app:progress_background_width="20dp"
 *      app:progress_text_size="12sp"/>
 * ```
 */
class DownloadCircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle), DownloadCircle {

    private var mProgressBackgroundPaint = Paint()
    private var mProgressPaint = Paint()
    private var mProgressStartPaint = Paint()
    private var mTextCirclePaint = Paint()
    private var mTextPaint = Paint()
    private val mDecimalFormat = DecimalFormat("0.##").also {
        it.roundingMode = RoundingMode.FLOOR
    }

    /**
     * The rect containing the progress arc.
     */
    private val oval = RectF()

    /**
     * Progress text size.
     */
    var progressTextSize = 0f

    /**
     * Radius of the download circle.
     */
    private var circleRadius = 0f

    /**
     * Width of the download circle progress.
     */
    private var progressBackgroundWidth = 0f

    /**
     * Download progress.Range from 0.0 to 1.0.
     */
    private var progress = 0f

    /**
     * Progress background color int.
     */
    private var progressBackgroundColorInt: Int = Color.GRAY

    /**
     * Progress color int.
     */
    private var progressColorInt: Int = ColorUtils.colorHex2Int("#3B4463")

    /**
     * Progress shader.
     */
    private var progressShader: Shader? = null

    /**
     * Progress start color int.
     */
    private var progressStartColorInt: Int = ColorUtils.colorHex2Int("#f0932b")

    /**
     * Progress end color int.
     */
    private var progressEndColorInt: Int = ColorUtils.colorHex2Int("#f0932b")

    /**
     * Progress text color int.
     */
    private var progressTextColorInt: Int = Color.WHITE

    /**
     * Progress stroke cap.
     */
    private var progressStrokeCap: Paint.Cap = Paint.Cap.ROUND

    /**
     * Progress start and end circle.
     */
    private var progressStartAndEnd: Boolean = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val width = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            (2 * circleRadius + progressBackgroundWidth).toInt()
        }
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val height = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            (2 * circleRadius + progressBackgroundWidth).toInt()
        }
        setMeasuredDimension(width, height)
    }

    /**
     * init paints.
     */
    private fun initPaint() {
        mProgressBackgroundPaint.apply {
            strokeWidth = progressBackgroundWidth
            color = progressBackgroundColorInt
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
        mProgressPaint.apply {
            if (null == progressShader) {
                color = progressColorInt
            } else {
                shader = progressShader
            }
            strokeCap = progressStrokeCap
            strokeWidth = progressBackgroundWidth
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
        mProgressStartPaint.apply {
            color = progressStartColorInt
            isAntiAlias = true
            style = Paint.Style.FILL
        }
        mTextCirclePaint.apply {
            color = progressEndColorInt
            isAntiAlias = true
            style = Paint.Style.FILL
        }
        mTextPaint.apply {
            textSize = progressTextSize
            color = progressTextColorInt
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    override fun setProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        this.progress = progress
        invalidate()
    }

    @Throws(RuntimeException::class)
    override fun setProgress(
        @FloatRange(from = 0.0) currentProgress: Float,
        @FloatRange(from = 0.0) totalProgress: Float
    ) {
        if (currentProgress > totalProgress) {
            throw RuntimeException("currentProgress must less then totalProgress!")
        }
        progress = currentProgress / totalProgress
        invalidate()
    }

    override fun resetProgress() {
        progress = 0f
        invalidate()
    }

    override fun setProgressShader(shader: Shader?) {
        progressShader = shader
        invalidate()
    }

    override fun setProgressBackgroundColor(@ColorInt color: Int) {
        progressBackgroundColorInt = color
        invalidate()
    }

    override fun setProgressStartColor(@ColorInt color: Int) {
        progressStartColorInt = color
        invalidate()
    }

    override fun setProgressEndColor(@ColorInt color: Int) {
        progressEndColorInt = color
        invalidate()
    }

    override fun setProgressTextColor(@ColorInt color: Int) {
        progressTextColorInt = color
        invalidate()
    }

    override fun setProgressStartAndEndEnabled(show: Boolean) {
        progressStartAndEnd = show
        invalidate()
    }

    override fun setProgressStrokeCap(cap: Paint.Cap) {
        progressStrokeCap = cap
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initPaint()
        // Draw progress background.
        val circlePoint = (width / 2).toFloat()
        canvas.drawCircle(
            circlePoint,
            circlePoint,
            circlePoint - progressBackgroundWidth / 2,
            mProgressBackgroundPaint
        )
        // Draw progress.
        oval.left = progressBackgroundWidth / 2
        oval.top = progressBackgroundWidth / 2
        oval.right = width - progressBackgroundWidth / 2
        oval.bottom = width - progressBackgroundWidth / 2
        val range = 360 * progress
        canvas.drawArc(oval, -90f, range, false, mProgressPaint)
        if (progressStartAndEnd) {
            // Draw progress start.
            canvas.drawCircle(
                circlePoint,
                progressBackgroundWidth / 2,
                progressBackgroundWidth / 2,
                mProgressStartPaint
            )
            // Draw progress end.
            val radius = circlePoint - progressBackgroundWidth / 2
            val x1 = circlePoint - radius * cos((range + 90) * 3.14 / 180)
            val y1 = circlePoint - radius * sin((range + 90) * 3.14 / 180)
            canvas.drawCircle(
                x1.toFloat(),
                y1.toFloat(),
                progressBackgroundWidth / 2,
                mTextCirclePaint
            )
        }
        val radius = circlePoint - progressBackgroundWidth / 2
        val x1 = circlePoint - radius * cos((range + 90) * 3.14 / 180)
        val y1 = circlePoint - radius * sin((range + 90) * 3.14 / 180)
        val txt = "${mDecimalFormat.format(progress * 100)}%"
        val stringWidth = mTextPaint.measureText(txt)
        canvas.drawText(
            txt,
            x1.toFloat() - stringWidth / 2,
            y1.toFloat() + progressTextSize / 2,
            mTextPaint
        )
    }

    /**
     * Initialize the attributes for the DownloadCircleView.
     */
    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.DownloadCircleView)
        circleRadius = ta.getDimension(R.styleable.DownloadCircleView_circle_radius, 0f)
        progressBackgroundWidth =
            ta.getDimension(R.styleable.DownloadCircleView_progress_background_width, 0f)
        progressTextSize = ta.getDimension(R.styleable.DownloadCircleView_progress_text_size, 0f)
        progressBackgroundColorInt =
            ta.getColor(R.styleable.DownloadCircleView_progress_background_color, Color.GRAY)
        progressColorInt =
            ta.getColor(
                R.styleable.DownloadCircleView_progress_color,
                ColorUtils.colorHex2Int("#3B4463")
            )
        progressStartColorInt =
            ta.getColor(
                R.styleable.DownloadCircleView_progress_start_color,
                ColorUtils.colorHex2Int("#f0932b")
            )
        progressEndColorInt =
            ta.getColor(
                R.styleable.DownloadCircleView_progress_end_color,
                ColorUtils.colorHex2Int("#f0932b")
            )
        progressTextColorInt =
            ta.getColor(R.styleable.DownloadCircleView_progress_text_color, Color.WHITE)
        ta.recycle()
    }
}