package cn.enaium.epsilon.module.modules.movement

import cn.enaium.epsilon.event.EventTarget
import cn.enaium.epsilon.event.events.EventUpdate
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class SprintModule : Module("Sprint", GLFW.GLFW_KEY_V, Category.MOVEMENT) {
    @EventTarget
    fun onUpdate(eventUpdate: EventUpdate) {
        MinecraftClient.getInstance().player!!.isSprinting = true
    }
}