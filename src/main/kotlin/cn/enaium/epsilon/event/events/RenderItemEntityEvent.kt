package cn.enaium.epsilon.event.events

import cn.enaium.epsilon.event.Event
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.ItemEntity

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class RenderItemEntityEvent(
    val itemEntity: ItemEntity,
    val yaw: Float,
    val delta: Float,
    val matrixStack: MatrixStack,
    val vertexConsumerProvider: VertexConsumerProvider,
    val light: Int
) : Event(Type.PRE)