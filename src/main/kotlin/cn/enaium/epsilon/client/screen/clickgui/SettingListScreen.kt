package cn.enaium.epsilon.client.screen.clickgui

import cn.enaium.cf4m.provider.ModuleProvider
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.setting.*
import cn.enaium.epsilon.client.ui.UI
import cn.enaium.epsilon.client.ui.elements.*
import cn.enaium.epsilon.client.utils.Render2DUtils
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class SettingListScreen(val func: ModuleProvider) : UI() {

    override fun initUI() {
        super.initUI()
        var y = 0
        val scrollPanel = ScrollPanel(Render2DUtils.scaledWidth / 2 - 50, 50, 100, 120)
        val settings = func.setting.all
        if (settings != null) {
            for (setting in settings) {
                when (val s = setting.getSetting<Any>()) {
                    is EnableSetting -> {
                        scrollPanel.addElement(object :
                            CheckBox(0, y, setting.name, s.enable) {
                            override fun onLeftClicked() {
                                s.enable = this.checked
                                super.onLeftClicked()
                            }
                        })
                    }
                    is IntegerSetting, is FloatSetting, is DoubleSetting, is LongSetting -> {
                        val textField = TextField(0, y, 50)
                        textField.setSuggestion(setting.name)
                        when (s) {
                            is IntegerSetting -> {
                                textField.setText(s.current.toString())
                                scrollPanel.addElement(object : Button(60, y, 40, 20, "SET") {
                                    override fun onLeftClicked() {
                                        s.current = textField.getText().toInt()
                                        super.onLeftClicked()
                                    }
                                })
                            }
                            is FloatSetting -> {
                                textField.setText(s.current.toString())
                                scrollPanel.addElement(object : Button(60, y, 40, 20, "SET") {
                                    override fun onLeftClicked() {
                                        s.current = textField.getText().toFloat()
                                        super.onLeftClicked()
                                    }
                                })
                            }
                            is DoubleSetting -> {
                                textField.setText(s.current.toString())
                                scrollPanel.addElement(object : Button(60, y, 40, 20, "SET") {
                                    override fun onLeftClicked() {
                                        s.current = textField.getText().toDouble()
                                        super.onLeftClicked()
                                    }
                                })
                            }
                            is LongSetting -> {
                                textField.setText(s.current.toString())
                                scrollPanel.addElement(object : Button(60, y, 40, 20, "SET") {
                                    override fun onLeftClicked() {
                                        s.current = textField.getText().toLong()
                                        super.onLeftClicked()
                                    }
                                })
                            }
                        }
                        scrollPanel.addElement(textField)
                    }
                    is ModeSetting -> {
                        scrollPanel.addElement(object :
                            ModeButton(0, y, s.modes as ArrayList<String>, getCurrentModeIndex(s)) {
                            override fun onLeftClicked() {
                                try {
                                    s.current = s.modes[getCurrentModeIndex(s) + 1]
                                } catch (e: Exception) {
                                    s.current = s.modes.first()
                                }
                                this.current = getCurrentModeIndex(s)
                                super.onLeftClicked()
                            }

                            override fun onRightClicked() {
                                try {
                                    s.current = s.modes[getCurrentModeIndex(s) - 1]
                                } catch (e: Exception) {
                                    s.current = s.modes.last()
                                }
                                this.current = getCurrentModeIndex(s)
                                super.onRightClicked()
                            }
                        })
                    }
                    is BlockListSetting -> {
                        scrollPanel.addElement(object : Button(0, y, "Set:${setting.name}") {
                            override fun onLeftClicked() {
                                MC.openScreen(EditBlockListSettingScreen(s))
                                super.onLeftClicked()
                            }
                        })
                    }
                }
                y += 30
            }
        }
        addElement(scrollPanel)
    }

    private fun getCurrentModeIndex(modeSetting: ModeSetting): Int {
        var index = 0
        for (ms in modeSetting.modes) {
            index++
            if (ms == ms) {
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