package me.ponktacology.majnkraft.renderer

object Mouse {

    private var prevX: Double = 0.0
    private var prevY: Double = 0.0
    var currentX: Double = 0.0
    var currentY: Double = 0.0

    var deltaX = 0.0
    var deltaY = 0.0

    fun update(prevX: Double, prevY: Double, currentX: Double, currentY: Double) {
        this.prevX = prevX
        this.prevY = prevY
        this.currentX = currentX
        this.currentY = currentY
        this.deltaY = this.currentY - this.prevY
        this.deltaX = this.currentX - this.prevX
    }

    fun reset() {
        deltaX = 0.0
        deltaY = 0.0
    }
}