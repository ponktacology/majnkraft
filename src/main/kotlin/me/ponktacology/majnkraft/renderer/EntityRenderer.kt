package me.ponktacology.majnkraft.renderer

import me.ponktacology.majnkraft.renderer.shader.ShaderLoader
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL30.*


class EntityRenderer(val camera: Camera) {

    fun render(renderable: Renderable) {
        val model = renderable.model
        glBindVertexArray(model.mesh.id)
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        val shader = ShaderLoader.BASIC_SHADER
        val viewMatrix = getViewMatrix(camera)
        val transformationMatrix = getModelViewMatrix(renderable, viewMatrix)
        shader.setTransformationMatrix(transformationMatrix)

        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, model.texture.id)
        glDrawElements(GL_TRIANGLES, model.mesh.indices, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glBindVertexArray(0)
    }

    fun getModelViewMatrix(gameItem: Renderable, viewMatrix: Matrix4f): Matrix4f {
        val rotation = gameItem.rotation
        val modelViewMatrix = Matrix4f()
            .translate(gameItem.position)
            .rotateX(Math.toRadians(-rotation.x.toDouble()).toFloat())
            .rotateY(Math.toRadians(-rotation.y.toDouble()).toFloat())
            .rotateZ(Math.toRadians(-rotation.z.toDouble()).toFloat())
            .scale(gameItem.scale)
        val viewCurr = Matrix4f(viewMatrix)
        return viewCurr.mul(modelViewMatrix)
    }

    fun getViewMatrix(camera: Camera): Matrix4f {
        val viewMatrix = Matrix4f()
        val cameraPos = camera.position
        val rotation = camera.rotation

        viewMatrix.rotate(Math.toRadians(rotation.x.toDouble()).toFloat(), Vector3f(1f, 0f, 0f))
            .rotate(Math.toRadians(rotation.y.toDouble()).toFloat(), Vector3f(0f, 1f, 0f))

        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)
        return viewMatrix
    }

}