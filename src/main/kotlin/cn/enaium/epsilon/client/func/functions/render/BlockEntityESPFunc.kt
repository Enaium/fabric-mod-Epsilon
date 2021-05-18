package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.IMC
import cn.enaium.epsilon.client.events.Render3DEvent
import cn.enaium.epsilon.client.utils.BlockUtils
import cn.enaium.epsilon.client.utils.Render3DUtils
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
@Module("BlockEntityESP",category = Category.RENDER)
class BlockEntityESPFunc {
    private var blockEntityBox = 0

    @Enable
    fun onEnable() {
        blockEntityBox = GL11.glGenLists(1)
        GL11.glNewList(blockEntityBox, GL11.GL_COMPILE)
        val bb = Box(BlockPos.ORIGIN)
        Render3DUtils.drawOutlined(bb)
        GL11.glEndList()
    }

    @Disable
    fun onDisable() {
        GL11.glDeleteLists(blockEntityBox, 1)
        blockEntityBox = 0
    }

    @Event
    fun onRender(render3DEvent: Render3DEvent) {
        for (blockEntity in IMC.world.blockEntityTickers) {
            Render3DUtils.drawBox(BlockUtils.getBoundingBox(blockEntity.pos), Color.DARK_GRAY, blockEntityBox)
        }
    }
}