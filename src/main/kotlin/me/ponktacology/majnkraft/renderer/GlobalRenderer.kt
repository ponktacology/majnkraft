package me.ponktacology.majnkraft.renderer

import me.ponktacology.majnkraft.Input
import me.ponktacology.majnkraft.renderer.model.mesh.MeshLoader
import me.ponktacology.majnkraft.renderer.model.texture.TextureLoader
import me.ponktacology.majnkraft.renderer.shader.ShaderLoader
import org.joml.Matrix4f
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import java.util.concurrent.ConcurrentHashMap


class GlobalRenderer(private val entityRenderer: EntityRenderer) {

    companion object {
        private const val FOV = 60 / 180f * Math.PI.toFloat()
        private const val NEAR = 0.01f
        private const val FAR = 10_000.0f
    }

    private lateinit var errorCallback: GLFWErrorCallback

    private lateinit var window: Window
    private lateinit var input: Input
    private val renderables = ArrayList<Renderable>()


    fun start() {
        initContext()
        input.init()

        ShaderLoader.BASIC_SHADER.start()

        val aspectRatio = window.width / window.height.toFloat()
        val projectionMatrix = Matrix4f().perspective(FOV, aspectRatio, NEAR, FAR)
        ShaderLoader.BASIC_SHADER.setProjectionMatrix(projectionMatrix)

        ShaderLoader.BASIC_SHADER.stop()
    }

    fun render(timeDelta: Float) {
        glClearColor(0f, 0f, 0f, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        entityRenderer.camera.input(input)
        Mouse.reset()

        ShaderLoader.BASIC_SHADER.start()

        renderables.forEach { entityRenderer.render(it) }

        ShaderLoader.BASIC_SHADER.stop()

        glfwSwapBuffers(window.id)
        glfwPollEvents()
    }

    private fun initContext() {
        if (!glfwInit()) {
            error("Unable to initialize GLFW")
        }

        val windowId = glfwCreateWindow(1920, 1080, "Majnkraft", NULL, NULL)
        if (windowId == NULL) {
            error("Couldn't create a GLFW window")
        }

        errorCallback = GLFWErrorCallback.createPrint(System.err)
        glfwSetErrorCallback(errorCallback)

        stackPush().use { stack ->
            val width = stack.mallocInt(1)
            val height = stack.mallocInt(1)

            glfwGetWindowSize(windowId, width, height)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor()) ?: error("Could not get video monitor")

            glfwSetWindowPos(
                windowId,
                (vidmode.width() - width.get(0)) / 2,
                (vidmode.height() - height.get(0)) / 2
            )

            window = Window(windowId, width.get(), height.get())
            input = Input(window)
            glfwMakeContextCurrent(windowId)
            glfwSwapInterval(0)
            glfwShowWindow(windowId)
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        }

        GL.createCapabilities()
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_CULL_FACE)
        glCullFace(GL_FRONT)
    }

    fun destroy() {
        TextureLoader.destroy()

        glfwFreeCallbacks(window.id);
        glfwDestroyWindow(window.id);
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
        TextureLoader.destroy()
    }

    fun hasStopped() = glfwWindowShouldClose(window.id)

    fun addRenderable(renderable: Renderable) {
        renderables.add(renderable)
    }

    fun removeRenderable(renderable: Renderable) {
        renderables.remove(renderable)
    }

    fun camera() = entityRenderer.camera
}
