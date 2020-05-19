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
class FuncConfig : Config("Functions") {

    override fun load() {
        super.load()
        val funcObject = JSON.parseObject(FileUtils.read(getPath()))
        for (func in Epsilon.funcManager.functions) {
            if (funcObject != null) {
                if (funcObject.containsKey(func.name)) {
                    val funcClassObject = JSON.parseObject(funcObject.getString(func.name))
                    if (funcClassObject.getBoolean("enable")) func.enable()
                    func.keyCode = funcClassObject.getInteger("keyCode")
                    val settings = Epsilon.settingManager.getSettingsForFunc(func)
                    if (settings != null) {
                        val settingObject = JSON.parseObject(funcClassObject.getString("settings"))
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
        val funcObject = JSONObject(true)
        for (func in Epsilon.funcManager.functions) {
            val funcClassObject = JSONObject(true)
            funcClassObject["enable"] = func.enable
            funcClassObject["keyCode"] = func.keyCode
            val settings = Epsilon.settingManager.getSettingsForFunc(func)
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
                funcClassObject["settings"] = settingObject
            }
            funcObject[func.name] = funcClassObject
        }
        FileUtils.write(getPath(), JSON.toJSONString(funcObject, SerializerFeature.PrettyFormat))
    }

}