package cn.enaium.epsilon.event.events

import cn.enaium.epsilon.event.Event
import net.minecraft.block.entity.BlockEntity

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class RenderBlockEntityEvent(val blockEntity: BlockEntity) : Event(Type.PRE)