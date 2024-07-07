package me.ponktacology.majnkraft.renderer

import org.joml.Vector3f

data class Entity(
    val model: Model,
    val position: Vector3f = Vector3f(0.0f, 0.0f, 0f),
    val rotation: Vector3f = Vector3f(0f, 0f, 0f),
    var scale: Float = 1f
)