package cn.enaium.epsilon.module

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.setting.SettingManager
import cn.enaium.epsilon.utils.I18nUtils

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
open class Module(var name: String, var keyCode: Int, var category: Category) {

    var enable = false
    var tag = ""

    fun addSetting(setting: Setting) {
        SettingManager.settings.add(setting)
    }

    fun enable() {
        enable = !enable
        if (enable) onEnable() else onDisable()
    }

    open fun onEnable() {
        Epsilon.eventManager.register(this)
    }

    open fun onDisable() {
        Epsilon.eventManager.unregister(this)
    }

    open fun getDisplayNameTag(): String {
        return if (getDisplayTag() == "") {
            getDisplayName()
        } else {
            getDisplayName() + ":" + getDisplayTag()
        }
    }

    open fun getDisplayName(): String {
        return try {
            I18nUtils.getKey("module." + category.name + "." + name)
        } catch (e: Exception) {
            name
        }
    }

    open fun getDisplayTag(): String {
        return if (tag == "") {
            ""
        } else try {
            I18nUtils.getKey("module." + category.name + ".setting." + tag)
        } catch (e: Exception) {
            return tag
        }
    }


}