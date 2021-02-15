package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.Event
import cn.enaium.cf4m.annotation.Setting
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

    private var categoryValues: ArrayList<Category> = ArrayList()
    private var currentCategoryIndex = 0
    private var currentModIndex = 0
    private var currentSettingIndex = 0

    private var editMode = false

    private var screen = 0

    @Setting
    private val tabGUI = EnableSetting(this, "TabGUI", "", true)

    @Setting
    private val list = EnableSetting(this, "List", "", true)

    @Setting
    private val entityList = EnableSetting(this, "EntityList", "", false)

    @Setting
    private val coords = EnableSetting(this, "Coords", "", true)

    @Setting
    private val direction = EnableSetting(this, "Direction", "", true)

    @Setting
    private val fps = EnableSetting(this, "FPS", "", true)

    @Setting
    private val gameTime = EnableSetting(this, "GameTime", "", false)

    @Setting
    private val realTime = EnableSetting(this, "RealTime", "", false)

    @Setting
    private val ping = EnableSetting(this, "Ping", "", false)

    @Setting
    private val titleInfo = EnableSetting(this, "TitleInfo", "", false)


    private var yaw = 0.0F
    private var pitch = 0.0F

    init {
        categoryValues = ArrayList()
        currentCategoryIndex = 0
        currentModIndex = 0
        currentSettingIndex = 0
        editMode = false
        screen = 0
        this.categoryValues.addAll(Category.values())
    }

    @Event
    fun entityList(render2DEvent: Render2DEvent) {
        if (!entityList.isEnable)
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
        var infoY = 54 + categoryValues.size * (fontHeight + 2) + 10
        val infoList: ArrayList<String> = ArrayList()

        if (coords.isEnable) {
            infoList.add(
                "XYZ:" + Formatting.AQUA + Utils.valueFix(MC.player!!.x) + "/" + Utils.valueFix(MC.player!!.y) + "/" + Utils.valueFix(
                    MC.player!!.z
                )
            )
        }

        if (direction.isEnable) {
            infoList.add("Face:${Formatting.BLUE}${MC.cameraEntity!!.horizontalFacing}[$yaw/$pitch]")
        }

        if (fps.isEnable) {
            infoList.add("FPS:${IMC.mc.currentFps}")
        }

        if (gameTime.isEnable) {
            val timeOfDay = MC.world!!.timeOfDay
            val timeTick = timeOfDay % 24000
            val timeHour = ((timeTick / 1000) + 6) % 24
            val timeMin = (timeTick / 16.6) % 60
            val timeSec = (timeTick / 0.27) % 60
            infoList.add("GameTime:${Formatting.GRAY}${timeHour.toInt()}:${timeMin.toInt()}:${timeSec.toInt()}")
        }

        if (realTime.isEnable) {
            infoList.add("RealTime:${Formatting.GRAY}${SimpleDateFormat("HH:mm:ss").format(Date())}")
        }

        if (ping.isEnable) {
            infoList.add("Ping:${MC.networkHandler!!.getPlayerListEntry(MC.player!!.uuid)!!.latency}")
        }

        infoList.sortedBy { getWidth(it) }

        for (s in infoList) {
            drawStringWithShadow(render2DEvent.matrixStack, s, 5, infoY, Color.WHITE.rgb)
            infoY += fontHeight + 4
        }

        if (titleInfo.isEnable) {
            IMC.mc.window.setTitle(Utils.clearFormat(infoList.toString()))
        }

    }

    @Event
    fun list(render2DEvent: Render2DEvent) {
        if (!list.isEnable)
            return

        var yStart = 1

        val functions = ArrayList<Any>()
        for (m in CF4M.getInstance().module.modules) {
            if (CF4M.getInstance().module.isEnable(m)) {
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
        val name = CF4M.getInstance().module.getName(module)
        val tag = CF4M.getInstance().module.getValue<String>(module, "tag")
        return return if (tag == null) {
            name
        } else {
            "$name|$tag"
        }
    }

    @Event
    fun tabGUI(render2DEvent: Render2DEvent) {
        if (!tabGUI.isEnable)
            return

        GL11.glScaled(2.0, 2.0, 2.0)
        val i = drawStringWithShadow(render2DEvent.matrixStack, NAME, 2, 2, Color.WHITE.rgb)
        GL11.glScaled(0.5, 0.5, 0.5)

        drawStringWithShadow(render2DEvent.matrixStack, VERSION, i * 2, 2, Color.ORANGE.rgb)
        drawStringWithShadow(render2DEvent.matrixStack, "By $AUTHOR", i * 2, fontHeight + 2, Color.YELLOW.rgb)

        val startX = 5
        var startY = 5 + 9 + 40
        Render2DUtils.drawRect(
            render2DEvent.matrixStack,
            startX,
            startY,
            startX + this.getWidestCategory() + 5,
            startY + categoryValues.size * (fontHeight + 2),
            ColorUtils.BG
        )
        for (c in categoryValues) {
            if (getCurrentCategory() == c) {
                Render2DUtils.drawRect(
                    render2DEvent.matrixStack,
                    startX + 1,
                    startY,
                    startX + this.getWidestCategory() + 5 - 1,
                    startY + fontHeight + 2,
                    ColorUtils.SELECT
                )
            }
            val name: String = c.name
            drawStringWithShadow(
                render2DEvent.matrixStack,
                name.substring(0, 1).toUpperCase() + name.substring(1, name.length).toLowerCase(),
                startX + 2 + if (getCurrentCategory() == c) 2 else 0,
                startY + 2,
                -1
            )
            startY += fontHeight + 2
        }

        if (screen == 1 || screen == 2) {
            val startModsX: Int = startX + this.getWidestCategory() + 6
            var startModsY = 5 + 9 + 40 + currentCategoryIndex * (fontHeight + 2)
            Render2DUtils.drawRect(
                render2DEvent.matrixStack,
                startModsX,
                startModsY,
                startModsX + this.getWidestMod() + 5,
                startModsY + getModsForCurrentCategory().size * (fontHeight + 2),
                ColorUtils.BG
            )
            for (f in getModsForCurrentCategory()) {
                if (getCurrentFunc() == f) {
                    Render2DUtils.drawRect(
                        render2DEvent.matrixStack,
                        startModsX + 1,
                        startModsY,
                        startModsX + this.getWidestMod() + 5 - 1,
                        startModsY + fontHeight + 2,
                        ColorUtils.SELECT
                    )
                }
                drawStringWithShadow(
                    render2DEvent.matrixStack,
                    CF4M.getInstance().module.getName(f) + if (getSettingsForFunc(f) != null) ">" else "",
                    startModsX + 2 + if (getCurrentFunc() == f) 2 else 0,
                    startModsY + 2,
                    if (CF4M.getInstance().module.isEnable(f)) -1 else Color.GRAY.rgb
                )
                startModsY += fontHeight + 2
            }
        }
        if (screen == 2) {
            val startSettingX: Int = startX + getWidestCategory() + 6 + getWidestCategory() + 8
            var startSettingY = 5 + 9 + 40 + currentCategoryIndex * (9 + 2) + currentModIndex * (9 + 2)
            Render2DUtils.drawRect(
                render2DEvent.matrixStack,
                startSettingX,
                startSettingY,
                startSettingX + getWidestSetting() + 5,
                startSettingY + getSettingForCurrentMod()!!.size * (fontHeight + 2),
                ColorUtils.BG
            )
            for (s in getSettingForCurrentMod()!!) {
                if (getCurrentSetting() == s) {
                    Render2DUtils.drawRect(
                        render2DEvent.matrixStack,
                        startSettingX + 1,
                        startSettingY,
                        startSettingX + this.getWidestSetting() + 5 - 1,
                        startSettingY + fontHeight + 2,
                        ColorUtils.SELECT
                    )
                }
                when (s) {
                    is EnableSetting -> {
                        drawStringWithShadow(
                            render2DEvent.matrixStack,
                            s.name + ": " + s.isEnable,
                            startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb
                        )
                    }
                    is IntegerSetting -> {
                        drawStringWithShadow(
                            render2DEvent.matrixStack,
                            s.name + ": " + s.current,
                            startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb
                        )
                    }
                    is DoubleSetting -> {
                        drawStringWithShadow(
                            render2DEvent.matrixStack,
                            s.name + ": " + s.current,
                            startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb
                        )
                    }
                    is FloatSetting -> {
                        drawStringWithShadow(
                            render2DEvent.matrixStack,
                            s.name + ": " + s.current,
                            startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb
                        )
                    }
                    is LongSetting -> {
                        drawStringWithShadow(
                            render2DEvent.matrixStack,
                            s.name + ": " + s.current,
                            startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb
                        )
                    }
                    is ModeSetting -> {
                        drawStringWithShadow(
                            render2DEvent.matrixStack,
                            s.name + ": " + s.current,
                            startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0,
                            startSettingY + 2,
                            if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb
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
            currentModIndex = this.getModsForCurrentCategory().size - 1
        } else if (currentSettingIndex > 0 && screen == 2 && !editMode) {
            currentSettingIndex--
        } else if (currentSettingIndex == 0 && screen == 2 && !editMode) {
            currentSettingIndex = this.getSettingForCurrentMod()!!.size - 1
        }
        if (editMode) {
            val s: SettingBase = this.getCurrentSetting()
            if (s is EnableSetting) {
                s.isEnable = !s.isEnable
            } else if (s is IntegerSetting) {
                if (s.current < s.max) s.current = s.current + 1
            } else if (s is DoubleSetting) {
                if (s.current < s.max) s.current = Utils.valueFix(s.current + 0.1)
            } else if (s is FloatSetting) {
                if (s.current < s.max) s.current = Utils.valueFix(s.current + 0.1f)
            } else if (s is LongSetting) {
                if (s.current < s.max) s.current = s.current + 1
            } else {
                try {
                    (s as ModeSetting).current = s.modes[getCurrentModeIndex(s) - 1]
                } catch (e: Exception) {
                    (s as ModeSetting).current = s.modes[s.modes.size - 1]
                }
            }
        }
    }

    private fun down() {
        if (currentCategoryIndex < categoryValues.size - 1 && screen == 0) {
            currentCategoryIndex++
        } else if (currentCategoryIndex == categoryValues.size - 1 && screen == 0) {
            currentCategoryIndex = 0
        } else if (currentModIndex < this.getModsForCurrentCategory().size - 1 && screen == 1) {
            currentModIndex++
        } else if (currentModIndex == this.getModsForCurrentCategory().size - 1 && screen == 1) {
            currentModIndex = 0
        } else if (currentSettingIndex < this.getSettingForCurrentMod()!!.size - 1 && screen == 2 && !editMode) {
            currentSettingIndex++
        } else if (currentSettingIndex == this.getSettingForCurrentMod()!!.size - 1 && screen == 2 && !editMode) {
            currentSettingIndex = 0
        }
        if (editMode) {
            val s: SettingBase = this.getCurrentSetting()
            if (s is EnableSetting) {
                s.isEnable = !s.isEnable
            } else if (s is IntegerSetting) {
                if (s.current > s.min) s.current = s.current - 1
            } else if (s is DoubleSetting) {
                if (s.current > s.min) s.current = Utils.valueFix(s.current - 0.1)
            } else if (s is FloatSetting) {
                if (s.current > s.min) s.current = Utils.valueFix(s.current - 0.1f)
            } else if (s is LongSetting) {
                if (s.current > s.min) s.current = s.current - 1
            } else {
                try {
                    (s as ModeSetting).current = s.modes[getCurrentModeIndex(s) + 1]
                } catch (e: Exception) {
                    (s as ModeSetting).current = s.modes[0]
                }
            }
        }
    }

    private fun getCurrentModeIndex(modeSetting: ModeSetting): Int {
        var index = 0
        for (ms in modeSetting.modes) {
            index++
            if(modeSetting.current.equals(ms)) {
                return index
            }
        }
        return 0;
    }

    private fun right(key: Int) {
        if (screen == 0) {
            screen = 1
        } else if (screen == 1 && this.getSettingForCurrentMod() == null) {
            CF4M.getInstance().module.enable(this.getCurrentFunc())
        } else if (screen == 1 && this.getSettingForCurrentMod() != null && key == GLFW.GLFW_KEY_ENTER) {
            CF4M.getInstance().module.enable(this.getCurrentFunc())
        } else if (screen == 1 && this.getSettingForCurrentMod() != null) {
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

    fun getSettingsForFunc(m: Any): ArrayList<SettingBase>? {
        val settings = ArrayList<SettingBase>()
        for (s in CF4M.getInstance().module.settings) {
            if (s !is BlockListSetting)
                if (s.module == m) settings.add(s)
        }
        return if (settings.isEmpty()) null else settings
    }

    private fun getCurrentSetting(): SettingBase {
        return getSettingForCurrentMod()!![currentSettingIndex]
    }

    private fun getSettingForCurrentMod(): ArrayList<SettingBase>? {
        return getSettingsForFunc(getCurrentFunc())
    }

    private fun getCurrentCategory(): Category {
        return categoryValues[currentCategoryIndex]
    }

    private fun getCurrentFunc(): Any {
        return getModsForCurrentCategory()[currentModIndex]
    }

    private fun getModsForCurrentCategory(): ArrayList<Any> {
        return getFuncForCategory(getCurrentCategory())
    }

    private fun getFuncForCategory(category: Category): ArrayList<Any> {
        var list: ArrayList<Any> = ArrayList();
        for (module in CF4M.getInstance().module.modules) {
            if (CF4M.getInstance().module.getCategory(module).equals(category)) {
                list.add(module)
            }
        }
        return list;
    }

    private fun getWidestSetting(): Int {
        var width = 0
        for (s in getSettingForCurrentMod()!!) {
            val name: String = when (s) {
                is EnableSetting -> {
                    s.name + ": " + s.isEnable
                }
                is IntegerSetting -> {
                    s.name + ": " + s.current
                }
                is FloatSetting -> {
                    s.name + ": " + s.current
                }
                is DoubleSetting -> {
                    s.name + ": " + s.current
                }
                is LongSetting -> {
                    s.name + ": " + s.current
                }
                else -> {
                    s.name + ": " + (s as ModeSetting).current
                }
            }
            if (getWidth(name) > width) {
                width = getWidth(name)
            }
        }
        return width
    }

    private fun getWidestMod(): Int {
        var width = 0
        for (m in CF4M.getInstance().module.modules) {
            val cWidth = getWidth(CF4M.getInstance().module.getName(m))
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
            val cWidth = getWidth(name.substring(0, 1).toUpperCase() + name.substring(1, name.length).toLowerCase())
            if (cWidth > width) {
                width = cWidth
            }
        }
        return width
    }

}