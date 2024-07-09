package me.ponktacology.majnkraft

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryUtil.NULL

object GLContext {

    fun init(width: Int, height: Int, vsync: Boolean = false, onUpdate: () -> (Unit) = {}): Window {
        glfwInit()

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        //glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

        val windowId = glfwCreateWindow(width, height, "Learn", NULL, NULL)
        require(windowId != NULL) { "Failed to create the GLFW window" }
        val window = Window(windowId, width, height, onUpdate)

        glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_DISABLED)
        glfwMakeContextCurrent(windowId)
        GL.createCapabilities()

        glViewport(0, 0, width, height)
        glEnable(GL_DEPTH_TEST)

        glfwSwapInterval(if (vsync) 1 else 0)
        return window
    }
}