package me.ponktacology.majnkraft.renderer

import org.joml.Matrix4f
import org.joml.Vector3f

object Translation {

    fun createTransformationMatrix(
        translation: Vector3f,
        rotation: Vector3f,
        scale: Float = 1.0f
    ) = Matrix4f()
        .translate(translation)
        .rotateX(rotation.x)
        .rotateY(rotation.y)
        .rotateZ(rotation.z)
        .scale(scale)

    fun createProjectionMatrix(fov: Float, aspectRatio: Float, near: Float, far: Float) = Matrix4f()
        .perspective(fov, aspectRatio, near, far)

    fun createViewMatrix(camera: Camera) = Matrix4f()
        .identity()
        .translate(camera.position.mul(-1.0f, Vector3f()))
        .rotateX(camera.rotation.x)
        .rotateY(camera.rotation.y)
        .rotateZ(camera.rotation.z)
}