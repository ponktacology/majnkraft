package me.ponktacology.majnkraft

import org.joml.Matrix4f
import org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray
import org.lwjgl.opengl.GL20.*

class EntityRenderer {

    fun renderEntities(entities: Collection<Entity>, staticShader: Shader) {
        if (entities.isEmpty()) return

        val entity = entities.first()

        glBindVertexArray(entity.mesh.vaoId)
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, entity.texture.id)

        entities.forEach {
            val model = Matrix4f().translate(it.position.x, it.position.y, it.position.z)
            staticShader.setUniform("model", model)

            glDrawArrays(GL_TRIANGLES, 0, entity.mesh.vertexCount)
        }

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glBindVertexArray(0)

    }

}