package com.leeminkyu16.extendedgameoflife.gameoflife

abstract class Grid constructor(
        private val xSpan: Int,
        private val ySpan: Int)
    : GridObserver{
    protected val cells: ArrayList<ArrayList<Cell>> = arrayListOf<ArrayList<Cell>>()

    abstract override fun update(xCoordinate: Int, yCoordinate: Int, value: Int)

    fun setCellValue(value: Int, xCoordinate: Int, yCoordinate: Int) {
        cells[xCoordinate][yCoordinate].setCellValue(value)
    }

    fun doIteration() {
        for (i in 0..xSpan) {
            for (j in 0..ySpan) {
                cells[i][j].sendUpdateEvent()
            }
        }

        for (i in 0..xSpan) {
            for (j in 0..ySpan) {
                cells[i][j].doIteration()
                cells[i][j].resetNeighbourCount()
            }
        }
    }

    fun clearGrid() {
        for (i in 0..xSpan) {
            for (j in 0..ySpan) {
                cells[i][j].setDead()
            }
        }
    }
}