package me.ponktacology.majnkraft

import me.ponktacology.majnkraft.renderer.Camera
import me.ponktacology.majnkraft.renderer.Renderer
import me.ponktacology.majnkraft.world.World

object Majnkraft {

    val camera = Camera()
    var lastTime = System.nanoTime()
    var frames = 0
    var fpsTime = 0L
    val world = World()

    fun run() {
        Renderer.start()

        while (!Renderer.hasStopped()) {
            tick()
        }

        stop()
    }

    private fun stop() {
        Renderer.destroy()
    }

    private fun tick() {
        val now = System.nanoTime()
        val delta = (now - lastTime) / 1_000_000_000.0f
        fpsTime += now - lastTime
        lastTime = now

        camera.tick(delta)
        world.tick()

        Renderer.render(delta)

        frames++
        if (fpsTime >= 1000000000) {
            System.out.println("FPS: " + frames);
            frames = 0;
            fpsTime = 0;
        }
    }
}