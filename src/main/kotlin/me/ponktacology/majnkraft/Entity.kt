package me.ponktacology.majnkraft

import org.joml.Vector3f

interface Entity {
    val position: Vector3f
    val rotation: Vector3f
    val scale: Float
    val mesh: Mesh
    val texture: Texture
}