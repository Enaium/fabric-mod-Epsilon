package cn.enaium.epsilon.func.funcs.movement

import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.func.FuncAT
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@FuncAT
class SprintFunc : Func("Sprint", GLFW.GLFW_KEY_V, Category.MOVEMENT) {
    @EventAT
    fun onUpdate(updateEvent: UpdateEvent) {
        MinecraftClient.getInstance().player!!.isSprinting = true
    }
}