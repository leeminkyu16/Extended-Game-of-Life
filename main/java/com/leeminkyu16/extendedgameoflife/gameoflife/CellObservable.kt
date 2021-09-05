package com.leeminkyu16.extendedgameoflife.gameoflife

interface CellObservable {
    val cellObservers: ArrayList<CellObserver>

    fun attach(observer: CellObserver) {
        cellObservers.add(observer)
    }

    fun remove(observer: CellObserver) {
        cellObservers.remove(observer)
    }

    fun sendUpdateEvent()
}