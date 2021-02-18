package cn.enaium.epsilon.client.func.functions.world

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.event.events.UpdateEvent
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.IMC

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("FastPlace", category = Category.OTHER)
class FastPlaceFunc {
    @Event
    fun on(updateEvent: UpdateEvent) {
        IMC.mc.itemUseCooldown = 0
    }
}