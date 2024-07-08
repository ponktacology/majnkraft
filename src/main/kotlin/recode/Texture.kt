package recode

import de.matthiasmann.twl.utils.PNGDecoder
import org.lwjgl.opengl.ARBFramebufferObject.glGenerateMipmap
import org.lwjgl.opengl.GL11.*
import java.nio.ByteBuffer
import java.nio.file.Path

class Texture(path: Path) {

    val id = glGenTextures()

    init {
        val decoder = PNGDecoder(this.javaClass.classLoader.getResourceAsStream(path.toString()))
        val buffer = ByteBuffer.allocateDirect(4 * decoder.height * decoder.width)
        decoder.decodeFlipped(buffer, decoder.width * 4, PNGDecoder.Format.RGBA)
        buffer.flip()

        glBindTexture(GL_TEXTURE_2D, id)

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(
            GL_TEXTURE_2D, 0, GL_RGBA, decoder.width, decoder.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer
        )

        glGenerateMipmap(GL_TEXTURE_2D)
    }
}