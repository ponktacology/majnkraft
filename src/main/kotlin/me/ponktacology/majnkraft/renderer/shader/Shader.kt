package me.ponktacology.majnkraft.renderer.shader

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import java.io.BufferedInputStream
import java.nio.file.Path


abstract class Shader(shaderFile: Path, fragmentFile: Path) {

    private val programId = glCreateProgram()
    private val shaderId = loadShader(shaderFile, GL_VERTEX_SHADER)
    private val fragmentId = loadShader(fragmentFile, GL_FRAGMENT_SHADER)
    private val buffer = BufferUtils.createFloatBuffer(16)
    private val uniforms = HashMap<String, Int>()

    init {
        glAttachShader(programId, shaderId)
        glAttachShader(programId, fragmentId)
        bindAttributes()
        glLinkProgram(programId)
        glValidateProgram(programId)
        bindUniforms()
    }

    abstract fun bindAttributes()

    abstract fun bindUniforms();

    fun bindUniform(name: String) {
        uniforms[name] = glGetUniformLocation(programId, name)
    }

    fun setUniform(name: String, matrix4f: Matrix4f) {
        val location = uniforms[name] ?: error("invalid uniform")
        glUniformMatrix4fv(location, false, matrix4f.get(buffer))
    }

    protected fun bindAttribute(variableName: String, attribute: Int) {
        glBindAttribLocation(programId, attribute, variableName)
    }

    fun start() {
        glUseProgram(programId)
    }

    fun stop() {
        glUseProgram(0)
    }

    private fun loadShader(path: Path, type: Int): Int {
        BufferedInputStream(this.javaClass.classLoader.getResourceAsStream(path.toString())).use {
            val code = String(it.readAllBytes())
            val shaderId = glCreateShader(type)
            glShaderSource(shaderId, code)
            glCompileShader(shaderId)

            if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
                error("Error compiling shader $shaderId: ${glGetShaderInfoLog(shaderId)}")
            }
            return shaderId
        }
    }

    fun cleanup() {
        stop()
        glDetachShader(programId, shaderId)
        glDetachShader(programId, fragmentId)
        glDeleteShader(shaderId)
        glDeleteShader(fragmentId)
        glDeleteProgram(programId)
    }
}