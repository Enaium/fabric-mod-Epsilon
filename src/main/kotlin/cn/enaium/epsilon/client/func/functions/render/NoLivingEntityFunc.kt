package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.events.RenderLivingEntityEvent

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module("NoLivingEntity", category = Category.RENDER)
class NoLivingEntityFunc {
    @Event
    fun on(renderLivingEntityEvent: RenderLivingEntityEvent) {
        renderLivingEntityEvent.cancel()
    }
}