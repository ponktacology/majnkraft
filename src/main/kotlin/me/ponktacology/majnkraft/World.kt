package me.ponktacology.majnkraft

import org.joml.Vector2i

data class World(private val camera: Camera) {

    companion object {
        const val VIEW_DISTANCE = 16
    }

    private val chunks = HashSet<Chunk>()
    val cubes = HashSet<Entity>()
    private var prevChunkPosX = -1
    private var prevChunkPosZ = -1

    fun tick() {
        if (!camera.hasMoved()) return

        val position = camera.position


        val chunkX = position.x.toInt() shr 4
        val chunkZ = position.z.toInt() shr 4

        if (chunkX == prevChunkPosX && chunkZ == prevChunkPosZ) {
            return
        }

        chunks.clear()
        cubes.clear()

        for (i in (-VIEW_DISTANCE..VIEW_DISTANCE)) {
            for (j in (-VIEW_DISTANCE..VIEW_DISTANCE)) {
                val chunk = Chunk(Vector2i(chunkX + i, chunkZ + j))
                chunks.add(chunk)
                cubes.add(ChunkRenderer.createCubes(chunk))
            }
        }

        prevChunkPosX = chunkX
        prevChunkPosZ = chunkZ
    }
}