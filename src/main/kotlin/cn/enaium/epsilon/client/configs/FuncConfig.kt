package cn.enaium.epsilon.client.configs

import cn.enaium.cf4m.annotation.config.Config
import cn.enaium.cf4m.annotation.config.Load
import cn.enaium.cf4m.annotation.config.Save
import cn.enaium.cf4m.setting.settings.*
import cn.enaium.epsilon.client.cf4m
import cn.enaium.epsilon.client.setting.BlockListSetting
import cn.enaium.epsilon.client.utils.FileUtils
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.serializer.SerializerFeature


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Config("Functions")
class FuncConfig {
    @Load
    fun load() {
        val funcObject = JSON.parseObject(FileUtils.read(cf4m.config.getPath(this)))
        for (func in cf4m.module.modules) {
            if (funcObject != null) {
                if (funcObject.containsKey(cf4m.module.getName(func))) {
                    val funcClassObject =
                        JSON.parseObject(funcObject.getString(cf4m.module.getName(func)))
                    if (funcClassObject.getBoolean("enable")) cf4m.module.enable(func)
                    cf4m.module.setKey(func, funcClassObject.getInteger("key"))
                    val settings = cf4m.module.getSettings(func)
                    if (settings.isNotEmpty()) {
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
                                        is BlockListSetting -> {
                                            setting.blockList.clear()
                                            val blockArray = JSON.parseArray(settingObject.getString(setting.name))
                                            for (ba in blockArray) {
                                                setting.blockList.add(ba.toString())
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
    }

    @Save
    fun save() {
        val funcObject = JSONObject(true)
        for (func in cf4m.module.modules) {
            val funcClassObject = JSONObject(true)
            funcClassObject["enable"] = cf4m.module.getEnable(func)
            funcClassObject["key"] = cf4m.module.getKey(func)
            val settings = cf4m.module.getSettings(func)
            if (settings.isNotEmpty()) {
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
                        is BlockListSetting -> {
                            settingObject[s.name] = s.blockList
                        }
                    }
                }
                funcClassObject["settings"] = settingObject
            }
            funcObject[cf4m.module.getName(func)] = funcClassObject
        }
        FileUtils.write(
            cf4m.config.getPath(this),
            JSON.toJSONString(funcObject, SerializerFeature.PrettyFormat)
        )
    }

}