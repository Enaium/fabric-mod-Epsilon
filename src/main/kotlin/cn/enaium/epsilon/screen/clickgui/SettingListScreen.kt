package cn.enaium.epsilon.screen.clickgui

import cn.enaium.cf4m.setting.settings.*
import cn.enaium.epsilon.client.Epsilon
import cn.enaium.epsilon.client.MC
import cn.enaium.cf4m.CF4M
import cn.enaium.epsilon.client.setting.BlockListSetting
import cn.enaium.epsilon.ui.UI
import cn.enaium.epsilon.ui.elements.*
import cn.enaium.epsilon.client.utils.Render2DUtils
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class SettingListScreen(val func: Any) : UI() {

    override fun initUI() {
        super.initUI()
        var y = 0
        val scrollPanel = ScrollPanel(Render2DUtils.scaledWidth / 2 - 50, 50, 100, 120)
        for (setting in CF4M.getInstance().module.getSettings(func)) {
            if (setting is EnableSetting) {
                scrollPanel.addElement(object :
                    CheckBox(0, y, setting.name, setting.isEnable) {
                    override fun onLeftClicked() {
                        setting.isEnable = this.checked
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
                scrollPanel.addElement(object : ModeButton(0, y, setting.modes as ArrayList<String>, getCurrentModeIndex(setting)) {
                    override fun onLeftClicked() {
                        try {
                            setting.current = setting.modes[getCurrentModeIndex(setting) + 1]
                        } catch (e: Exception) {
                            setting.current = setting.modes.first()
                        }
                        this.current = getCurrentModeIndex(setting)
                        super.onLeftClicked()
                    }

                    override fun onRightClicked() {
                        try {
                            setting.current = setting.modes[getCurrentModeIndex(setting) - 1]
                        } catch (e: Exception) {
                            setting.current = setting.modes.last()
                        }
                        this.current = getCurrentModeIndex(setting)
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

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
            MC.openScreen(CategoryListScreen())
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun shouldCloseOnEsc(): Boolean {
        return false
    }
}