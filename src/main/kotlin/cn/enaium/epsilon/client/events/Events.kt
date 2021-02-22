package cn.enaium.epsilon.client.events

import cn.enaium.cf4m.event.Listener
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
class BlockBreakingProgressEvent(val blockPos: BlockPos, val direction: Direction) : Listener(At.HEAD)
class MotionEvent(at: At, var yaw: Float, var pitch: Float, var ground: Boolean, var vec3d: Vec3d) : Listener(at)
class MouseScrollEvent(val windowHandle: Long, val up: Double, val down: Double) : Listener(At.HEAD)
class Render2DEvent(var matrixStack: MatrixStack, var partialTicks: Float) : Listener(At.HEAD)
class Render3DEvent(val tickDelta: Float, val limitTime: Long, val matrixStack: MatrixStack) : Listener(At.HEAD)
class RenderBlockEntityEvent(val blockEntity: BlockEntity) : Listener(At.HEAD)
class RenderItemEntityEvent(
    val itemEntity: ItemEntity,
    val yaw: Float,
    val delta: Float,
    val matrixStack: MatrixStack,
    val vertexConsumerProvider: VertexConsumerProvider,
    val light: Int
) : Listener(At.HEAD)

class RenderLivingEntityEvent : Listener(At.HEAD)
class ShouldDrawSideEvent(val blockState: BlockState) : Listener(At.HEAD) {
    var rendered: Boolean? = null
}

class TessellateBlockEvent(val blockState: BlockState) : Listener(At.HEAD)