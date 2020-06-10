package cn.enaium.epsilon.screen.clickgui

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.settings.*
import cn.enaium.epsilon.ui.UI
import cn.enaium.epsilon.ui.elements.*
import cn.enaium.epsilon.utils.Render2DUtils
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class SettingListScreen(val func: Func) : UI() {

    override fun initUI() {
        super.initUI()
        var y = 0
        val scrollPanel = ScrollPanel(Render2DUtils.scaledWidth / 2 - 50, 50, 100, 120)
        for (setting in Epsilon.settingManager.getSettingsForFunc(func)) {
            if (setting is EnableSetting) {
                scrollPanel.addElement(object :
                    CheckBox(0, y, setting.name, setting.enable) {
                    override fun onLeftClicked() {
                        setting.enable = this.checked
                        super.onLeftClicked()
                    }
                })
            } else if (setting is IntegerSetting || setting is FloatSetting || setting is DoubleSetting || setting is LongSetting) {
                val textField = TextField(0, y, 50)
                textField.setSuggestion(setting.name)
                when (setting) {
                    is IntegerSetting -> {
                        textField.setText(setting.current.toString())
                        scrollPanel.addElement(object : Button(60, y, 40, 20, "SET") {
                            override fun onLeftClicked() {
                                setting.current = textField.getText().toInt()
                                super.onLeftClicked()
                            }
                        })
                    }
                    is FloatSetting -> {
                        textField.setText(setting.current.toString())
                        scrollPanel.addElement(object : Button(60, y, 40, 20, "SET") {
                            override fun onLeftClicked() {
                                setting.current = textField.getText().toFloat()
                                super.onLeftClicked()
                            }
                        })
                    }
                    is DoubleSetting -> {
                        textField.setText(setting.current.toString())
                        scrollPanel.addElement(object : Button(60, y, 40, 20, "SET") {
                            override fun onLeftClicked() {
                                setting.current = textField.getText().toDouble()
                                super.onLeftClicked()
                            }
                        })
                    }
                    is LongSetting -> {
                        textField.setText(setting.current.toString())
                        scrollPanel.addElement(object : Button(60, y, 40, 20, "SET") {
                            override fun onLeftClicked() {
                                setting.current = textField.getText().toLong()
                                super.onLeftClicked()
                            }
                        })
                    }
                }
                scrollPanel.addElement(textField)
            } else if (setting is ModeSetting) {
                scrollPanel.addElement(object : ModeButton(0, y, setting.modes, setting.getCurrentIndex()) {
                    override fun onLeftClicked() {
                        try {
                            setting.current = setting.modes[setting.getCurrentIndex() + 1]
                        } catch (e: Exception) {
                            setting.current = setting.modes.first()
                        }
                        this.current = setting.getCurrentIndex()
                        super.onLeftClicked()
                    }

                    override fun onRightClicked() {
                        try {
                            setting.current = setting.modes[setting.getCurrentIndex() - 1]
                        } catch (e: Exception) {
                            setting.current = setting.modes.last()
                        }
                        this.current = setting.getCurrentIndex()
                        super.onRightClicked()
                    }
                })
            } else if (setting is BlockListSetting) {
                scrollPanel.addElement(object : Button(0, y, "Set:${setting.name}") {
                    override fun onLeftClicked() {
                        MC.openScreen(EditBlockListSettingScreen(setting))
                        super.onLeftClicked()
                    }
                })
            }
            y += 30
        }
        addElement(scrollPanel)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
            Epsilon.MC.openScreen(CategoryListScreen())
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun shouldCloseOnEsc(): Boolean {
        return false
    }
}