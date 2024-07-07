package me.ponktacology.majnkraft.renderer

import me.ponktacology.majnkraft.Cube
import me.ponktacology.majnkraft.Majnkraft
import me.ponktacology.majnkraft.renderer.shader.Shader
import me.ponktacology.majnkraft.renderer.shader.StaticShader
import org.joml.Vector3f
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWCursorPosCallback
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import java.nio.file.Path


object Renderer {

    private val testShader by lazy {
        StaticShader(Path.of("shaders/vertex.glsl"), Path.of("shaders/fragment.glsl"))
    }
    private lateinit var errorCallback: GLFWErrorCallback
    private lateinit var keyBoardCallback: GLFWKeyCallback
    private lateinit var mouseCallback: GLFWCursorPosCallback

    private var window = 0L
    private val entities = mutableSetOf<Entity>()
    private val shaders = mutableSetOf<Shader>()
    private var width = 0
    private var height = 0

    lateinit var cubeModel: Model

    fun start() {
        initContext()
        val testTexture = Loader.loadTexture(Path.of("textures/dirt.png"))
        val testMesh = Loader.createVao(Cube.vertices, Cube.indices, Cube.uv)
        cubeModel = Model(testTexture, testMesh)

        testShader.start()
        val fov = Math.toRadians(60.0).toFloat()
        val aspectRatio = width / height.toFloat()
        val near = 0.01f
        val far = 10_000.0f

        val projectionMatrix = Translation.createProjectionMatrix(fov, aspectRatio, near, far)
        testShader.setProjectionMatrix(projectionMatrix)
        testShader.stop()

        shaders.add(testShader)
    }

    fun render(timeDelta: Float) {
        entities.clear()
        Majnkraft.world.loadedChunks.values.forEach { chunk ->
            chunk.blocks.forEachIndexed { x, it ->
                it.forEachIndexed { y, it ->
                    it.forEachIndexed { z, it ->
                        if (chunk.blocks[x][y][z] == 1) {
                            entities.add(
                                Entity(
                                    cubeModel,
                                    Vector3f(x + (chunk.x shl 4).toFloat(),
                                        y.toFloat(),
                                        z + (chunk.z shl 4).toFloat())
                                )
                            )
                        }
                    }
                }
            }
        }

        glClearColor(0f, 0f, 0f, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        Mouse.reset()
        shaders.forEach { it.start() }
        testShader.setViewMatrix(Translation.createViewMatrix(Majnkraft.camera))
        entities.forEach { EntityRenderer.render(it, testShader) }
        shaders.forEach { it.stop() }
        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    fun destroy() {
        shaders.forEach { it.cleanup() }
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
        Loader.destroy()
    }

    fun hasStopped() = glfwWindowShouldClose(window)

    fun isKeyPressed(key: Int) = glfwGetKey(window, key) == GLFW_PRESS

    private fun initContext() {
        if (!glfwInit()) {
            error("Unable to initialize GLFW")
        }

        window = glfwCreateWindow(1920, 1080, "Majnkraft", NULL, NULL)
        if (window == NULL) {
            error("Couldn't create a GLFW window")
        }

        errorCallback = GLFWErrorCallback.createPrint(System.err)
        glfwSetErrorCallback(errorCallback)

        keyBoardCallback = GLFWKeyCallback.create { window, key, scancode, action, mods ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true)
        }
        glfwSetKeyCallback(window, keyBoardCallback)

        mouseCallback = GLFWCursorPosCallback.create { window, x, y ->
            if (window != this.window) return@create
            Mouse.update(Mouse.currentX, Mouse.currentY, x, y)
        }

        glfwSetCursorPosCallback(window, mouseCallback)

        stackPush().use { stack ->
            val width = stack.mallocInt(1)
            val height = stack.mallocInt(1)

            glfwGetWindowSize(window, width, height)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor()) ?: error("Could not get video monitor")

            glfwSetWindowPos(
                window,
                (vidmode.width() - width.get(0)) / 2,
                (vidmode.height() - height.get(0)) / 2
            )

            this.width = width.get()
            this.height = height.get()

            glfwMakeContextCurrent(window)
            glfwSwapInterval(0)
            glfwShowWindow(window)
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        }


        GL.createCapabilities()

        glEnable(GL_DEPTH_TEST)
    }
}
