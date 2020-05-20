package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.Epsilon.AUTHOR
import cn.enaium.epsilon.Epsilon.IMC
import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.Epsilon.NAME
import cn.enaium.epsilon.Epsilon.VERSION
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.KeyboardEvent
import cn.enaium.epsilon.event.events.Render2DEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.func.FuncAT
import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.setting.SettingAT
import cn.enaium.epsilon.setting.settings.*
import cn.enaium.epsilon.utils.ColorUtils
import cn.enaium.epsilon.utils.FontUtils.drawStringWithShadow
import cn.enaium.epsilon.utils.FontUtils.fontHeight
import cn.enaium.epsilon.utils.FontUtils.getWidth
import cn.enaium.epsilon.utils.Render2DUtils
import cn.enaium.epsilon.utils.Render2DUtils.scaledHeight
import cn.enaium.epsilon.utils.Render2DUtils.scaledWidth
import cn.enaium.epsilon.utils.Utils
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.util.*
import kotlin.collections.ArrayList


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@FuncAT
class HUDFunc : Func("HUD", GLFW.GLFW_KEY_P, Category.RENDER) {
    private var categoryValues: ArrayList<Category> = ArrayList()
    private var currentCategoryIndex = 0
    private var currentModIndex = 0
    private var currentSettingIndex = 0

    private var editMode = false

    private var screen = 0

    @SettingAT
    private val tabGUI = EnableSetting(this, "TabGUI", true)

    @SettingAT
    private val list = EnableSetting(this, "List", true)

    @SettingAT
    private val entityList = EnableSetting(this, "EntityList", false)

    @SettingAT
    private val coords = EnableSetting(this, "Coords", true)

    @SettingAT
    private val direction = EnableSetting(this, "Direction", true)
    
    init {
        categoryValues = ArrayList()
        currentCategoryIndex = 0
        currentModIndex = 0
        currentSettingIndex = 0
        editMode = false
        screen = 0
        this.categoryValues.addAll(Category.values());
    }

    @EventAT
    fun entityList(render2DEvent: Render2DEvent) {
        if (!entityList.enable)
            return

        val entities: MutableSet<String> = HashSet()
        for (e in MC.world!!.entities) {
            entities.add(e.javaClass.simpleName)
        }

        entities.sortedWith(Comparator { o1: String, o2: String -> getWidth(o2) - getWidth(o1) })

        var yStart = scaledHeight - fontHeight

        for (entity in entities) {
            val startX = scaledWidth - getWidth(entity) - 6
            drawStringWithShadow(render2DEvent.matrixStack, entity, startX + 3, yStart, Color.WHITE.rgb)
            yStart -= fontHeight + 4
        }
    }

    @EventAT
    fun infoList(render2DEvent: Render2DEvent) {
        var infoY = 54 + categoryValues.size * (fontHeight + 2) + 10
        val infoList: ArrayList<String> = ArrayList()

        if (coords.enable) {
            infoList.add("Coords:" + Utils.valueFix(MC.player!!.x) + "/" + Utils.valueFix(MC.player!!.y) + "/" + Utils.valueFix(MC.player!!.z))
        }

        if (direction.enable) {
            infoList.add("Direction:${MC.cameraEntity!!.horizontalFacing}")
        }

        infoList.sortedBy { getWidth(it) }

        for (s in infoList) {
            drawStringWithShadow(render2DEvent.matrixStack, s, 5, infoY, Color.WHITE.rgb)
            infoY += fontHeight + 4
        }

    }

    @EventAT
    fun list(render2DEvent: Render2DEvent) {
        if (!list.enable)
            return

        var yStart = 1

        val functions = ArrayList<Func>()
        for (m in Epsilon.funcManager.functions) {
            if (m.enable) {
                functions.add(m)
            }
        }

        val mods: ArrayList<Func> = functions
        mods.sortByDescending { getWidth(it.getDisplayTag()) }
        for (func in mods) {
            val startX = scaledWidth - getWidth(func.getDisplayTag()) - 6
            drawStringWithShadow(render2DEvent.matrixStack, func.getDisplayTag(), startX + 3, yStart, Color.WHITE.rgb)
            yStart += fontHeight + 4
        }
    }

