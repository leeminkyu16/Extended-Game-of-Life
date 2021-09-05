package com.leeminkyu16.extendedgameoflife.gameoflife

interface CellObserver {
    fun update(cellValue: Int)
}