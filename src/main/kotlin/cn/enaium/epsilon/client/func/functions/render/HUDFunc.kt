package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.event.events.KeyboardEvent
import cn.enaium.cf4m.module.Category
import cn.enaium.cf4m.setting.SettingBase
import cn.enaium.cf4m.setting.settings.*
import cn.enaium.epsilon.client.Epsilon.AUTHOR
import cn.enaium.epsilon.client.Epsilon.NAME
import cn.enaium.epsilon.client.Epsilon.VERSION
import cn.enaium.epsilon.IMC
import cn.enaium.epsilon.client.MC
import cn.enaium.cf4m.CF4M
import cn.enaium.epsilon.client.events.MotionEvent
import cn.enaium.epsilon.client.events.Render2DEvent
import cn.enaium.epsilon.client.setting.BlockListSetting
import cn.enaium.epsilon.client.utils.ColorUtils
import cn.enaium.epsilon.client.utils.FontUtils.drawStringWithShadow
import cn.enaium.epsilon.client.utils.FontUtils.fontHeight
import cn.enaium.epsilon.client.utils.FontUtils.getWidth
import cn.enaium.epsilon.client.utils.Render2DUtils
import cn.enaium.epsilon.client.utils.Render2DUtils.scaledHeight
import cn.enaium.epsilon.client.utils.Render2DUtils.scaledWidth
import cn.enaium.epsilon.client.utils.Utils
import net.minecraft.util.Formatting
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module("HUD", key = GLFW.GLFW_KEY_O, category = Category.RENDER)
class HUDFunc {
    private val list = EnableSetting(this, "List", "", true)

    private val entityList = EnableSetting(this, "EntityList", "", false)

    private val coords = EnableSetting(this, "Coords", "", true)

    private val direction = EnableSetting(this, "Direction", "", true)

    private val fps = EnableSetting(this, "FPS", "", true)

    private val gameTime = EnableSetting(this, "GameTime", "", false)

    private val realTime = EnableSetting(this, "RealTime", "", false)

    private val ping = EnableSetting(this, "Ping", "", false)

    private val titleInfo = EnableSetting(this, "TitleInfo", "", false)


    private var yaw = 0.0F
    private var pitch = 0.0F

    @Event
    fun entityList(render2DEvent: Render2DEvent) {
        if (!entityList.enable)
            return

        val entities: MutableSet<String> = HashSet()
        for (e in MC.world!!.entities) {
            entities.add(e.javaClass.simpleName)
        }

        entities.sortedBy { getWidth(it) }

        var yStart = scaledHeight - fontHeight

        for (entity in entities) {
            val startX = scaledWidth - getWidth(entity) - 6
            drawStringWithShadow(render2DEvent.matrixStack, entity, startX + 3, yStart, Color.WHITE.rgb)
            yStart -= fontHeight + 4
        }
    }

    @Event
    fun motion(motionEvent: MotionEvent) {
        yaw = Utils.valueFix(motionEvent.yaw)
        pitch = Utils.valueFix(motionEvent.pitch)
    }

    @Event
    fun infoList(render2DEvent: Render2DEvent) {
        var infoY = 2
        val infoList: ArrayList<String> = ArrayList()

        if (coords.enable) {
            infoList.add(
                "XYZ:" + Formatting.AQUA + Utils.valueFix(MC.player!!.x) + "/" + Utils.valueFix(MC.player!!.y) + "/" + Utils.valueFix(
                    MC.player!!.z
                )
            )
        }

        if (direction.enable) {
            infoList.add("Face:${Formatting.BLUE}${MC.cameraEntity!!.horizontalFacing}[$yaw/$pitch]")
        }

        if (fps.enable) {
            infoList.add("FPS:${IMC.mc.currentFps}")
        }

        if (gameTime.enable) {
            val timeOfDay = MC.world!!.timeOfDay
            val timeTick = timeOfDay % 24000
            val timeHour = ((timeTick / 1000) + 6) % 24
            val timeMin = (timeTick / 16.6) % 60
            val timeSec = (timeTick / 0.27) % 60
            infoList.add("GameTime:${Formatting.GRAY}${timeHour.toInt()}:${timeMin.toInt()}:${timeSec.toInt()}")
        }

        if (realTime.enable) {
            infoList.add("RealTime:${Formatting.GRAY}${SimpleDateFormat("HH:mm:ss").format(Date())}")
        }

        if (ping.enable) {
            infoList.add("Ping:${MC.networkHandler!!.getPlayerListEntry(MC.player!!.uuid)!!.latency}")
        }

        infoList.sortedBy { getWidth(it) }

        for (s in infoList) {
            drawStringWithShadow(render2DEvent.matrixStack, s, 5, infoY, Color.WHITE.rgb)
            infoY += fontHeight + 4
        }

        if (titleInfo.enable) {
            IMC.mc.window.setTitle(Utils.clearFormat(infoList.toString()))
        }

    }

    @Event
    fun list(render2DEvent: Render2DEvent) {
        if (!list.enable)
            return

        var yStart = 1

        val functions = ArrayList<Any>()
        for (m in CF4M.INSTANCE.module.modules) {
            if (CF4M.INSTANCE.module.getEnable(m)) {
                functions.add(m)
            }
        }

        val mods: ArrayList<Any> = functions
        mods.sortByDescending { getWidth(getDisplayName(it)) }
        for (func in mods) {
            val startX = scaledWidth - getWidth(getDisplayName(func)) - 6
            drawStringWithShadow(render2DEvent.matrixStack, getDisplayName(func), startX + 3, yStart, Color.WHITE.rgb)
            yStart += fontHeight + 4
        }
    }

    private fun getDisplayName(module: Any): String {
        val name = CF4M.INSTANCE.module.getName(module)
        val tag = CF4M.INSTANCE.module.getValue<String>(module, "tag")
        return if (tag == null) {
            name
        } else {
            "$name|$tag"
        }
    }
}