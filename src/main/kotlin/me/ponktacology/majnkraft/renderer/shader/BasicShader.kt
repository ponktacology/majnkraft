package me.ponktacology.majnkraft.renderer.shader

import org.joml.Matrix4f
import java.nio.file.Path

class BasicShader(shaderFile: Path, fragmentFile: Path) : Shader(shaderFile, fragmentFile) {

    override fun bindAttributes() {
        bindAttribute("position", 0)
        bindAttribute("texturePos", 1)
    }

    override fun bindUniforms() {
        bindUniform("transformationMatrix")
        bindUniform("projectionMatrix")
    }

    fun setTransformationMatrix(transformationMatrix: Matrix4f) {
        super.setUniform("transformationMatrix", transformationMatrix)
    }

    fun setProjectionMatrix(projectionMatrix: Matrix4f) {
        super.setUniform("projectionMatrix", projectionMatrix)
    }


}