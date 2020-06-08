package cn.enaium.epsilon.event.events

import cn.enaium.epsilon.event.Event
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class BlockBreakingProgressEvent(val blockPos: BlockPos, val direction: Direction) : Event(Type.PRE)