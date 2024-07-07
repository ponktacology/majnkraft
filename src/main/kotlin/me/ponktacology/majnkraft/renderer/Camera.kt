package me.ponktacology.majnkraft.renderer

import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*
import kotlin.math.cos
import kotlin.math.sin

const val SPEED = 5f
const val SENSITIVITY = 1f

data class Camera(val position: Vector3f = Vector3f(0f, 0f, 0f), val rotation: Vector3f = Vector3f(0f, 0f, 0f)) {

    private var moveAt = 0f

    fun tick(timeDelta: Float) {
        if (Renderer.isKeyPressed(GLFW_KEY_W)) {
            moveAt = timeDelta * -SPEED
        } else if (Renderer.isKeyPressed(GLFW_KEY_S)) {
            moveAt = timeDelta * SPEED
        } else {
            moveAt = 0.0f;
        }

        rotation.add(
            Vector3f(
                timeDelta * Mouse.deltaY.toFloat() * -SENSITIVITY,
                timeDelta * Mouse.deltaX.toFloat() * SENSITIVITY,
                0f
            )
        )

        val deltaX = -(moveAt * sin(Math.toRadians(rotation.y.toDouble())).toFloat())
        val deltaY = moveAt * sin(Math.toRadians(rotation.y.toDouble())).toFloat()
        val deltaZ = moveAt * cos(Math.toRadians(rotation.y.toDouble())).toFloat()


        position.add(Vector3f(deltaX, deltaY, deltaZ))

        println(position)
    }
}