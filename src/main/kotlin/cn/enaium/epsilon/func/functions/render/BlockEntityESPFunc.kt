package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.IMC
import cn.enaium.epsilon.event.events.Render3DEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.utils.BlockUtils
import cn.enaium.epsilon.utils.Render3DUtils
import net.minecraft.block.entity.BlockEntity
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
    private var blockEntityBox = 0

    override fun onEnable() {
        super.onEnable()
        blockEntityBox = GL11.glGenLists(1)
        GL11.glNewList(blockEntityBox, GL11.GL_COMPILE)
        val bb = Box(BlockPos.ORIGIN)
        Render3DUtils.drawOutlined(bb)
        GL11.glEndList()
    }

    override fun onDisable() {
        super.onDisable()
        GL11.glDeleteLists(blockEntityBox, 1)
        blockEntityBox = 0
    }

    fun onRender(render3DEvent: Render3DEvent) {
        IMC.world.entityList.forEachEntity {
            Render3DUtils.drawBox(BlockUtils.getBoundingBox(it.blockPos), Color.DARK_GRAY, blockEntityBox)
        }
    }
}