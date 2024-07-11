package me.ponktacology.majnkraft

object ChunkGenerator {

    fun generate(chunk: Chunk, noiseGenerator: PerlinNoiseGenerator) {
        for (x in 0..<16) {
            for (z in 0..<16) {
                chunk.blocks[z][0][x] = 1
            }
        }
    }


}