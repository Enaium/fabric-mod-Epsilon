package cn.enaium.epsilon.module.modules.world

import cn.enaium.epsilon.Epsilon.IMC
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.module.ModuleAT
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@ModuleAT
class FastPlaceModule : Module("FastPlace", GLFW.GLFW_KEY_B, Category.WORLD) {
    @EventAT
    fun on(updateEvent: UpdateEvent) {
        IMC.setItemUseCooldown(0)
    }
}