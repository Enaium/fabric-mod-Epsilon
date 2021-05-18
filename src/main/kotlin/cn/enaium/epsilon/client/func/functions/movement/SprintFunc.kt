package cn.enaium.epsilon.client.func.functions.movement

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.events.MotioningEvent
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("Sprint", key = GLFW.GLFW_KEY_V, category = Category.MOVEMENT)
class SprintFunc {
    @Event
    fun onUpdate(motioningEvent: MotioningEvent) {
        MinecraftClient.getInstance().player!!.isSprinting = true
    }
}