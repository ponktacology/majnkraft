package me.ponktacology.majnkraft

import org.joml.Vector3f

data class Cube(
    override val position: Vector3f,
    override val mesh: Mesh,
    override val texture: Texture,
    override val rotation: Vector3f = Vector3f(),
    override val scale: Float = 1f
) : Entity