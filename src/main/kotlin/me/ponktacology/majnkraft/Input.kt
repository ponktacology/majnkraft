package me.ponktacology.majnkraft

import me.ponktacology.majnkraft.renderer.Mouse
import me.ponktacology.majnkraft.renderer.Window
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWCursorPosCallback
import org.lwjgl.glfw.GLFWKeyCallback

data class Input(private val window: Window) {

    private lateinit var keyBoardCallback: GLFWKeyCallback
    private lateinit var mouseCallback: GLFWCursorPosCallback

    fun init() {
        keyBoardCallback = GLFWKeyCallback.create { window, key, scancode, action, mods ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true)
        }
        glfwSetKeyCallback(window.id, keyBoardCallback)

        mouseCallback = GLFWCursorPosCallback.create { _, x, y ->
            Mouse.update(Mouse.currentX, Mouse.currentY, x, y)
        }
        glfwSetCursorPosCallback(window.id, mouseCallback)
    }

    fun isKeyPressed(key: Int) = glfwGetKey(window.id, key) == GLFW_PRESS
}