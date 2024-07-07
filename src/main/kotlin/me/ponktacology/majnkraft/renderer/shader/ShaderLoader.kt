package me.ponktacology.majnkraft.renderer.shader

import java.nio.file.Path

object ShaderLoader {

    val BASIC_SHADER by lazy {
        BasicShader(Path.of("shaders/vertex.glsl"), Path.of("shaders/fragment.glsl"))
    }
}