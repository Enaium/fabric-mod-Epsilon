package cn.enaium.epsilon.event.events

import cn.enaium.epsilon.event.Event
import net.minecraft.block.BlockState

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class TessellateBlockEvent(val blockState: BlockState) : Event(Type.PRE)