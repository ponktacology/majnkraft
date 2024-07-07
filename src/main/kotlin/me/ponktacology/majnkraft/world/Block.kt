package me.ponktacology.majnkraft.world

import me.ponktacology.majnkraft.renderer.Renderable
import me.ponktacology.majnkraft.renderer.model.Model
import me.ponktacology.majnkraft.renderer.model.ModelRegistry
import org.joml.Vector3f

data class Block(
    override val model: Model = ModelRegistry.getModel("cube"),
    override val position: Vector3f,
    override val rotation: Vector3f = Vector3f(0f, 0f, 0f),
    override var scale: Float = 1f
) : Renderable