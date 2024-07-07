package me.ponktacology.majnkraft.renderer.shader

import org.joml.Matrix4f
import java.nio.file.Path

class StaticShader(shaderFile: Path, fragmentFile: Path) : Shader(shaderFile, fragmentFile) {

    private var transformationMatrixLocation = 0
    private var projectionMatrixLocation = 0
    private var viewMatrixLocation = 0

    override fun bindAttributes() {
        bindAttribute("position", 0)
        bindAttribute("texturePos", 1)
    }

    override fun getUniforms() {
        transformationMatrixLocation = getUniformLocation("transformationMatrix")
        projectionMatrixLocation = getUniformLocation("projectionMatrix")
        viewMatrixLocation = getUniformLocation("viewMatrix")
    }

    fun setTransformationMatrix(transformationMatrix: Matrix4f) {
        super.setUniform(transformationMatrixLocation, transformationMatrix)
    }

    fun setProjectionMatrix(projectionMatrix: Matrix4f) {
        super.setUniform(projectionMatrixLocation, projectionMatrix)
    }

    fun setViewMatrix(viewMatrix: Matrix4f) {
        super.setUniform(viewMatrixLocation, viewMatrix)
    }

}