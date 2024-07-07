package me.ponktacology.majnkraft.renderer.model.texture

import de.matthiasmann.twl.utils.PNGDecoder
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30
import java.nio.ByteBuffer
import java.nio.file.Path

object TextureLoader {

    private val textureIds = mutableListOf<Int>()

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

        GL30.glGenerateMipmap(GL_TEXTURE_2D)

        return Texture(textureId)
    }

    fun destroy() {
        textureIds.forEach { glDeleteTextures(it) }
    }
}