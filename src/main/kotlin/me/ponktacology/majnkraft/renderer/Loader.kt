package me.ponktacology.majnkraft.renderer

import de.matthiasmann.twl.utils.PNGDecoder
import me.ponktacology.majnkraft.renderer.model.Mesh
import org.lwjgl.opengl.GL30.*
import java.nio.ByteBuffer
import java.nio.file.Path

object Loader {

    private val textureIds = mutableListOf<Int>()
    private val vaoIds = mutableListOf<VaoId>()
    private val vboIds = mutableListOf<VboId>()

    fun loadTexture(path: Path): Texture {
        val decoder = PNGDecoder(this.javaClass.classLoader.getResourceAsStream(path.toString()))
        val buffer = ByteBuffer.allocateDirect(4 * decoder.height * decoder.width)
        decoder.decode(buffer, decoder.width * 4, PNGDecoder.Format.RGBA)
        buffer.flip()

        val textureId = glGenTextures()
        textureIds.add(textureId)

        glBindTexture(GL_TEXTURE_2D, textureId)
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(
            GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(),
            decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer
        )

        glGenerateMipmap(GL_TEXTURE_2D)

        return Texture(textureId)
    }

    fun createVao(vertices: FloatArray, indicies: IntArray, uv: FloatArray): Mesh {
        val vaoId = createVaoId()
        storeDataInAttributeList(vertices, 0, 3)
        storeDataInAttributeList(uv, 1, 2)
        bindIndicesBuffer(indicies)
        glBindVertexArray(0)
        return Mesh(vaoId, indicies.size)
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

    fun destroy() {
        vaoIds.forEach { glDeleteVertexArrays(it) }
        vboIds.forEach { glDeleteBuffers(it) }
        textureIds.forEach { glDeleteTextures(it) }
    }
}