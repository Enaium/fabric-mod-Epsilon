package cn.enaium.epsilon.func.functions.movement

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.settings.DoubleSetting
import net.minecraft.util.math.Vec3d
import org.lwjgl.glfw.GLFW


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class FlightFunc : Func("Flight", GLFW.GLFW_KEY_G, Category.MOVEMENT) {

    private var speed = DoubleSetting(this, "Speed", 3.0, 1.0, 10.0);

    fun on(updateEvent: UpdateEvent) {

        MC.player!!.abilities.flying = false
        MC.player!!.flyingSpeed = speed.current.toFloat()

        MC.player!!.setVelocity(0.0, 0.0, 0.0)
        val velocity: Vec3d = MC.player!!.velocity

        if (MC.options.keyJump.isPressed) MC.player!!.velocity = velocity.add(0.0, speed.current, 0.0)

        if (MC.options.keySneak.isPressed) MC.player!!.velocity = velocity.subtract(0.0, speed.current, 0.0)
    }

    override fun onEnable() {

        super.onEnable()
    }

}