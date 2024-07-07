package me.ponktacology.majnkraft.renderer.model

object ModelRegistry {
    private val models = mutableMapOf<String, Model>()

    fun registerModel(name: String, model: Model) {
        models[name] = model
    }

    fun getModel(name: String) = models[name] ?: error("invalid model name $name")
}