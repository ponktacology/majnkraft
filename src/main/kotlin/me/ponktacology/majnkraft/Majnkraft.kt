package me.ponktacology.majnkraft

import me.ponktacology.majnkraft.renderer.Camera
import me.ponktacology.majnkraft.renderer.EntityRenderer
import me.ponktacology.majnkraft.renderer.GlobalRenderer
import me.ponktacology.majnkraft.renderer.model.Model
import me.ponktacology.majnkraft.renderer.model.ModelRegistry
import me.ponktacology.majnkraft.renderer.model.mesh.MeshLoader
import me.ponktacology.majnkraft.renderer.model.texture.TextureLoader
import me.ponktacology.majnkraft.world.World
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.io.path.Path

object Majnkraft {


    private val renderer = GlobalRenderer(EntityRenderer(Camera()))
    private val world = World(renderer)
    private var fpsTime: Long = 0
    private var renderTickTime = 0L
    fun run() {
        renderer.start()

        val texture = TextureLoader.loadTexture(java.nio.file.Path.of("textures/dirt.png"))
        val mesh = MeshLoader.createMesh(Cube.vertices, Cube.indices, Cube.uv)
        ModelRegistry.registerModel("cube", Model(mesh, texture))

        while (!renderer.hasStopped()) {
            val now = System.nanoTime()
            val delta = (now - renderTickTime) / 1_000_000_000.0f
            fpsTime += now - renderTickTime
            renderTickTime = now
            world.tick()
            renderer.render(delta)
        }
    }

}