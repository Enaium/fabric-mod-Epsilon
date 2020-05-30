package cn.enaium.epsilon.screen.clickgui.screen.setting

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.screen.clickgui.panel.element.EnableElementPanel
import cn.enaium.epsilon.screen.clickgui.panel.element.ElementPanel
import cn.enaium.epsilon.screen.clickgui.panel.element.ModeElementPanel
import cn.enaium.epsilon.screen.clickgui.panel.element.ValueElementPanel
import cn.enaium.epsilon.screen.clickgui.screen.ClickGUIScreen
import cn.enaium.epsilon.setting.settings.*
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class EditSettingScreen(func: Func) : Screen(LiteralText("")) {

    private var elementPanels: ArrayList<ElementPanel> = ArrayList()

    init {
        for (setting in Epsilon.settingManager.getSettingsForFunc(func)!!) {
            if (setting is EnableSetting) {
                elementPanels.add(EnableElementPanel(setting))
            } else if (setting is IntegerSetting || setting is FloatSetting || setting is DoubleSetting || setting is LongSetting) {
                elementPanels.add(ValueElementPanel(setting))
            } else if (setting is ModeSetting) {
                elementPanels.add(ModeElementPanel(setting))
            }
        }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        var y = 10.0
        for (settingPanel in elementPanels) {
            settingPanel.render(matrices, mouseX, mouseY, delta, 100.0, y)
            y += settingPanel.height + 5
        }
        super.render(matrices, mouseX, mouseY, delta)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        for (settingPanel in elementPanels) {
            settingPanel.mouseClicked(mouseX, mouseY, button)
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            MC.openScreen(ClickGUIScreen)
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun shouldCloseOnEsc(): Boolean {
        return false
    }
}