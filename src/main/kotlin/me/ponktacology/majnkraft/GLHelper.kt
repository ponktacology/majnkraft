package me.ponktacology.majnkraft

import org.lwjgl.opengl.GL20.*

object GLHelper {

    fun clear() {
        glClearColor(0f, 0f, 0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    fun createVBO(index: Int, entrySize: Int, floatArray: FloatArray) {
        val vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)

        glBufferData(GL_ARRAY_BUFFER, floatArray, GL_STATIC_DRAW)

        glVertexAttribPointer(index, entrySize, GL_FLOAT, false, 0, 0)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun turnWireframe() {
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE)
    }

    fun disableWireframe() {
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL)
    }

}