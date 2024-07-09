package me.ponktacology.majnkraft

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*
import java.nio.file.Path
import kotlin.system.measureNanoTime

object Main {

    private const val FOV = Math.PI.toFloat() / 3f

    private var width = 1920
    private var height = 1080
    private val projection = Matrix4f()
        .perspective(FOV, width / height.toFloat(), 0.1f, 10_000f)

    @JvmStatic
    fun main(args: Array<String>) {
        val window = GLContext.init(1920, 1080) {
            projection.perspective(FOV, width / height.toFloat(), 0.1f, 10_000f)
        }

        val view = Matrix4f()
            .translate(Vector3f(0f, 1f, 0f))

        val staticShader = Shader(Path.of("shaders/vertex.glsl"), Path.of("shaders/fragment.glsl"))

        staticShader.use {
            staticShader.setUniform("projection", projection)
        }

        val camera = Camera()
        val input = Input(window)

        val world = World(camera)

        val entityRenderer = EntityRenderer()

        var lastTitleUpdate = 0.0
        var lastTime = glfwGetTime()
        var frames = 0


        world.tick()

        while (!window.hasClosed()) {
            val currentTime = glfwGetTime()
            val deltaTime = currentTime - lastTime
            lastTitleUpdate += deltaTime
            lastTime = currentTime

            GLHelper.clear()

            camera.input(input, deltaTime)

            view.identity()
                .rotate(Math.toRadians(camera.rotation.x.toDouble()).toFloat(), Vector3f(1f, 0f, 0f))
                .rotate(Math.toRadians(camera.rotation.y.toDouble()).toFloat(), Vector3f(0f, 1f, 0f))
                .translate(-camera.position.x, -camera.position.y, -camera.position.z)

            staticShader.use {
                staticShader.setUniform("view", view)

                entityRenderer.renderEntities(world.cubes, staticShader)
            }

            glfwSwapBuffers(window.id)
            input.reset()
            glfwPollEvents()

            if (lastTitleUpdate >= 1) {
                println(measureNanoTime { world.tick() })
                lastTitleUpdate = 0.0
                window.title("Majnkraft $frames FPS")
                frames = 0
            }

            frames++
        }

        glfwTerminate()
    }


}