package me.ponktacology.majnkraft

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWCursorPosCallback
import org.lwjgl.glfw.GLFWKeyCallback

data class Input(private val window: Window) {

    private val keyBoardCallback: GLFWKeyCallback
    private val mouseCallback: GLFWCursorPosCallback

    private var prevX = 0.0
    private var prevY = 0.0
    private var currentX = 0.0
    private var currentY = 0.0

    var deltaX = 0.0
    var deltaY = 0.0

    init {
        keyBoardCallback = GLFWKeyCallback.create { window, key, scancode, action, mods ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true)
        }
        glfwSetKeyCallback(window.id, keyBoardCallback)

        mouseCallback = GLFWCursorPosCallback.create { _, x, y ->
            update(x, y)
        }
        glfwSetCursorPosCallback(window.id, mouseCallback)
    }


    fun update(currentX: Double, currentY: Double) {
        this.prevX = this.currentX
        this.prevY = this.currentY
        this.currentX = currentX
        this.currentY = currentY
        this.deltaY = this.currentY - this.prevY
        this.deltaX = this.currentX - this.prevX
    }

    fun reset() {
        deltaX = 0.0
        deltaY = 0.0
    }

    fun isKeyPressed(key: Int) = glfwGetKey(window.id, key) == GLFW_PRESS
}