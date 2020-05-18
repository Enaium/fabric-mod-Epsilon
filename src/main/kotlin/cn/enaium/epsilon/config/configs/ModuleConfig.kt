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
        for (module in Epsilon.funcManager.funcs) {
            if (moduleObject != null) {
                if (moduleObject.containsKey(module.name)) {
                    val moduleClassObject = JSON.parseObject(moduleObject.getString(module.name))
                    if (moduleClassObject.getBoolean("enable")) module.enable()
                    module.keyCode = moduleClassObject.getInteger("keyCode")
                    val settings = Epsilon.settingManager.getSettingsForModule(module)
                    if (settings != null) {
                        val settingObject = JSON.parseObject(moduleClassObject.getString("settings"))
                        for (setting in settings) {
                            if (settingObject != null) {
                                if (settingObject.containsKey(setting.name)) {
                                    when (setting) {
                                        is EnableSetting -> {
                                            setting.enable = settingObject.getBoolean(setting.name)
                                        }
                                        is IntegerSetting -> {
                                            setting.current = settingObject.getInteger(setting.name)
                                        }
                                        is FloatSetting -> {
                                            setting.current = settingObject.getFloat(setting.name)
                                        }
                                        is DoubleSetting -> {
                                            setting.current = settingObject.getDouble(setting.name)
                                        }
                                        is LongSetting -> {
                                            setting.current = settingObject.getLong(setting.name)
                                        }
                                        is ModeSetting -> {
                                            setting.current = settingObject.getString(setting.name)
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
        val moduleObject = JSONObject(true)
        for (module in Epsilon.funcManager.funcs) {
            val moduleClassObject = JSONObject(true)
            moduleClassObject["enable"] = module.enable
            moduleClassObject["keyCode"] = module.keyCode
            val settings = Epsilon.settingManager.getSettingsForModule(module)
            if (settings != null) {
                val settingObject = JSONObject(true)
                for (s in settings) {
                    when (s) {
                        is EnableSetting -> {
                            settingObject[s.name] = s.enable
                        }
                        is IntegerSetting -> {
                            settingObject[s.name] = s.current
                        }
                        is FloatSetting -> {
                            settingObject[s.name] = s.current
                        }
                        is DoubleSetting -> {
                            settingObject[s.name] = s.current
                        }
                        is LongSetting -> {
                            settingObject[s.name] = s.current
                        }
                        is ModeSetting -> {
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