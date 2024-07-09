package me.ponktacology.majnkraft

import org.joml.Vector3f

object ChunkRenderer {

    fun createCubes(chunk: Chunk): Entity {
        val vertices = FloatArray(CubeModel.vertices.size * chunk.blocks.size)
        val textures = FloatArray(CubeModel.textures.size * chunk.blocks.size)

        var vertexIndex = 0
        var textureIndex = 0

        chunk.blocks.forEach {
            val position = it
            CubeModel.vertices.forEachIndexed { index, fl ->
                when (index % 3) {
                    0 -> vertices[vertexIndex++] = fl + position.x
                    1 -> vertices[vertexIndex++] = fl + position.y
                    2 -> vertices[vertexIndex++] = fl + position.z
                }
            }
            CubeModel.textures.forEach {
                textures[textureIndex++] = it
            }
        }


        return Cube(
            position = Vector3f((chunk.position.x shl 4).toFloat(), 0f, (chunk.position.y shl 4).toFloat()),
            mesh = ModelMaker.createModel(vertices, textures),
            texture = CubeModel.texture
        )
    }
}