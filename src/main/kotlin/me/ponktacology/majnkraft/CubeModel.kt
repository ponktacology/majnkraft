package me.ponktacology.majnkraft

import java.nio.file.Path

object CubeModel {

    val model by lazy {
        ModelMaker.createModel(vertices, textures)
    }

    val texture by lazy {
        Texture(Path.of("textures/dirt.png"))
    }



    val vertices = floatArrayOf(

        //LEFT
        0.5f, 0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,
        0.5f, 0.5f, -0.5f,

        //RIGHT
        -0.5f, 0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, 0.5f,
        -0.5f, -0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f,
        -0.5f, 0.5f, -0.5f,

        //TOP
        -0.5f, 0.5f, 0.5f,
        -0.5f, 0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,
        0.5f, 0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f,


        //
        -0.5f, -0.5f, 0.5f,
        -0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, 0.5f,
        -0.5f, -0.5f, 0.5f,

        -0.5f, 0.5f, 0.5f,
        -0.5f, -0.5f, 0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f,

        -0.5f, 0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,
        -0.5f, 0.5f, -0.5f
    )

    val textures = floatArrayOf(
        // LEFT
        0f, 1f, // top-left
        0f, 0f, // bottom-left
        1f, 0f, // bottom-right
        1f, 0f, // bottom-right
        1f, 1f, // top-right
        0f, 1f, // top-left

        // RIGHT
        0f, 1f, // top-left
        0f, 0f, // bottom-left
        1f, 0f, // bottom-right
        1f, 0f, // bottom-right
        1f, 1f, // top-right
        0f, 1f, // top-left

        // TOP
        0f, 1f, // top-left
        0f, 0f, // top-right
        1f, 0f, // bottom-right
        1f, 0f, // bottom-right
        1f, 1f, // bottom-left
        0f, 1f, // top-left

        // BOTTOM
        0f, 1f, // top-left
        0f, 0f, // bottom-left
        1f, 0f, // bottom-right
        1f, 0f, // bottom-right
        1f, 1f, // top-right
        0f, 1f, // top-left

        // FRONT
        0f, 1f, // top-left
        0f, 0f, // bottom-left
        1f, 0f, // bottom-right
        1f, 0f, // bottom-right
        1f, 1f, // top-right
        0f, 1f, // top-left

        // BACK
        0f, 1f, // top-left
        0f, 0f, // bottom-left
        1f, 0f, // bottom-right
        1f, 0f, // bottom-right
        1f, 1f, // top-right
        0f, 1f  // top-left
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