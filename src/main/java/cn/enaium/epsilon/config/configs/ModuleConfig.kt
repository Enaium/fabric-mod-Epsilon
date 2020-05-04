package cn.enaium.epsilon.config.configs

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.config.Config
import cn.enaium.epsilon.setting.SettingManager
import cn.enaium.epsilon.setting.settings.*
import cn.enaium.epsilon.utils.FileUtils
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
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
        for (m in Epsilon.moduleManager.modules) {
            val moduleArray = JSON.parseArray(FileUtils.read(getPath()))
            for (ma in moduleArray) {
                val moduleObject = JSON.parseObject(ma.toString())
                if (m.name == moduleObject["name"]) {
                    if (moduleObject["enable"] as Boolean) m.enable()
                    m.keyCode = moduleObject["keyCode"] as Int
                    val settings = SettingManager.getSettingsForModule(m)
                    if (settings != null) {
                        for (s in settings) {
                            val settingArray = JSON.parseArray(moduleObject["settings"].toString())
                            for (sa in settingArray) {
                                val settingObject = JSON.parseObject(sa.toString())
                                if (settingObject[s.name] != null) {
                                    when (s) {
                                        is SettingEnable -> {
                                            s.enable = settingObject.getBoolean(s.name)
                                        }
                                        is SettingInteger -> {
                                            s.current = settingObject.getInteger(s.name)
                                        }
                                        is SettingFloat -> {
                                            s.current = settingObject.getFloat(s.name)
                                        }
                                        is SettingDouble -> {
                                            s.current = settingObject.getDouble(s.name)
                                        }
                                        is SettingLong -> {
                                            s.current = settingObject.getLong(s.name)
                                        }
                                        is SettingMode -> {
                                            s.current = settingObject.getString(s.name)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun save() {
        super.save()
        val moduleArray = JSONArray()
        for (m in Epsilon.moduleManager.modules) {
            val moduleObject = JSONObject(true)
            moduleObject["name"] = m.name
            moduleObject["enable"] = m.enable
            moduleObject["keyCode"] = m.keyCode

            val settingArray = JSONArray()
            val settings = SettingManager.getSettingsForModule(m)

            if (settings != null) {
                val settingObject = JSONObject()
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
                settingArray.add(settingObject)
                moduleObject["settings"] = settingArray
            }

            moduleArray.add(moduleObject)
        }
        FileUtils.write(getPath(), JSON.toJSONString(moduleArray, SerializerFeature.PrettyFormat))
    }

}