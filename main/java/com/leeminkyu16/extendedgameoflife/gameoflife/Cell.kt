package com.leeminkyu16.extendedgameoflife.gameoflife

const val NUMBER_OF_CELL_TYPES = 6

const val CELL_DEAD = 0
const val CELL_NORMAL = 1
const val CELL_HIGH = 2
const val CELL_TWO_BY_TWO = 3
const val CELL_LIVE_FREE_OR_DIE = 4
const val CELL_MAZE = 5

private fun getTotalNumOfAliveNeighbours(numOfNeighboursArray: Array<Int>): Int {
    var totalSum = 0
    for (i in 1 until numOfNeighboursArray.size) {
        totalSum += numOfNeighboursArray[i]
    }
    return totalSum
}

private fun getMaxNeighbourValue(numOfNeighboursArray: Array<Int>): Int {
    var maxValue = numOfNeighboursArray[1]
    var maxIndex = 1

    for (i in 2 until numOfNeighboursArray.size) {
        if (numOfNeighboursArray[i] > maxValue) {
            maxIndex = i
            maxValue = numOfNeighboursArray[i]
        }
    }

    return maxIndex
}

class Cell constructor(
    private var x: Int,
    private var y: Int,
    override val gridObservers: ArrayList<GridObserver>
) : CellObserver, CellObservable, GridObservable {
    override val cellObservers: ArrayList<CellObserver> = arrayListOf<CellObserver>()
    private var numOfNeighbours: Array<Int> = Array<Int>(NUMBER_OF_CELL_TYPES) { 0 }
    private var cellValue: Int = 0

    override fun sendUpdateEvent() {
        cellObservers.forEach { it.update(this.cellValue) }
    }

    override fun update(cellValue: Int) {
        this.numOfNeighbours[cellValue]++
    }

    fun setCellValue(value: Int) {
        this.cellValue = value
        this.gridObservers.forEach { it.update(this.x, this.y, this.cellValue) }
    }

    fun setDead() {
        this.cellValue = CELL_DEAD
        this.gridObservers.forEach { it.update(this.x, this.y, 0) }
    }

    fun resetNeighbourCount() {
        for (i in 0 until NUMBER_OF_CELL_TYPES) {
            this.numOfNeighbours[i] = 0
        }
    }

    fun doIteration() {
        val totalNumOfAliveNeighbours = getTotalNumOfAliveNeighbours(this.numOfNeighbours)
        val maxNeighbourValue = getMaxNeighbourValue(this.numOfNeighbours)

        // Cell Alive
        if (this.cellValue > CELL_DEAD) {
            when (cellValue) {
                CELL_NORMAL ->
                    if (totalNumOfAliveNeighbours < 2
                        || 3 < totalNumOfAliveNeighbours
                    ) {
                        this.cellValue = CELL_DEAD
                        this.gridObservers.forEach { it.update(this.x, this.y, this.cellValue) }
                    }
                CELL_HIGH ->
                    if (totalNumOfAliveNeighbours < 2
                        || 3 < totalNumOfAliveNeighbours
                    ) {
                        this.cellValue = CELL_DEAD
                        this.gridObservers.forEach { it.update(this.x, this.y, this.cellValue) }
                    }
                CELL_TWO_BY_TWO ->
                    if (totalNumOfAliveNeighbours < 1
                        || (totalNumOfAliveNeighbours in 3..4)
                        || totalNumOfAliveNeighbours > 5
                    ) {
                        this.cellValue = CELL_DEAD
                        this.gridObservers.forEach { it.update(this.x, this.y, this.cellValue) }
                    }
                CELL_LIVE_FREE_OR_DIE ->
                    if (totalNumOfAliveNeighbours > 0) {
                        this.cellValue = CELL_DEAD
                        this.gridObservers.forEach { it.update(this.x, this.y, this.cellValue) }
                    }
                CELL_MAZE ->
                    if (totalNumOfAliveNeighbours < 1
                        || totalNumOfAliveNeighbours > 5
                    ) {
                        this.cellValue = CELL_DEAD
                        this.gridObservers.forEach { it.update(this.x, this.y, this.cellValue) }
                    }
            }
        }
        // Cell Dead
        else if (this.cellValue == CELL_DEAD) {
            when (totalNumOfAliveNeighbours) {
                2 ->
                    if (maxNeighbourValue == CELL_LIVE_FREE_OR_DIE) {
                        this.cellValue = maxNeighbourValue
                        this.gridObservers.forEach {
                            it.update(this.x, this.y, this.cellValue)
                        }
                    }
                3 ->
                    if (maxNeighbourValue == CELL_NORMAL
                        || maxNeighbourValue == CELL_HIGH
                        || maxNeighbourValue == CELL_TWO_BY_TWO
                        || maxNeighbourValue == CELL_MAZE
                    ) {
                        this.cellValue = maxNeighbourValue
                        this.gridObservers.forEach {
                            it.update(this.x, this.y, this.cellValue)
                        }
                    }
                6 ->
                    if (maxNeighbourValue == CELL_HIGH
                        || maxNeighbourValue == CELL_TWO_BY_TWO
                    ) {
                        this.cellValue = maxNeighbourValue
                        this.gridObservers.forEach {
                            it.update(this.x, this.y, this.cellValue)
                        }
                    }
            }
        }
    }
}