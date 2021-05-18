package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.cf4m.provider.ModuleProvider
import cn.enaium.cf4m.provider.SettingProvider
import cn.enaium.epsilon.client.Epsilon
import cn.enaium.epsilon.client.Epsilon.AUTHOR
import cn.enaium.epsilon.client.Epsilon.NAME
import cn.enaium.epsilon.client.Epsilon.VERSION
import cn.enaium.epsilon.client.IMC
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.events.KeyboardEvent
import cn.enaium.epsilon.client.events.MotioningEvent
import cn.enaium.epsilon.client.events.Rendered2DEvent
import cn.enaium.epsilon.client.events.Rendering2DEvent
import cn.enaium.epsilon.client.func.Func
import cn.enaium.epsilon.client.setting.*
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
import java.awt.Color
import java.text.SimpleDateFormat
import java.util.*
import org.lwjgl.opengl.GL11
import kotlin.math.ceil

import java.util.ArrayList


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module("HUD", key = GLFW.GLFW_KEY_O, category = Category.RENDER)
class HUDFunc() {

    @Setting("TabGUI")
    private val tabGUI = EnableSetting(true)

    @Setting("Log")
    private val logo = EnableSetting(true)

    @Setting("List")
    private val list = EnableSetting(true)

    @Setting("EntityList")
    private val entityList = EnableSetting(false)

    @Setting("Coords")
    private val coords = EnableSetting(true)

    @Setting("Direction")
    private val direction = EnableSetting(true)

    @Setting("FPS")
    private val fps = EnableSetting(true)

    @Setting("GameTime")
    private val gameTime = EnableSetting(false)

    @Setting("RealTime")
    private val realTime = EnableSetting(false)

    @Setting("Ping")
    private val ping = EnableSetting(false)

    @Setting("TitleInfo")
    private val titleInfo = EnableSetting(false)

    @Setting("KeyStrokes")
    private val keyStrokes = EnableSetting(true)

    private var yaw = 0.0F
    private var pitch = 0.0F

    private var categoryValues: ArrayList<Category> = ArrayList()
    private var currentCategoryIndex = 0
    private var currentModIndex = 0
    private var currentSettingIndex = 0

    private var editMode = false

    private var screen = 0

    init {
        categoryValues = ArrayList()
        currentCategoryIndex = 0
        currentModIndex = 0
        currentSettingIndex = 0
        editMode = false
        screen = 0
        categoryValues.addAll(Category.values())
    }

