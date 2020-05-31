package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.event.events.RenderItemEntityEvent
import cn.enaium.epsilon.event.events.RenderLivingEntityEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class NoLivingEntityFunc : Func("NoLivingEntity", 0, Category.RENDER) {
    fun on(renderLivingEntityEvent: RenderLivingEntityEvent) {
        renderLivingEntityEvent.cancel()
    }
}