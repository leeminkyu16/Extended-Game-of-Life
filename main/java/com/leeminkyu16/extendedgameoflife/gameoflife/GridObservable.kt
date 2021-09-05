package com.leeminkyu16.extendedgameoflife.gameoflife

interface GridObservable {
    val gridObservers: ArrayList<GridObserver>

    fun attach(observer: GridObserver) {
        gridObservers.add(observer)
    }

    fun remove(observer: GridObserver) {
        gridObservers.remove(observer)
    }

    fun sendUpdateEvent()
}