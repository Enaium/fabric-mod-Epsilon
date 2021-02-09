package cn.enaium.epsilon.func.functions.movement

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.events.UpdateEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class WalkFunc : Func("AutoWalk", 0, Category.MOVEMENT) {

    fun on(updateEvent: UpdateEvent) {
        MC.options.keyForward.isPressed = true
    }

    override fun onDisable() {
        super.onDisable()
        MC.options.keyForward.isPressed = false
    }

}