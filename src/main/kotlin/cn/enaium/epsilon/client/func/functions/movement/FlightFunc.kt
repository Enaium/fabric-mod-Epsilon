package cn.enaium.epsilon.client.func.functions.movement

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.event.events.UpdateEvent
import cn.enaium.cf4m.module.Category
import cn.enaium.cf4m.setting.settings.DoubleSetting
import cn.enaium.epsilon.client.MC
import net.minecraft.util.math.Vec3d
import org.lwjgl.glfw.GLFW


/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("Flight", key = GLFW.GLFW_KEY_G, category = Category.MOVEMENT)
class FlightFunc {

    @Setting
    private var speed = DoubleSetting(this, "Speed", "Fly speed", 3.0, 1.0, 10.0)

    @Event
    fun on(updateEvent: UpdateEvent) {

        MC.player!!.abilities.flying = false
        MC.player!!.flyingSpeed = speed.current.toFloat()

        MC.player!!.setVelocity(0.0, 0.0, 0.0)
        val velocity: Vec3d = MC.player!!.velocity

        if (MC.options.keyJump.isPressed) MC.player!!.velocity = velocity.add(0.0, speed.current, 0.0)

        if (MC.options.keySneak.isPressed) MC.player!!.velocity = velocity.subtract(0.0, speed.current, 0.0)
    }
}