package me.ponktacology.majnkraft.renderer

import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*
import kotlin.math.cos
import kotlin.math.sin


data class Camera(val position: Vector3f = Vector3f(0f, 20f, 0f), val rotation: Vector3f = Vector3f(0f, 0f, 0f)) {

    companion object {
        private const val MOVEMENT_SPEED = 0.01f
        private const val FLY_SPEED = 0.03f
    }

    private val mouseRot = Vector3f().zero()
    private val input = Vector3f().zero()

    fun input() {
        input.zero()

        if (Renderer.isKeyPressed(GLFW_KEY_W)) {
            input.z = -1f;
        } else if (Renderer.isKeyPressed(GLFW_KEY_S)) {
            input.z = 1f;
        }
        if (Renderer.isKeyPressed(GLFW_KEY_A)) {
            input.x = -1f;
        } else if (Renderer.isKeyPressed(GLFW_KEY_D)) {
            input.x = 1f;
        }
        if (Renderer.isKeyPressed(GLFW_KEY_SPACE)) {
            input.y = -1f;
        } else if (Renderer.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            input.y = 1f;
        }

        movePosition(
            input.x * MOVEMENT_SPEED,
            input.y * FLY_SPEED,
            input.z * MOVEMENT_SPEED
        )

        // Update camera based on mouse
        if (Mouse.deltaX > 0 || Mouse.deltaY > 0) {
            mouseRot.zero()

            if (Mouse.deltaY != 0.0) {
                mouseRot.x = Mouse.deltaY.toFloat() * MOVEMENT_SPEED
            }

            if (Mouse.deltaX != 0.0) {
                mouseRot.y = Mouse.deltaX.toFloat() * MOVEMENT_SPEED
            }

            moveRotation(mouseRot.x, mouseRot.y, mouseRot.z)
        }
    }

    private fun moveRotation(offsetX: Float, offsetY: Float, offsetZ: Float) {
        rotation.x += offsetX
        rotation.y += offsetY
        rotation.z += offsetZ
    }

    private fun movePosition(offsetX: Float, offsetY: Float, offsetZ: Float) {
        if (offsetZ != 0f) {
            position.x += sin(Math.toRadians(rotation.y.toDouble())).toFloat() * -1.0f * offsetZ
            position.z += cos(Math.toRadians(rotation.y.toDouble())).toFloat() * offsetZ
        }
        if (offsetX != 0f) {
            position.x += sin(Math.toRadians((rotation.y - 90).toDouble())).toFloat() * -1.0f * offsetX
            position.z += cos(Math.toRadians((rotation.y - 90).toDouble())).toFloat() * offsetX
        }
        position.y += offsetY
    }
}