    @Event
    fun entityList(rendering2DEvent: Rendering2DEvent) {
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
            drawStringWithShadow(rendering2DEvent.matrixStack, entity, startX + 3, yStart, Color.WHITE.rgb)
            yStart -= fontHeight + 4
        }
    }

    @Event
    fun motion(motioningEvent: MotioningEvent) {
        yaw = Utils.valueFix(motioningEvent.yaw)
        pitch = Utils.valueFix(motioningEvent.pitch)
    }

    @Event
    fun logo(rendering2DEvent: Rendering2DEvent) {
        if (!logo.enable)
            return
        GL11.glScaled(2.0, 2.0, 2.0)
        val i = drawStringWithShadow(rendering2DEvent.matrixStack, NAME, 0, 0, rainbow(0))
        GL11.glScaled(0.5, 0.5, 0.5)
        drawStringWithShadow(rendering2DEvent.matrixStack, VERSION, i * 2, 0, rainbow(100))
        drawStringWithShadow(rendering2DEvent.matrixStack, "by $AUTHOR", i * 2, fontHeight, rainbow(200))
    }

    @Event
    fun infoList(rendering2DEvent: Rendering2DEvent) {
        val infoList: ArrayList<String> = ArrayList()
        var infoY = 100

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
            drawStringWithShadow(rendering2DEvent.matrixStack, s, 0, infoY, Color.WHITE.rgb)
            infoY += fontHeight + 4
        }

        if (titleInfo.enable) {
            IMC.mc.window.setTitle(Utils.clearFormat(infoList.toString()))
        }

    }

    @Event
    fun list(rendering2DEvent: Rendering2DEvent) {
        if (!list.enable)
            return

        var yStart = 1

        val mods: ArrayList<ModuleProvider> =
            CF4M.module.all.filter { it.enable }.toCollection(ArrayList<ModuleProvider>())
        mods.sortByDescending { getWidth(getDisplayName(it)) }
        for ((index, func) in mods.withIndex()) {
            val startX = scaledWidth - getWidth(getDisplayName(func)) - 6
            drawStringWithShadow(
                rendering2DEvent.matrixStack,
                getDisplayName(func),
                startX + 3,
                yStart,
                rainbow(index * 100)
            )
            yStart += fontHeight + 4
        }
    }

    @Event
    fun messageRendering(rendering2DEvent: Rendering2DEvent) {
        Epsilon.message.render(rendering2DEvent.matrixStack)
    }

    @Event
    fun messageRendered(rendered2DEvent: Rendered2DEvent) {
        Epsilon.message.remove()
    }

    @Event
    fun keyStrokes(rendering2DEvent: Rendering2DEvent) {
        if (!keyStrokes.enable)
            return

        Render2DUtils.drawImage("keystrokes.png", 0.5 - 1, 251 / 2.0 - 1, 172 / 2.0, 172 / 2.0)

    }


    @Event
    fun tabGUI(rendering2DEvent: Rendering2DEvent) {
        if (!tabGUI.enable)
            return

        val startX = 0
        var startY = 20
        Render2DUtils.drawRect(
            rendering2DEvent.matrixStack,
            startX,
            startY,
            startX + getWidestCategory() + 5,
            startY + categoryValues.size * (fontHeight + 2),
            ColorUtils.BG
        )
        for (c in categoryValues) {
            if (getCurrentCategory() == c) {
                Render2DUtils.drawRect(
                    rendering2DEvent.matrixStack,
                    startX + 1,
                    startY,
                    startX + getWidestCategory() + 5 - 1,
                    startY + fontHeight + 2,
                    ColorUtils.SELECT
                )
            }
            val name: String = c.name
            drawStringWithShadow(
                rendering2DEvent.matrixStack,
                name.substring(0, 1).toUpperCase() + name.substring(1, name.length).toLowerCase(),
                startX + 2 + if (getCurrentCategory() == c) 2 else 0,
                startY + 2,
                -1
            )
            startY += fontHeight + 2
        }

        if (screen == 1 || screen == 2) {
            val startModsX: Int = startX + getWidestCategory() + 6
            var startModsY = 20 + currentCategoryIndex * (fontHeight + 2)
            Render2DUtils.drawRect(
                rendering2DEvent.matrixStack,
                startModsX,
                startModsY,
                startModsX + getWidestMod() + 5,
                startModsY + getModsForCurrentCategory().size * (fontHeight + 2),
                ColorUtils.BG
            )
            for (func in getModsForCurrentCategory()) {
                if (getCurrentFunc() == func) {
                    Render2DUtils.drawRect(
                        rendering2DEvent.matrixStack,
                        startModsX + 1,
                        startModsY,
                        startModsX + getWidestMod() + 5 - 1,
                        startModsY + fontHeight + 2,
                        ColorUtils.SELECT
                    )
                }
                drawStringWithShadow(
                    rendering2DEvent.matrixStack,
                    func.name,
                    startModsX + 2 + if (getCurrentFunc() == func) 2 else 0,
                    startModsY + 2,
                    if (func.enable) -1 else Color.GRAY.rgb
                )
                startModsY += fontHeight + 2
            }
        }

        if (screen == 2) {
            val startSettingX = startX + getWidestCategory() + 6 + getWidestCategory() + 8
            var startSettingY = 20 + currentCategoryIndex * (9 + 2) + currentModIndex * (9 + 2)
            Render2DUtils.drawRect(
                rendering2DEvent.matrixStack,
                startSettingX,
                startSettingY,
                startSettingX + getWidestSetting() + 5,
                startSettingY + getSettingsForCurrentFunc().size * (fontHeight + 2),
                ColorUtils.BG
            )
            for (setting in getSettingsForCurrentFunc()) {
                if (getCurrentSetting() == setting) {
                    Render2DUtils.drawRect(
                        rendering2DEvent.matrixStack,
                        startSettingX + 1,
                        startSettingY,
                        startSettingX + getWidestSetting() + 5 - 1,
                        startSettingY + fontHeight + 2,
                        ColorUtils.SELECT
                    )
                }
                when (val s = setting.getSetting<Any>()) {
                    is EnableSetting -> {
                        drawStringWithShadow(
                            rendering2DEvent.matrixStack,
                            setting.name + ": " + s.enable,
                            startSettingX + 2 + if (getCurrentSetting() == setting) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == setting) -1 else Color.GRAY.rgb
                        )
                    }
                    is IntegerSetting -> {
                        drawStringWithShadow(
                            rendering2DEvent.matrixStack,
                            setting.name + ": " + s.current,
                            startSettingX + 2 + if (getCurrentSetting() == setting) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == setting) -1 else Color.GRAY.rgb
                        )
                    }
                    is DoubleSetting -> {
                        drawStringWithShadow(
                            rendering2DEvent.matrixStack,
                            setting.name + ": " + s.current,
                            startSettingX + 2 + if (getCurrentSetting() == setting) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == setting) -1 else Color.GRAY.rgb
                        )
                    }
                    is FloatSetting -> {
                        drawStringWithShadow(
                            rendering2DEvent.matrixStack,
                            setting.name + ": " + s.current,
                            startSettingX + 2 + if (getCurrentSetting() == setting) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == setting) -1 else Color.GRAY.rgb
                        )
                    }
                    is LongSetting -> {
                        drawStringWithShadow(
                            rendering2DEvent.matrixStack,
                            setting.name + ": " + s.current,
                            startSettingX + 2 + if (getCurrentSetting() == setting) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == setting) -1 else Color.GRAY.rgb
                        )
                    }
                    is ModeSetting -> {
                        drawStringWithShadow(
                            rendering2DEvent.matrixStack,
                            setting.name + ": " + s.current,
                            startSettingX + 2 + if (getCurrentSetting() == setting) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == setting) -1 else Color.GRAY.rgb
                        )
                    }
                }
                startSettingY += fontHeight + 2
            }
        }
    }

    private fun up() {
        if (currentCategoryIndex > 0 && screen == 0) {
            currentCategoryIndex--
        } else if (currentCategoryIndex == 0 && screen == 0) {
            currentCategoryIndex = categoryValues.size - 1
        } else if (currentModIndex > 0 && screen == 1) {
            currentModIndex--
        } else if (currentModIndex == 0 && screen == 1) {
            currentModIndex = getModsForCurrentCategory().size - 1
        } else if (currentSettingIndex > 0 && screen == 2 && !editMode) {
            currentSettingIndex--
        } else if (currentSettingIndex == 0 && screen == 2 && !editMode) {
            currentSettingIndex = getSettingsForCurrentFunc().size - 1
        }
        if (editMode) {
            val setting: Any = getCurrentSetting()
            if (setting is EnableSetting) {
                setting.enable = !setting.enable
            } else if (setting is IntegerSetting) {
                if (setting.current < setting.max) setting.current = setting.current + 1
            } else if (setting is DoubleSetting) {
                if (setting.current < setting.max) setting.current = Utils.valueFix(setting.current + 0.1)
            } else if (setting is FloatSetting) {
                if (setting.current < setting.max) setting.current = Utils.valueFix(setting.current + 0.1f)
            } else if (setting is LongSetting) {
                if (setting.current < setting.max) setting.current = setting.current + 1
            } else {
                try {
                    (setting as ModeSetting).current = setting.modes[getCurrentModeIndex(setting) - 1]
                } catch (e: Exception) {
                    (setting as ModeSetting).current = setting.modes[setting.modes.size - 1]
                }
            }
        }
    }

    private fun down() {
        if (currentCategoryIndex < categoryValues.size - 1 && screen == 0) {
            currentCategoryIndex++
        } else if (currentCategoryIndex == categoryValues.size - 1 && screen == 0) {
            currentCategoryIndex = 0
        } else if (currentModIndex < getModsForCurrentCategory().size - 1 && screen == 1) {
            currentModIndex++
        } else if (currentModIndex == getModsForCurrentCategory().size - 1 && screen == 1) {
            currentModIndex = 0
        } else if (currentSettingIndex < getSettingsForCurrentFunc().size - 1 && screen == 2 && !editMode) {
            currentSettingIndex++
        } else if (currentSettingIndex == getSettingsForCurrentFunc().size - 1 && screen == 2 && !editMode) {
            currentSettingIndex = 0
        }
        if (editMode) {
            val setting: Any = getCurrentSetting()
            if (setting is EnableSetting) {
                setting.enable = !setting.enable
            } else if (setting is IntegerSetting) {
                if (setting.current > setting.min) setting.current = setting.current - 1
            } else if (setting is DoubleSetting) {
                if (setting.current > setting.min) setting.current = Utils.valueFix(setting.current - 0.1)
            } else if (setting is FloatSetting) {
                if (setting.current > setting.min) setting.current = Utils.valueFix(setting.current - 0.1f)
            } else if (setting is LongSetting) {
                if (setting.current > setting.min) setting.current = setting.current - 1
            } else {
                try {
                    (setting as ModeSetting).current = setting.modes[getCurrentModeIndex(setting) + 1]
                } catch (e: Exception) {
                    (setting as ModeSetting).current = setting.modes[0]
                }
            }
        }
    }

    private fun getCurrentModeIndex(modeSetting: ModeSetting): Int {
        var index = 0
        for (ms in modeSetting.modes) {
            index++
            if (modeSetting.current == ms) {
                return index
            }
        }
        return 0;
    }

    private fun right(key: Int) {
        if (screen == 0) {
            screen = 1
        } else if (screen == 1 && getCurrentFunc().setting.all.isNotEmpty()) {
            getCurrentFunc().enable()
        } else if (screen == 1 && getCurrentFunc().setting.all.isNotEmpty() && key == GLFW.GLFW_KEY_ENTER) {
            getCurrentFunc().enable()
        } else if (screen == 1 && getCurrentFunc().setting.all.isNotEmpty()) {
            screen = 2
        } else if (screen == 2) {
            editMode = !editMode
        }
    }

    private fun left() {
        if (screen == 1) {
            screen = 0
            currentModIndex = 0
        } else if (screen == 2) {
            screen = 1
            currentSettingIndex = 0
        }
    }

    @Event
    fun onKey(keyBoardEvent: KeyboardEvent) {
        when (keyBoardEvent.key) {
            GLFW.GLFW_KEY_UP -> up()
            GLFW.GLFW_KEY_DOWN -> down()
            GLFW.GLFW_KEY_RIGHT -> right(GLFW.GLFW_KEY_RIGHT)
            GLFW.GLFW_KEY_LEFT -> left()
            GLFW.GLFW_KEY_ENTER -> right(GLFW.GLFW_KEY_ENTER)
        }
    }

    private fun getCurrentSetting(): SettingProvider {
        return getSettingsForCurrentFunc()[currentSettingIndex]
    }

    private fun getSettingsForCurrentFunc(): ArrayList<SettingProvider> {
        return getCurrentFunc().setting.all
    }

    private fun getCurrentCategory(): Category {
        return categoryValues[currentCategoryIndex]
    }

    private fun getCurrentFunc(): ModuleProvider {
        return getModsForCurrentCategory()[currentModIndex]
    }

    private fun getModsForCurrentCategory(): ArrayList<ModuleProvider> {
        return CF4M.module.getAllByCategory(getCurrentCategory())
    }

    private fun getWidestSetting(): Int {
        var width = 0
        for (setting in getSettingsForCurrentFunc()) {
            val name: String = when (val s = setting.getSetting<Any>()) {
                is EnableSetting -> {
                    setting.name + ": " + s.enable
                }
                is IntegerSetting -> {
                    setting.name + ": " + s.current
                }
                is FloatSetting -> {
                    setting.name + ": " + s.current
                }
                is DoubleSetting -> {
                    setting.name + ": " + s.current
                }
                is LongSetting -> {
                    setting.name + ": " + s.current
                }
                is ModeSetting -> {
                    setting.name + ": " + s.current
                }
                else -> "NULL"
            }
            if (getWidth(name) > width) {
                width = getWidth(name)
            }
        }
        return width
    }

    private fun getWidestMod(): Int {
        var width = 0
        for (module in CF4M.module.all) {
            val cWidth = getWidth(module.name)
            if (cWidth > width) {
                width = cWidth
            }
        }
        return width
    }

    private fun getWidestCategory(): Int {
        var width = 0
        for (c in categoryValues) {
            val name: String = c.name
            val cWidth = getWidth(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase())
            if (cWidth > width) {
                width = cWidth
            }
        }
        return width
    }

    private fun rainbow(delay: Int): Int {
        var rainbowState = ceil((System.currentTimeMillis() + delay) / 20.0)
        rainbowState %= 360.0
        return Color.getHSBColor((rainbowState / 360.0f).toFloat(), 0.8f, 0.7f).rgb
    }

    private fun getDisplayName(module: ModuleProvider): String {
        val name = module.name
        val tag = module.getExtend<Func>()
        return if (tag == null) {
            name
        } else {
            "$name[$tag]"
        }
    }
}