package me.ponktacology.majnkraft

import org.joml.Vector3f

object ChunkRenderer {

    fun createCubes(chunk: Chunk): Entity {
        val vertices = mutableListOf<Float>()
        val textures = mutableListOf<Float>()

        val blocks = chunk.blocks
        blocks.forEachIndexed { x, ys ->
            ys.forEachIndexed { y, zs ->
                zs.forEachIndexed { z, material ->
                    if (material != 1) return@forEachIndexed
                    val faces = mutableSetOf<CubeFace>()
                    faces.addAll(CubeFace.entries)

                    if (y < CHUNK_HEIGHT - 1 && blocks[x][y + 1][z] == 1) {
                        faces.remove(CubeFace.TOP)
                    }

                    if (y > 0 && blocks[x][y - 1][z] == 1) {
                        faces.remove(CubeFace.BOTTOM)
                    }

                    if (x > 0 && blocks[x - 1][y][z] == 1) {
                        faces.remove(CubeFace.LEFT)
                    }

                    if (x < CHUNK_SIZE - 1 && blocks[x + 1][y][z] == 1) {
                        faces.remove(CubeFace.RIGHT)
                    }

                    if (z > 0 && blocks[x][y][z - 1] == 1) {
                        faces.remove(CubeFace.BACK)
                    }

                    if (z < CHUNK_SIZE - 1 && blocks[x][y][z + 1] == 1) {
                        faces.remove(CubeFace.FRONT)
                    }


                    println(faces.size)

                    faces.forEach {
                        CubeModel.vertices[it]?.forEachIndexed { index, fl ->
                            when (index % 3) {
                                0 -> vertices.add(fl + x)
                                1 -> vertices.add(fl + y)
                                2 -> vertices.add(fl + z)
                            }
                            CubeModel.textures[it]?.forEach {
                                textures.add(it)
                            }
                        }


                    }
                }
            }
        }

        println(vertices.size)


        return Cube(
            position = Vector3f((chunk.position.x shl 4).toFloat(), 0f, (chunk.position.y shl 4).toFloat()),
            mesh = ModelMaker.createModel(vertices.toFloatArray(), textures.toFloatArray()),
            texture = CubeModel.texture
        )
    }
}