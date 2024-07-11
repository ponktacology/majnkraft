package me.ponktacology.majnkraft

import org.joml.Vector2i

data class World(private val camera: Camera, private val noiseGenerator: PerlinNoiseGenerator) {

    companion object {
        const val VIEW_DISTANCE = 16
    }

    private val chunks = HashSet<Chunk>()
    val cubes = HashSet<Entity>()
    private var prevChunkPosX = -2
    private var prevChunkPosZ = -2

    fun tick() {
        if (!camera.hasMoved()) return

        val position = camera.position


        val chunkX = (position.x.toInt() shr 4)
        val chunkZ = (position.z.toInt() shr 4)

        if (chunkX == prevChunkPosX && chunkZ == prevChunkPosZ) {
            return
        }

        chunks.clear()
        cubes.clear()

        for (i in (-VIEW_DISTANCE..VIEW_DISTANCE)) {
            for (j in (-VIEW_DISTANCE..VIEW_DISTANCE)) {
                val chunk = Chunk(Vector2i(chunkX + i, chunkZ + j))
                chunks.add(chunk)
                generateChunk(chunk)
                cubes.add(ChunkRenderer.createCubes(chunk))
            }
        }

        prevChunkPosX = chunkX
        prevChunkPosZ = chunkZ
    }

    fun generateChunk(chunk: Chunk) {
        ChunkGenerator.generate(chunk, noiseGenerator)
    }
}