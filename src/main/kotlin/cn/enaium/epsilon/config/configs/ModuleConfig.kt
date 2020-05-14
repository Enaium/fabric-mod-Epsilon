package cn.enaium.epsilon.config.configs

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.config.Config
import cn.enaium.epsilon.setting.settings.*
import cn.enaium.epsilon.utils.FileUtils
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.serializer.SerializerFeature


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class ModuleConfig : Config("Modules") {

    override fun load() {
        super.load()
        val moduleObject = JSON.parseObject(FileUtils.read(getPath()))
        for (module in Epsilon.moduleManager.modules) {
            val moduleClassObject = JSON.parseObject(moduleObject.getString(module.name))
            if (moduleClassObject.getBoolean("enable")) module.enable()
            module.keyCode = moduleClassObject.getInteger("keyCode")
            val settings = Epsilon.settingManager.getSettingsForModule(module)
            if (settings != null) {
                val settingObject = JSON.parseObject(moduleClassObject.getString("settings"))
                for (setting in settings) {
                    when (setting) {
                        is SettingEnable -> {
                            setting.enable = settingObject.getBoolean(setting.name)
                        }
                        is SettingInteger -> {
                            setting.current = settingObject.getInteger(setting.name)
                        }
                        is SettingFloat -> {
                            setting.current = settingObject.getFloat(setting.name)
                        }
                        is SettingDouble -> {
                            setting.current = settingObject.getDouble(setting.name)
                        }
                        is SettingLong -> {
                            setting.current = settingObject.getLong(setting.name)
                        }
                        is SettingMode -> {
                            setting.current = settingObject.getString(setting.name)
                        }
                    }
                }
            }
        }
    }

    override fun save() {
        super.save()
        val moduleObject = JSONObject(true)
        for (module in Epsilon.moduleManager.modules) {
            val moduleClassObject = JSONObject(true)
            moduleClassObject["enable"] = module.enable
            moduleClassObject["keyCode"] = module.keyCode
            val settings = Epsilon.settingManager.getSettingsForModule(module)
            if (settings != null) {
                val settingObject = JSONObject(true)
                for (s in settings) {
                    when (s) {
                        is SettingEnable -> {
                            settingObject[s.name] = s.enable
                        }
                        is SettingInteger -> {
                            settingObject[s.name] = s.current
                        }
                        is SettingFloat -> {
                            settingObject[s.name] = s.current
                        }
                        is SettingDouble -> {
                            settingObject[s.name] = s.current
                        }
                        is SettingLong -> {
                            settingObject[s.name] = s.current
                        }
                        is SettingMode -> {
                            settingObject[s.name] = s.current
                        }
                    }
                }
                moduleClassObject["settings"] = settingObject
            }
            moduleObject[module.name] = moduleClassObject
        }
        FileUtils.write(getPath(), JSON.toJSONString(moduleObject, SerializerFeature.PrettyFormat))
    }

}