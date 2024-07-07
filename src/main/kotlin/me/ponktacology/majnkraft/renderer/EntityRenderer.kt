package me.ponktacology.majnkraft.renderer

import me.ponktacology.majnkraft.renderer.shader.StaticShader
import org.lwjgl.opengl.GL30.*

object EntityRenderer {


    fun render(entity: Entity, shader: StaticShader) {
        val model = entity.model
        glBindVertexArray(model.mesh.id)
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        val transformationMatrix = Translation.createTransformationMatrix(entity.position, entity.rotation, entity.scale)
        shader.setTransformationMatrix(transformationMatrix)

        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, model.texture.id)
        glDrawElements(GL_TRIANGLES, model.mesh.indices, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glBindVertexArray(0)
    }

}