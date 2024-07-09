package me.ponktacology.majnkraft

import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*
import kotlin.math.cos
import kotlin.math.sin

data class Camera(
    val position: Vector3f = Vector3f(0f, 0f, 0f),
    val rotation: Vector3f = Vector3f(0f, 0f, 0f)
) {

    companion object {
        private const val MOVEMENT_SPEED = 7.0f
        private const val FLY_SPEED = 7.0f
        private const val MAX_PITCH = 89.0f
        private const val SENSITIVITY = 0.1f
    }

    private val mouseRot = Vector3f().zero()
    private val inputVec = Vector3f().zero()
    private val previousPosition = Vector3f(-1f, -1f, -1f)

    fun input(input: Input, deltaTime: Double) {
        inputVec.zero()

        if (input.isKeyPressed(GLFW_KEY_X)) {
            GLHelper.turnWireframe()
        } else if (input.isKeyPressed(GLFW_KEY_Z)) {
            GLHelper.disableWireframe()
        }

        if (input.isKeyPressed(GLFW_KEY_W)) {
            inputVec.z = -1f;
        } else if (input.isKeyPressed(GLFW_KEY_S)) {
            inputVec.z = 1f;
        }
        if (input.isKeyPressed(GLFW_KEY_A)) {
            inputVec.x = -1f;
        } else if (input.isKeyPressed(GLFW_KEY_D)) {
            inputVec.x = 1f;
        }
        if (input.isKeyPressed(GLFW_KEY_SPACE)) {
            inputVec.y = 1f;
        } else if (input.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            inputVec.y = -1f;
        }

        inputVec.mul(deltaTime.toFloat())

        if (inputVec.x != 0f || inputVec.y != 0f || inputVec.z != 0f) {

            movePosition(
                inputVec.x * MOVEMENT_SPEED,
                inputVec.y * FLY_SPEED,
                inputVec.z * MOVEMENT_SPEED
            )
        }

        if (input.deltaX != 0.0 || input.deltaY != 0.0) {
            mouseRot.zero()

            if (input.deltaY != 0.0) {
                mouseRot.x = input.deltaY.toFloat()
            }

            if (input.deltaX != 0.0) {
                mouseRot.y = input.deltaX.toFloat()
            }


            moveRotation(mouseRot.x * SENSITIVITY, mouseRot.y * SENSITIVITY, mouseRot.z)
        }
    }

    private fun moveRotation(offsetX: Float, offsetY: Float, offsetZ: Float) {
        rotation.x += offsetX
        rotation.y += offsetY
        rotation.z += offsetZ

        if (rotation.x > MAX_PITCH) {
            rotation.x = MAX_PITCH
        } else if (rotation.x < -MAX_PITCH) {
            rotation.x = -MAX_PITCH
        }
    }

    private fun movePosition(offsetX: Float, offsetY: Float, offsetZ: Float) {
        previousPosition.x = position.x
        previousPosition.y = position.y
        previousPosition.z = position.z

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

    fun hasMoved() = previousPosition != position
}