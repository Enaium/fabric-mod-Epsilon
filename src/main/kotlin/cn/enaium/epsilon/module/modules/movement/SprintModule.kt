package cn.enaium.epsilon.module.modules.movement

import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.module.ModuleAT
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@ModuleAT
class SprintModule : Module("Sprint", GLFW.GLFW_KEY_V, Category.MOVEMENT) {
    @EventAT
    fun onUpdate(updateEvent: UpdateEvent) {
        MinecraftClient.getInstance().player!!.isSprinting = true
    }
}