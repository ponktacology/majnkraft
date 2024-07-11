package me.ponktacology.majnkraft

import java.nio.file.Path

object CubeModel {


    val texture by lazy {
        Texture(Path.of("textures/dirt.png"))
    }


    val vertices = mapOf(
        CubeFace.LEFT to floatArrayOf(
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, -0.5f
        ),
        CubeFace.RIGHT to floatArrayOf(
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
        ),
        CubeFace.TOP to floatArrayOf(
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
        ),
        CubeFace.BOTTOM to floatArrayOf(
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
        ),
        CubeFace.FRONT to floatArrayOf(
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
        ),
        CubeFace.BACK to floatArrayOf(
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f
        )
    )


    val textures = mapOf(
        // LEFT
        CubeFace.LEFT to floatArrayOf(
            0f, 1f, // top-left
            0f, 0f, // bottom-left
            1f, 0f, // bottom-right
            1f, 0f, // bottom-right
            1f, 1f, // top-right
            0f, 1f, // top-left)
        ),

        CubeFace.RIGHT to floatArrayOf(
// RIGHT
            0f, 1f, // top-left
            0f, 0f, // bottom-left
            1f, 0f, // bottom-right
            1f, 0f, // bottom-right
            1f, 1f, // top-right
            0f, 1f, // top-left)
        ),

        CubeFace.TOP to floatArrayOf(
// TOP
            0f, 1f, // top-left
            0f, 0f, // top-right
            1f, 0f, // bottom-right
            1f, 0f, // bottom-right
            1f, 1f, // bottom-left
            0f, 1f, // top-left)
        ),

        // BOTTOM
        CubeFace.BOTTOM to floatArrayOf(
            0f, 1f, // top-left
            0f, 0f, // bottom-left
            1f, 0f, // bottom-right
            1f, 0f, // bottom-right
            1f, 1f, // top-right
            0f, 1f, // top-left)
        ),

        // FRONT
        CubeFace.FRONT to floatArrayOf(
            0f, 1f, // top-left
            0f, 0f, // bottom-left
            1f, 0f, // bottom-right
            1f, 0f, // bottom-right
            1f, 1f, // top-right
            0f, 1f, // top-left)
        ),

        // BACK
        CubeFace.BACK to floatArrayOf(
            0f, 1f, // top-left
            0f, 0f, // bottom-left
            1f, 0f, // bottom-right
            1f, 0f, // bottom-right
            1f, 1f, // top-right
            0f, 1f  // top-left)
        )
    )


    val indeces = intArrayOf(
        0, 1, 3,
        3, 1, 2,
        4, 5, 7,
        7, 5, 6,
        8, 9, 11,
        11, 9, 10,
        12, 13, 15,
        15, 13, 14,
        16, 17, 19,
        19, 17, 18,
        20, 21, 23,
        23, 21, 22
    )
}