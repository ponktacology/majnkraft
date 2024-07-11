package me.ponktacology.majnkraft

import org.joml.Vector2i

const val CHUNK_SIZE = 16
const val CHUNK_HEIGHT = 16

data class Chunk(val position: Vector2i) {

    val blocks = Array(CHUNK_SIZE) { Array(CHUNK_HEIGHT) { IntArray(CHUNK_SIZE) } }

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