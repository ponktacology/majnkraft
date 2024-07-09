package me.ponktacology.majnkraft

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import java.io.BufferedInputStream
import java.nio.file.Path

class Shader(vertexPath: Path, fragmentPath: Path) {

    private val programId: Int = glCreateProgram()
    private val floatBuffer = BufferUtils.createFloatBuffer(16)

    init {
        val vertex = loadShader(vertexPath, GL_VERTEX_SHADER)
        val fragment = loadShader(fragmentPath, GL_FRAGMENT_SHADER)

        glAttachShader(programId, vertex)
        glAttachShader(programId, fragment)

        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            error("Error linking program $programId: ${glGetProgramInfoLog(programId)}")
        }

        glDeleteShader(vertex)
        glDeleteShader(fragment)
    }

    fun use(action: () -> Unit) {
        glUseProgram(programId)
        action()
    }


    private fun loadShader(path: Path, type: Int): Int {
        BufferedInputStream(this.javaClass.classLoader.getResourceAsStream(path.toString())).use {
            val code = String(it.readAllBytes())

            return createShader(code, type)
        }
    }

    fun setUniform(name: String, mat4: Matrix4f) {
        glUniformMatrix4fv(glGetUniformLocation(programId, name), false, mat4.get(floatBuffer))
    }

    private fun createShader(code: String, type: Int): Int {
        val shader = glCreateShader(type)
        glShaderSource(shader, code)

        glCompileShader(shader)
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            error("Error compiling shader $code: ${glGetShaderInfoLog(shader)}")
        }

        return shader
    }
}