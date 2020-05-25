package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.Render3DEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.utils.BlockUtils
import cn.enaium.epsilon.utils.Render3DUtils
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import org.lwjgl.opengl.GL11
import java.awt.Color

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class BlockEntityESPFunc : Func("BlockEntityESP", 0, Category.RENDER) {

    override fun onEnable() {
        super.onEnable()
        GL11.glNewList(1, GL11.GL_COMPILE)
        val bb = Box(BlockPos.ORIGIN)
        Render3DUtils.drawOutlined(bb)
        GL11.glEndList()
    }

    override fun onDisable() {
        super.onDisable()
        GL11.glDeleteLists(1, 1)
    }

    @EventAT
    fun onRender(render3DEvent: Render3DEvent) {
        for (be in MC.world!!.blockEntities) {
            Render3DUtils.drawOutlinedBox(BlockUtils.getBoundingBox(be.pos), Color.DARK_GRAY)
        }
    }
}