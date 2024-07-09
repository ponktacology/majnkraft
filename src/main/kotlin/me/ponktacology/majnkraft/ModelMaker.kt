package me.ponktacology.majnkraft

import org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray
import org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays

object ModelMaker {


    fun createModel(vertices: FloatArray, textures: FloatArray): Mesh {
        val vao = glGenVertexArrays()
        glBindVertexArray(vao)

        GLHelper.createVBO(0, 3, vertices)
        GLHelper.createVBO(1, 2, textures)
        glBindVertexArray(0)

        return Mesh(vao, vertices.size)
    }


}