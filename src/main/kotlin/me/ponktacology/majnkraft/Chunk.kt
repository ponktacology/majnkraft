package me.ponktacology.majnkraft

import org.joml.Vector2i
import org.joml.Vector3i

data class Chunk(val position: Vector2i) {

    val blocks = mutableSetOf<Vector3i>()

    init {
        for (i in 0..16) {
            for (j in 0..16) {
                blocks.add(Vector3i(i, 0, j))
                blocks.add(Vector3i(i, 1, j))
            }
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chunk

        return position == other.position
    }

    override fun hashCode(): Int {
        return position.hashCode()
    }


}