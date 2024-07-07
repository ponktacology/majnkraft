package me.ponktacology.majnkraft.world

data class Chunk(val x: Int, val z: Int) {
    val chunkKey = (x.toLong() shl 32) + z.toLong()
    val blocks = Array(16) { Array(256) { arrayOfNulls<Block>(16) } }
}