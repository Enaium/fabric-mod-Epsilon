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
class BlockBreakingProgressEvent(val blockPos: BlockPos, val direction: Direction) : Listener()
class MotioningEvent(var yaw: Float, var pitch: Float, var ground: Boolean, var vec3d: Vec3d) : Listener()
class MotionedEvent(var yaw: Float, var pitch: Float, var ground: Boolean, var vec3d: Vec3d) : Listener()
class MouseScrollEvent(val windowHandle: Long, val up: Double, val down: Double) : Listener()
class Rendering2DEvent(var matrixStack: MatrixStack, var partialTicks: Float) : Listener()
class Rendered2DEvent(var matrixStack: MatrixStack, var partialTicks: Float) : Listener()
class Render3DEvent(val tickDelta: Float, val limitTime: Long, val matrixStack: MatrixStack) : Listener()
class RenderBlockEntityEvent(val blockEntity: BlockEntity) : Event()
class RenderItemEntityEvent(
    val itemEntity: ItemEntity,
    val yaw: Float,
    val delta: Float,
    val matrixStack: MatrixStack,
    val vertexConsumerProvider: VertexConsumerProvider,
    val light: Int
) : Event()

class RenderLivingEntityEvent : Event()
class ShouldDrawSideEvent(val blockState: BlockState) : Listener() {
    var rendered: Boolean? = null
}

class TessellateBlockEvent(val blockState: BlockState) : Event()
class KeyboardEvent(val key: Int) : Listener()
open class Event : Listener() {
    var cancel: Boolean = false
}