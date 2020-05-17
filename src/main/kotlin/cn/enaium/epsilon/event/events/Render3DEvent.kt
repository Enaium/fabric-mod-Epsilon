package cn.enaium.epsilon.event.events

import cn.enaium.epsilon.event.Event
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class Render3DEvent(val tickDelta: Float, val limitTime: Long, val matrixStack: MatrixStack) : Event(Type.PRE)