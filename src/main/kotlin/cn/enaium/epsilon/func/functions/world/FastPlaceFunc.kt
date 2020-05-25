package cn.enaium.epsilon.func.functions.world

import cn.enaium.epsilon.IMC
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class FastPlaceFunc : Func("FastPlace", GLFW.GLFW_KEY_B, Category.WORLD) {
    @EventAT
    fun on(updateEvent: UpdateEvent) {
        IMC.mc.itemUseCooldown = 0
    }
}