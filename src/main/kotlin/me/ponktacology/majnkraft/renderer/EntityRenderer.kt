package me.ponktacology.majnkraft.renderer

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.*

object EntityRenderer {

    fun render(mesh: Mesh) {
        glBindVertexArray(mesh.id)
        glEnableVertexAttribArray(0)
        GL11.glDrawElements(GL_TRIANGLES, mesh.indicies, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
    }

}