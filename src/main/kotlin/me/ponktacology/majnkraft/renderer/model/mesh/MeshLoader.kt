package me.ponktacology.majnkraft.renderer.model.mesh

import org.lwjgl.opengl.GL30.*

object MeshLoader {

    private val vaoIds = mutableListOf<Int>()
    private val vboIds = mutableListOf<Int>()

    fun createMesh(vertices: FloatArray, indices: IntArray, uv: FloatArray): Mesh {
        val vaoId = createVaoId()
        storeDataInAttributeList(vertices, 0, 3)
        storeDataInAttributeList(uv, 1, 2)
        bindIndicesBuffer(indices)
        glBindVertexArray(0)
        return Mesh(vaoId, indices.size)
    }

    private fun createVaoId() = glGenVertexArrays().also {
        glBindVertexArray(it)
        vaoIds.add(it)
    }

    private fun storeDataInAttributeList(data: FloatArray, attributeNumber: Int, dimensions: Int) {
        val vboId = glGenBuffers()
        vboIds.add(vboId)

        glBindBuffer(GL_ARRAY_BUFFER, vboId)
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW)
        glVertexAttribPointer(attributeNumber, dimensions, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    private fun bindIndicesBuffer(indices: IntArray) {
        val vboId = glGenBuffers()
        vboIds.add(vboId)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)
    }
}