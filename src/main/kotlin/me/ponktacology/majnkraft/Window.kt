package me.ponktacology.majnkraft

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL20.glViewport

data class Window(val id: Long, var width: Int, var height: Int, val onUpdate: (() -> Unit) = {}) {

    init {
        glfwSetFramebufferSizeCallback(id) { _, width, height ->
            glViewport(0, 0, width, height)
            this.width = width
            this.height = height
            onUpdate()
        }
    }

    fun title(title: String) {
        glfwSetWindowTitle(id, title)
    }

    fun hasClosed() = glfwWindowShouldClose(id)

}