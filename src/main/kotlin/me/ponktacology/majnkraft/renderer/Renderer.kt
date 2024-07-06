package me.ponktacology.majnkraft.renderer

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL


object Renderer {

    private var window = 0L
    private val errorCallback = GLFWErrorCallback.createPrint(System.err)

    fun start() {
        initContext()
        spin()

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
        MeshLoader.destroy()
    }

    private fun initContext() {
        if (!glfwInit()) {
            error("Unable to initialize GLFW")
        }

        window = glfwCreateWindow(1920, 1080, "Majnkraft", NULL, NULL)
        if (window == NULL) {
            error("Couldn't create a GLFW window")
        }

        glfwSetKeyCallback(window) { _, key, _, action, _ ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true)
        }

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

            // glfwSwapInterval(0)
            glfwMakeContextCurrent(window)
            glfwShowWindow(window)
        }
    }

    private fun spin() {
        GL.createCapabilities()

        glClearColor(0f, 0f, 0f, 1f)

        val vertices = arrayOf(
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
        ).toFloatArray()
        val indicies = arrayOf(
            0, 1, 2,
            2, 3, 0
        ).toIntArray()
        val testMesh = MeshLoader.createVao(vertices, indicies)

        var lastTime = System.nanoTime()
        var delta = 0.0
        var frames = 0
        var fpsTime: Long = 0
        while (!glfwWindowShouldClose(window)) {
            val now = System.nanoTime()
            delta += (now - lastTime) / 1000000000.0
            fpsTime += now - lastTime
            lastTime = now

            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            EntityRenderer.render(testMesh)

            glfwSwapBuffers(window)
            glfwPollEvents()

            frames++
            if (fpsTime >= 1000000000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                fpsTime = 0;
            }
        }
    }
}