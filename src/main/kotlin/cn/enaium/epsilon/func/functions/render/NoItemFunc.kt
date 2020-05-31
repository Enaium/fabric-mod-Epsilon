package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.event.events.RenderItemEntityEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class NoItemFunc : Func("NoItem", 0, Category.RENDER) {
    fun on(renderItemEntityEvent: RenderItemEntityEvent) {
        renderItemEntityEvent.cancel()
    }
}