package me.ponktacology.majnkraft.renderer

import me.ponktacology.majnkraft.renderer.model.Model
import org.joml.Vector3f

interface Renderable {
    val model: Model
    val position: Vector3f
    val rotation: Vector3f
    var scale: Float
}