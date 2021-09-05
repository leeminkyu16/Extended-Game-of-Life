package com.leeminkyu16.extendedgameoflife.gameoflife

import android.graphics.Paint
import com.leeminkyu16.extendedgameoflife.CanvasView

class CanvasGrid constructor(
    private val xSpan: Int,
    private val ySpan: Int,
    private val canvasView: CanvasView
) : Grid(xSpan, ySpan) {

    init {
        for (i in 0..xSpan) {
            cells.add(arrayListOf<Cell>())
            for (j in 0..ySpan) {
                cells[i].add(Cell(i, j, arrayListOf<GridObserver>(this)))
            }
        }

        for (i in 0..xSpan) {
            for (j in 0..ySpan) {

                for (di in -1..1) {
                    for (dj in -1..1) {
                        if (!(di == 0 && dj == 0)
                            && (i + di) in 0..xSpan
                            && (j + dj) in 0..ySpan
                        ) {
                            cells[i][j].attach(cells[i + di][j + dj])
                        }
                    }
                }

            }
        }
    }

    private val blackPaint = Paint().apply {
        color = canvasView.colorBlack
        isAntiAlias = true
        isDither = true
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
    }
    private val bluePaint = Paint().apply {
        color = canvasView.colorBlue900
        isAntiAlias = true
        isDither = true
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
    }
    private val yellowPaint = Paint().apply {
        color = canvasView.colorYellow900
        isAntiAlias = true
        isDither = true
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
    }
    private val redPaint = Paint().apply {
        color = canvasView.colorRed900
        isAntiAlias = true
        isDither = true
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
    }
    private val greenPaint = Paint().apply {
        color = canvasView.colorGreen900
        isAntiAlias = true
        isDither = true
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
    }

    private val whitePaint = Paint().apply {
        color = canvasView.colorWhite
        isAntiAlias = true
        isDither = true
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
    }
    private val grayStrokePaint = Paint().apply {
        color = canvasView.colorGray900
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeWidth = 8f
    }

    override fun update(xCoordinate: Int, yCoordinate: Int, value: Int) {
        val leftCoordinate = (xCoordinate.toFloat()
                * canvasView.squareWidth - canvasView.xOffset)
        val rightCoordinate = ((xCoordinate.toFloat() + 1)
                * canvasView.squareWidth - canvasView.xOffset)
        val topCoordinate = (yCoordinate.toFloat()
                * canvasView.squareWidth - canvasView.yOffset)
        val bottomCoordinate = ((yCoordinate.toFloat() + 1)
                * canvasView.squareWidth - canvasView.yOffset)

        when (value) {
            0 -> {
                canvasView.extraCanvas.drawRect(
                    leftCoordinate,
                    topCoordinate,
                    rightCoordinate,
                    bottomCoordinate,
                    whitePaint
                )
            }
            1 -> {
                canvasView.extraCanvas.drawRect(
                    leftCoordinate,
                    topCoordinate,
                    rightCoordinate,
                    bottomCoordinate,
                    blackPaint
                )
            }
            2 -> {
                canvasView.extraCanvas.drawRect(
                    leftCoordinate,
                    topCoordinate,
                    rightCoordinate,
                    bottomCoordinate,
                    bluePaint
                )
            }
            3 -> {
                canvasView.extraCanvas.drawRect(
                    leftCoordinate,
                    topCoordinate,
                    rightCoordinate,
                    bottomCoordinate,
                    yellowPaint
                )
            }
            4 -> {
                canvasView.extraCanvas.drawRect(
                    leftCoordinate,
                    topCoordinate,
                    rightCoordinate,
                    bottomCoordinate,
                    redPaint
                )
            }
            5 -> {
                canvasView.extraCanvas.drawRect(
                    leftCoordinate,
                    topCoordinate,
                    rightCoordinate,
                    bottomCoordinate,
                    greenPaint
                )
            }
        }
        canvasView.extraCanvas.drawRect(
            leftCoordinate,
            topCoordinate,
            rightCoordinate,
            bottomCoordinate,
            grayStrokePaint
        )

        this.canvasView.invalidate()
    }
}