package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.events.RenderItemEntityEvent
import cn.enaium.epsilon.client.cf4m
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import java.util.*


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module("ItemPhysics", category = Category.RENDER)
class ItemPhysicsFunc {
    val random = Random();

    @Event
    fun on(renderItemEntityEvent: RenderItemEntityEvent) {

        renderItemEntityEvent.cancel()

        if (cf4m.module.getEnable(cf4m.module.getModule("NoItem")))
            return

        val itemStack: ItemStack = renderItemEntityEvent.itemEntity.stack

        val seed =
            if (itemStack.isEmpty) 187 else Item.getRawId(itemStack.item) + itemStack.damage
        random.setSeed(seed.toLong())

        renderItemEntityEvent.matrixStack.push()
        val bakedModel = MC.itemRenderer.getHeldItemModel(
            itemStack,
            renderItemEntityEvent.itemEntity.world,
            null,
            renderItemEntityEvent.light
        )
        val hasDepthInGui = bakedModel.hasDepth()

        val renderCount = getRenderedAmount(itemStack)

        renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(1.571f))

        val rotator = Rotation()
        if (!renderItemEntityEvent.itemEntity.isOnGround && !renderItemEntityEvent.itemEntity.isSubmergedInWater) {
            val rotation =
                (renderItemEntityEvent.itemEntity.age + renderItemEntityEvent.delta) / 20.0F + renderItemEntityEvent.itemEntity.hoverHeight
            renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(rotation))
            rotator.setRotation(Vec3d(0.0, 0.0, rotation.toDouble()))
        } else {
            renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(rotator.getRotation().z.toFloat()))
        }

        renderItemEntityEvent.matrixStack.translate(0.0, 0.0, -0.01)
        if (renderItemEntityEvent.itemEntity.stack.item is BlockItem) {
            renderItemEntityEvent.matrixStack.translate(0.0, 0.0, -0.12)
        }

        val scaleX = bakedModel.transformation.ground.scale.x
        val scaleY = bakedModel.transformation.ground.scale.y
        val scaleZ = bakedModel.transformation.ground.scale.z

        var x: Double
        var y: Double
        if (!hasDepthInGui) {
            val r = -0.0f * renderCount.toFloat() * 0.5 * scaleX
            x = -0.0f * renderCount.toFloat() * 0.5 * scaleY
            y = -0.09375f * renderCount.toFloat() * 0.5 * scaleZ
            renderItemEntityEvent.matrixStack.translate(r, x, y)
        }

        for (u in 0 until renderCount) {
            renderItemEntityEvent.matrixStack.push()

            if (u > 0) {
                if (hasDepthInGui) {
                    x = (random.nextFloat() * 2.0 - 1.0) * 0.15
                    y = (random.nextFloat() * 2.0 - 1.0) * 0.15
                    val z = (random.nextFloat() * 20f - 1.0) * 0.15
                    renderItemEntityEvent.matrixStack.translate(x, y, z)
                } else {
                    x = (random.nextFloat() * 2.0 - 1.0) * 0.15 * 0.5
                    y = (random.nextFloat() * 2.0 - 1.0) * 0.15 * 0.5
                    renderItemEntityEvent.matrixStack.translate(x, y, 0.0)
                    renderItemEntityEvent.matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(random.nextFloat()))
                }
            }

            MC.itemRenderer.renderItem(
                itemStack,
                ModelTransformation.Mode.GROUND,
                false,
                renderItemEntityEvent.matrixStack,
                renderItemEntityEvent.vertexConsumerProvider,
                renderItemEntityEvent.light,
                OverlayTexture.DEFAULT_UV,
                bakedModel
            )

            renderItemEntityEvent.matrixStack.pop()

            if (!hasDepthInGui) {
                renderItemEntityEvent.matrixStack.translate(0.0 * scaleX, 0.0 * scaleY, 0.0625 * scaleZ)
            }
        }

        renderItemEntityEvent.matrixStack.pop()
    }

    private fun getRenderedAmount(stack: ItemStack): Int {
        var i = 1
        when {
            stack.count > 48 -> {
                i = 5
            }
            stack.count > 32 -> {
                i = 4
            }
            stack.count > 16 -> {
                i = 3
            }
            stack.count > 1 -> {
                i = 2
            }
        }
        return i
    }

    class Rotation {
        private var rotation = Vec3d(0.0, 0.0, 0.0)

        fun getRotation(): Vec3d {
            return rotation
        }

        fun setRotation(rotation: Vec3d) {
            this.rotation = rotation
        }

        fun addRotation(rotation: Vec3d) {
            this.rotation.add(rotation)
        }

        fun addRotation(x: Double, y: Double, z: Double) {
            rotation.add(x, y, z)
        }
    }
}