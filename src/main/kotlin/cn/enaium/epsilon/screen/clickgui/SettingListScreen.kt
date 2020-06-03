package cn.enaium.epsilon.screen.clickgui

import cn.enaium.epsilon.Epsilon
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

    init {
        val x = Render2DUtils.scaledWidth / 2 - 50
        var y = 50
        val scrollPanel = ScrollPanel(x, 50, 100, 120)
        for (setting in Epsilon.settingManager.getSettingsForFunc(func)) {
            if (setting is EnableSetting) {
                scrollPanel.addElement(object :
                    CheckBox(Render2DUtils.scaledWidth / 2 - 50, y, setting.name, setting.enable) {
                    override fun onLeftClicked() {
                        setting.enable = this.checked
                        super.onLeftClicked()
                    }
                })
            } else if (setting is IntegerSetting) {
                val textField = TextField(x, y, 50)
                textField.setText(setting.current.toString())
                scrollPanel.addElementAll(textField, object : Button(x + 50, y, 50, 20, "SET") {
                    override fun onLeftClicked() {
                        setting.current = textField.getText().toInt()
                        super.onLeftClicked()
                    }
                })
            } else if (setting is IntegerSetting || setting is FloatSetting || setting is DoubleSetting || setting is LongSetting) {
                val textField = TextField(x, y, 50)
                scrollPanel.addElement(textField)
                when (setting) {
                    is IntegerSetting -> {
                        textField.setText(setting.current.toString())
                        scrollPanel.addElement(object : Button(x + 60, y, 40, 20, "SET") {
                            override fun onLeftClicked() {
                                setting.current = textField.getText().toInt()
                                super.onLeftClicked()
                            }
                        })
                    }
                    is FloatSetting -> {
                        textField.setText(setting.current.toString())
                        scrollPanel.addElement(object : Button(x + 60, y, 40, 20, "SET") {
                            override fun onLeftClicked() {
                                setting.current = textField.getText().toFloat()
                                super.onLeftClicked()
                            }
                        })
                    }
                    is DoubleSetting -> {
                        textField.setText(setting.current.toString())
                        scrollPanel.addElement(object : Button(x + 60, y, 40, 20, "SET") {
                            override fun onLeftClicked() {
                                setting.current = textField.getText().toDouble()
                                super.onLeftClicked()
                            }
                        })
                    }
                    is LongSetting -> {
                        textField.setText(setting.current.toString())
                        scrollPanel.addElement(object : Button(x + 60, y, 40, 20, "SET") {
                            override fun onLeftClicked() {
                                setting.current = textField.getText().toLong()
                                super.onLeftClicked()
                            }
                        })
                    }
                }
            } else if (setting is ModeSetting) {
                scrollPanel.addElement(object : ModeButton(x, y, setting.modes, setting.getCurrentIndex()) {
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