    @EventAT
    fun tabGUI(render2DEvent: Render2DEvent) {
        if (!tabGUI.enable)
            return

        GL11.glScaled(2.0, 2.0, 2.0)
        val i = drawStringWithShadow(render2DEvent.matrixStack, NAME, 2, 2, Color.WHITE.rgb);
        GL11.glScaled(0.5, 0.5, 0.5)

        drawStringWithShadow(render2DEvent.matrixStack, VERSION, i * 2, 2, Color.ORANGE.rgb)
        drawStringWithShadow(render2DEvent.matrixStack, "By $AUTHOR", i * 2, fontHeight + 2, Color.YELLOW.rgb)

        val startX = 5
        var startY = 5 + 9 + 40
        Render2DUtils.drawRect(render2DEvent.matrixStack, startX, startY, startX + this.getWidestCategory() + 5, startY + categoryValues.size * (fontHeight + 2), ColorUtils.BG)
        for (c in categoryValues) {
            if (getCurrentCategory() == c) {
                Render2DUtils.drawRect(render2DEvent.matrixStack, startX + 1, startY, startX + this.getWidestCategory() + 5 - 1, startY + fontHeight + 2, ColorUtils.SELECT)
            }
            val name: String = c.name
            drawStringWithShadow(render2DEvent.matrixStack, name.substring(0, 1).toUpperCase() + name.substring(1, name.length).toLowerCase(), startX + 2 + if (getCurrentCategory() == c) 2 else 0, startY + 2, -1)
            startY += fontHeight + 2
        }

        if (screen == 1 || screen == 2) {
            val startModsX: Int = startX + this.getWidestCategory() + 6
            var startModsY = 5 + 9 + 40 + currentCategoryIndex * (fontHeight + 2)
            Render2DUtils.drawRect(render2DEvent.matrixStack, startModsX, startModsY, startModsX + this.getWidestMod() + 5, startModsY + getModsForCurrentCategory().size * (fontHeight + 2), ColorUtils.BG)
            for (f in getModsForCurrentCategory()) {
                if (getCurrentFunc() == f) {
                    Render2DUtils.drawRect(render2DEvent.matrixStack, startModsX + 1, startModsY, startModsX + this.getWidestMod() + 5 - 1, startModsY + fontHeight + 2, ColorUtils.SELECT)
                }
                drawStringWithShadow(render2DEvent.matrixStack, f.name + if (Epsilon.settingManager.getSettingsForFunc(f) != null) ">" else "", startModsX + 2 + if (getCurrentFunc() == f) 2 else 0, startModsY + 2, if (f.enable) -1 else Color.GRAY.rgb)
                startModsY += fontHeight + 2
            }
        }
        if (screen == 2) {
            val startSettingX: Int = startX + getWidestCategory() + 6 + getWidestCategory() + 8
            var startSettingY = 5 + 9 + 40 + currentCategoryIndex * (9 + 2) + currentModIndex * (9 + 2)
            Render2DUtils.drawRect(render2DEvent.matrixStack, startSettingX, startSettingY, startSettingX + getWidestSetting() + 5, startSettingY + getSettingForCurrentMod()!!.size * (fontHeight + 2), ColorUtils.BG)
            for (s in getSettingForCurrentMod()!!) {
                if (getCurrentSetting() == s) {
                    Render2DUtils.drawRect(render2DEvent.matrixStack, startSettingX + 1, startSettingY, startSettingX + this.getWidestSetting() + 5 - 1, startSettingY + fontHeight + 2, ColorUtils.SELECT)
                }
                when (s) {
                    is EnableSetting -> {
                        drawStringWithShadow(render2DEvent.matrixStack, s.name + ": " + s.enable, startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0, startSettingY + 2, if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb)
                    }
                    is IntegerSetting -> {
                        drawStringWithShadow(render2DEvent.matrixStack, s.name + ": " + s.current, startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0, startSettingY + 2, if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb)
                    }
                    is DoubleSetting -> {
                        drawStringWithShadow(render2DEvent.matrixStack, s.name + ": " + s.current, startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0, startSettingY + 2, if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb)
                    }
                    is FloatSetting -> {
                        drawStringWithShadow(render2DEvent.matrixStack, s.name + ": " + s.current, startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0, startSettingY + 2, if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb)
                    }
                    is LongSetting -> {
                        drawStringWithShadow(render2DEvent.matrixStack, s.name + ": " + s.current, startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0, startSettingY + 2, if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb)
                    }
                    is ModeSetting -> {
                        drawStringWithShadow(render2DEvent.matrixStack, s.name + ": " + s.current, startSettingX + 2 + if (getCurrentSetting() == s) 2 else 0, startSettingY + 2, if (editMode && getCurrentSetting() == s) -1 else Color.GRAY.rgb)
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
            val s: Setting = this.getCurrentSetting()
            if (s is EnableSetting) {
                s.enable = !s.enable
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
                    (s as ModeSetting).current = s.modes[s.getCurrentIndex() - 1]
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
            val s: Setting = this.getCurrentSetting()
            if (s is EnableSetting) {
                s.enable = !s.enable
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
                    (s as ModeSetting).current = s.modes[s.getCurrentIndex() + 1]
                } catch (e: Exception) {
                    (s as ModeSetting).current = s.modes[0]
                }
            }
        }
    }

    private fun right(key: Int) {
        if (screen == 0) {
            screen = 1
        } else if (screen == 1 && this.getSettingForCurrentMod() == null) {
            this.getCurrentFunc().enable()
        } else if (screen == 1 && this.getSettingForCurrentMod() != null && key == GLFW.GLFW_KEY_ENTER) {
            this.getCurrentFunc().enable()
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

    @EventAT
    fun onKey(keyBoardEvent: KeyboardEvent) {
        if (MC.currentScreen != null) return
        if (keyBoardEvent.action != GLFW.GLFW_PRESS) return
        when (keyBoardEvent.keyCode) {
            GLFW.GLFW_KEY_UP -> up()
            GLFW.GLFW_KEY_DOWN -> down()
            GLFW.GLFW_KEY_RIGHT -> right(GLFW.GLFW_KEY_RIGHT)
            GLFW.GLFW_KEY_LEFT -> left()
            GLFW.GLFW_KEY_ENTER -> right(GLFW.GLFW_KEY_ENTER)
        }
    }

    private fun getCurrentSetting(): Setting {
        return getSettingForCurrentMod()!![currentSettingIndex]
    }

    private fun getSettingForCurrentMod(): ArrayList<Setting>? {
        return Epsilon.settingManager.getSettingsForFunc(getCurrentFunc())
    }

    private fun getCurrentCategory(): Category {
        return categoryValues[currentCategoryIndex]
    }

    private fun getCurrentFunc(): Func {
        return getModsForCurrentCategory()[currentModIndex]
    }

    private fun getModsForCurrentCategory(): ArrayList<Func> {
        return Epsilon.funcManager.getFuncForCategory(getCurrentCategory())
    }

    private fun getWidestSetting(): Int {
        var width = 0
        for (s in getSettingForCurrentMod()!!) {
            val name: String = when (s) {
                is EnableSetting -> {
                    s.name + ": " + s.enable
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
        for (m in Epsilon.funcManager.functions) {
            val cWidth = getWidth(m.name)
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