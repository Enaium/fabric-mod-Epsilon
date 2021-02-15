package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Disable
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.cf4m.setting.settings.EnableSetting
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.events.Render3DEvent
import cn.enaium.epsilon.client.utils.Render3DUtils
import net.minecraft.entity.Entity
import net.minecraft.entity.mob.SlimeEntity
import net.minecraft.entity.mob.WitherSkeletonEntity
import net.minecraft.util.math.Box
import org.lwjgl.opengl.GL11
import java.awt.Color

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("ESP", category = Category.RENDER)
class ESPFunc {

    @Setting
    private val witherSkeleton = EnableSetting(this, "WitherSkeleton", "Render WitherSkeleton", true)
    @Setting
    private val slime = EnableSetting(this, "Slime", "Render Slime", true)

    private var espBox = 0

    @Enable
    fun onEnable() {
        espBox = GL11.glGenLists(1)
        GL11.glNewList(espBox, GL11.GL_COMPILE)
        val bb = Box(-0.5, 0.0, -0.5, 0.5, 1.0, 0.5)
        Render3DUtils.drawOutlined(bb)
        GL11.glEndList()
    }

    @Disable
    fun onDisable() {
        GL11.glDeleteLists(espBox, 1)
        espBox = 0
    }

    @Event
    fun on(render3DEvent: Render3DEvent) {
        for (entity in getTargets()) {
            Render3DUtils.drawBox(entity, 0.5, render3DEvent.tickDelta, Color.CYAN, espBox)
        }
    }

    private fun getTargets(): ArrayList<Entity> {
        val entityList: ArrayList<Entity> = ArrayList()
        for (entity in MC.world!!.entities) {
            when (entity) {
                is WitherSkeletonEntity -> if (witherSkeleton.isEnable) entityList.add(entity)
                is SlimeEntity -> if (slime.isEnable) entityList.add(entity)
            }
        }
        return entityList
    }
}