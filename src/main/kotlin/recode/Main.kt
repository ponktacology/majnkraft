package recode

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray
import org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryUtil.NULL
import java.nio.file.Path
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        glfwInit()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

        val window = glfwCreateWindow(1920, 1080, "Learn", NULL, NULL)
        require(window != NULL) { "Failed to create the GLFW window" }

        glfwMakeContextCurrent(window)
        GL.createCapabilities()

        glViewport(0, 0, 1920, 1080)
        glfwSetFramebufferSizeCallback(window) { _, width, height ->
            glViewport(0, 0, width, height)
        }


        val vertices = floatArrayOf(
            // positions          // colors           // texture coords
            0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,   // top right
            0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f,   // bottom right
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,   // bottom left
            -0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f    // top left
        )

        val indeces = intArrayOf(
            0, 1, 2,
            3, 2, 0
        )


        val vao = glGenVertexArrays()
        glBindVertexArray(vao)

        val vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * Float.SIZE_BYTES, 0)
        glEnableVertexAttribArray(0)

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * Float.SIZE_BYTES, 3L * Float.SIZE_BYTES)
        glEnableVertexAttribArray(1)

        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * Float.SIZE_BYTES, 6L * Float.SIZE_BYTES)
        glEnableVertexAttribArray(2)

        val ebo = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indeces, GL_STATIC_DRAW)

        // RENDER TRIANGLES
        glUseProgram(0)

        val staticShader = Shader(Path.of("shaders/vertex.glsl"), Path.of("shaders/fragment.glsl"))
        val texture = Texture(Path.of("textures/dirt.png"))

        while (!glfwWindowShouldClose(window)) {
            processInput(window)

            glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            staticShader.use()

            glBindTexture(GL_TEXTURE_2D, texture.id);

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
            glDrawElements(GL_TRIANGLES, indeces.size, GL_UNSIGNED_INT, 0);

            glUseProgram(0)

            glfwSwapBuffers(window)
            glfwPollEvents()
        }

        glfwTerminate()
    }

    fun processInput(window: Long) {
        if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
            glfwSetWindowShouldClose(window, true);

    }

    fun createShader(code: String, type: Int): Int {
        val shader = glCreateShader(type)
        glShaderSource(shader, code)
        glCompileShader(shader)
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            error("Error compiling shader $code: ${glGetShaderInfoLog(shader)}")
        }
        return shader
    }

    fun turnWireframe() {
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE)
    }

    fun disableWireframe() {
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL)
    }

}