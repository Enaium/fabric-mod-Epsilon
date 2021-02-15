package cn.enaium.epsilon.client.events

import cn.enaium.cf4m.event.EventBase
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.ItemEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class BlockBreakingProgressEvent(val blockPos: BlockPos, val direction: Direction) : EventBase(Type.PRE)
class MotionEvent(type: Type, var yaw: Float, var pitch: Float, var ground: Boolean, var vec3d: Vec3d) : EventBase(type)
class MouseScrollEvent(val windowHandle: Long, val up: Double, val down: Double) : EventBase(Type.PRE)
class Render2DEvent(var matrixStack: MatrixStack, var partialTicks: Float) : EventBase(Type.PRE)
class Render3DEvent(val tickDelta: Float, val limitTime: Long, val matrixStack: MatrixStack) : EventBase(Type.PRE)
class RenderBlockEntityEvent(val blockEntity: BlockEntity) : EventBase(Type.PRE)
class RenderItemEntityEvent(
    val itemEntity: ItemEntity,
    val yaw: Float,
    val delta: Float,
    val matrixStack: MatrixStack,
    val vertexConsumerProvider: VertexConsumerProvider,
    val light: Int
) : EventBase(Type.PRE)

class RenderLivingEntityEvent : EventBase(Type.PRE)
class ShouldDrawSideEvent(val blockState: BlockState) : EventBase(Type.PRE) {
    var rendered: Boolean? = null
}

class TessellateBlockEvent(val blockState: BlockState) : EventBase(Type.PRE)