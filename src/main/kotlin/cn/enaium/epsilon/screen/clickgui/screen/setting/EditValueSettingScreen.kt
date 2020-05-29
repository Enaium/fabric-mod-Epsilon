package cn.enaium.epsilon.screen.clickgui.screen.setting

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.setting.settings.DoubleSetting
import cn.enaium.epsilon.setting.settings.FloatSetting
import cn.enaium.epsilon.setting.settings.IntegerSetting
import cn.enaium.epsilon.setting.settings.LongSetting
import cn.enaium.epsilon.utils.ColorUtils
import cn.enaium.epsilon.utils.FontUtils
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText
import org.lwjgl.glfw.GLFW


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class EditValueSettingScreen(val setting: Setting) : Screen(LiteralText("")) {

    private lateinit var value: TextFieldWidget

    override fun init() {
        value = TextFieldWidget(FontUtils.tr, width / 2 - 100, height / 2 - 10, 200, 20, LiteralText(""))
        value.setSelected(true)
        when (setting) {
            is IntegerSetting -> {
                value.text = setting.current.toString()
            }
            is FloatSetting -> {
                value.text = setting.current.toString()
            }
            is DoubleSetting -> {
                value.text = setting.current.toString()
            }
            is LongSetting -> {
                value.text = setting.current.toString()
            }
        }
        children.add(value)
        super.init()
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        drawCenteredString(matrices, FontUtils.tr, "Edit ${setting.name}", width / 2, 20, ColorUtils.WHITE)
        value.render(matrices, mouseX, mouseY, delta)
        super.render(matrices, mouseX, mouseY, delta)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            when (setting) {
                is IntegerSetting -> {
                    setting.current = value.text.toInt()
                }
                is FloatSetting -> {
                    setting.current = value.text.toFloat()
                }
                is DoubleSetting -> {
                    setting.current = value.text.toDouble()
                }
                is LongSetting -> {
                    setting.current = value.text.toLong()
                }
            }
            onClose()
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun isPauseScreen(): Boolean {
        return false
    }
}