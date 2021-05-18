package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.events.Render3DEvent
import cn.enaium.epsilon.client.utils.BlockUtils
import cn.enaium.epsilon.client.utils.Render3DUtils
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import org.lwjgl.opengl.GL11
import java.awt.Color


/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("BlockOverlay",category = Category.RENDER)
class BlockOverlayFunc {
    private var block = 0

    @Enable
    fun onEnable() {
        block = GL11.glGenLists(1)
        GL11.glNewList(block, GL11.GL_COMPILE)
        val bb = Box(BlockPos.ORIGIN)
        Render3DUtils.drawSolid(bb)
        GL11.glEndList()
    }

    @Disable
    fun onDisable() {
        GL11.glDeleteLists(block, 1)
        block = 0
    }

    @Event
    fun render3d(render3DEvent: Render3DEvent) {
        if (MC.crosshairTarget!!.type == HitResult.Type.BLOCK) {
            Render3DUtils.drawBox(
                BlockUtils.getBoundingBox((MC.crosshairTarget as BlockHitResult).blockPos),
                Color.BLUE,
                block
            )
        }
    }
}