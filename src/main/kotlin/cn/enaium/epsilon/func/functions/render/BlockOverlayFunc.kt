package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.events.Render2DEvent
import cn.enaium.epsilon.event.events.Render3DEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.utils.BlockUtils
import cn.enaium.epsilon.utils.FontUtils
import cn.enaium.epsilon.utils.Render2DUtils
import cn.enaium.epsilon.utils.Render3DUtils
import net.minecraft.text.TranslatableText
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import org.lwjgl.opengl.GL11
import java.awt.Color


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class BlockOverlayFunc : Func("BlockOverlay", 0, Category.RENDER) {

    private var block = 0

    override fun onEnable() {
        super.onEnable()
        block = GL11.glGenLists(1)
        GL11.glNewList(block, GL11.GL_COMPILE)
        val bb = Box(BlockPos.ORIGIN)
        Render3DUtils.drawSolid(bb)
        GL11.glEndList()
    }

    override fun onDisable() {
        super.onDisable()
        GL11.glDeleteLists(block, 1)
        block = 0
    }

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