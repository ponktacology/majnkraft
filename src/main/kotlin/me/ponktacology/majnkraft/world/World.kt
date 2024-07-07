package me.ponktacology.majnkraft.world

import me.ponktacology.majnkraft.renderer.GlobalRenderer
import org.joml.Vector3f
import java.util.concurrent.ConcurrentHashMap

const val VIEW_DISTANCE = 100

data class World(val renderer: GlobalRenderer) {

    private val loadedChunks = ConcurrentHashMap<Long, Chunk>()

    fun tick() {
        val camera = renderer.camera()

        val cameraChunkX = camera.position.x.toInt() shr 4
        val cameraChunkZ = camera.position.z.toInt() shr 4

        populateChunks(cameraChunkX, cameraChunkZ)
    }

    private fun populateChunks(cameraChunkX: Int, cameraChunkZ: Int) {
        val currentlyLoadedChunks = mutableSetOf<Long>()

        for (dx in -VIEW_DISTANCE..VIEW_DISTANCE) {
            for (dz in -VIEW_DISTANCE..VIEW_DISTANCE) {
                val x = cameraChunkX + dx
                val z = cameraChunkZ + dz
                val key = (x.toLong() shl 32) + z.toLong()
                if (!loadedChunks.containsKey(key)) {
                    val chunk = Chunk(x, z)
                    loadChunk(chunk)
                }
                currentlyLoadedChunks.add(key)
            }
        }


        loadedChunks.entries.forEach {
            if (!currentlyLoadedChunks.contains(it.key)) {
                unLoadChunk(it.value)
            }
        }

        println("LOADED $loadedChunks")
    }

    private fun loadChunk(chunk: Chunk) {
        loadedChunks[chunk.chunkKey] = chunk

        for (x in 0..15) {
            for (z in 0..15) {
                for (y in 0..256) {
                    if (y < 100) {
                        val block = convertToRenderable(x + (chunk.x shl 4), y, z + (chunk.z shl 4))
                        chunk.blocks[x][y][z] = block
                        //   renderer.addRenderable(block)
                    }
                }
            }
        }
    }

    private fun unLoadChunk(chunk: Chunk) {
        loadedChunks.remove(chunk.chunkKey)

        for (x in 0..15) {
            for (z in 0..15) {
                for (y in 0..256) {
                    if (y == 1) {
                        chunk.blocks[x][y][z]?.let {
                            //         renderer.removeRenderable(it)
                        }
                    }
                }
            }
        }
    }

    private fun convertToRenderable(x: Int, y: Int, z: Int): Block {
        return Block(position = Vector3f(x.toFloat(), y.toFloat(), z.toFloat()))
    }

}