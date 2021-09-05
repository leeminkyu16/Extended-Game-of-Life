package com.leeminkyu16.extendedgameoflife

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.leeminkyu16.extendedgameoflife.gameoflife.CanvasGrid
import kotlin.math.ceil
import kotlin.math.floor

class CanvasView@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    val colorBlack = ResourcesCompat.getColor(resources, R.color.black, null)
    val colorWhite = ResourcesCompat.getColor(resources, R.color.white, null)
    val colorBlue900 = ResourcesCompat.getColor(resources, R.color.blue_900, null)
    val colorYellow900 = ResourcesCompat.getColor(resources, R.color.yellow_900, null)
    val colorRed900 = ResourcesCompat.getColor(resources, R.color.red_900, null)
    val colorGreen900 = ResourcesCompat.getColor(resources, R.color.green_900, null)
    val colorGray900 = ResourcesCompat.getColor(resources, R.color.gray_900, null)

    val squareWidth = 80
    var xOffset = 0
    var yOffset = 0

    lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private val grayStrokePaint = Paint().apply {
        color = colorGray900
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeWidth = 8f
    }

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    var tapMode: Int = 1

    private lateinit var grid: CanvasGrid

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        grid = CanvasGrid(
                ceil(width.toDouble() / squareWidth).toInt(),
                ceil(height.toDouble() / squareWidth).toInt(),
                this)
        xOffset = (width % squareWidth) / 2
        yOffset = (height % squareWidth) / 2

        extraCanvas.drawColor(colorWhite)
        for (i in 0..ceil(width.toDouble() / 80).toInt()) {
            for (j in 0..ceil(height.toDouble() / 80).toInt()) {
                extraCanvas.drawRect(
                        i.toFloat() * squareWidth - xOffset,
                        j.toFloat() * squareWidth - yOffset,
                        (i.toFloat() + 1) * squareWidth - xOffset,
                        (j.toFloat() + 1) * squareWidth - yOffset,
                    grayStrokePaint)
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun touchStart() {
        val xCoordinate = floor((motionTouchEventX + xOffset) / squareWidth).toInt()
        val yCoordinate =  floor((motionTouchEventY + yOffset) / squareWidth).toInt()

        grid.setCellValue(this.tapMode, xCoordinate, yCoordinate)
    }
    private fun touchMove() {

    }
    private fun touchUp() {

    }

    fun doGridIteration() {
        grid.doIteration()
    }

    fun clearGrid() {
        grid.clearGrid()
    }
}