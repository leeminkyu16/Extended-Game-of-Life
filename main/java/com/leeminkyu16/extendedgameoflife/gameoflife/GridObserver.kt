package com.leeminkyu16.extendedgameoflife.gameoflife

interface GridObserver {
    fun update(xCoordinate: Int, yCoordinate: Int, value: Int)
}