package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.events.RenderItemEntityEvent

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module("NoItem", category = Category.RENDER)
class NoItemFunc {
    @Event
    fun on(renderItemEntityEvent: RenderItemEntityEvent) {
        renderItemEntityEvent.cancel = true
    }
}