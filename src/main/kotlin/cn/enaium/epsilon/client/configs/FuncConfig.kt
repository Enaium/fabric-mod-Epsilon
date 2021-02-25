package cn.enaium.epsilon.client.configs

import cn.enaium.cf4m.annotation.config.Config
import cn.enaium.cf4m.annotation.config.Load
import cn.enaium.cf4m.annotation.config.Save
import cn.enaium.epsilon.client.cf4m
import cn.enaium.epsilon.client.settings.*
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
                    val settings = cf4m.setting.getSettings(func)
                    if (settings != null) {
                        val settingObject = JSON.parseObject(funcClassObject.getString("settings"))
                        for (setting in settings) {
                            if (settingObject != null) {
                                if (settingObject.containsKey(cf4m.setting.getName(func, setting))) {
                                    when (setting) {
                                        is EnableSetting -> {
                                            setting.enable = settingObject.getBoolean(cf4m.setting.getName(func, setting))
                                        }
                                        is IntegerSetting -> {
                                            setting.current = settingObject.getInteger(cf4m.setting.getName(func, setting))
                                        }
                                        is FloatSetting -> {
                                            setting.current = settingObject.getFloat(cf4m.setting.getName(func, setting))
                                        }
                                        is DoubleSetting -> {
                                            setting.current = settingObject.getDouble(cf4m.setting.getName(func, setting))
                                        }
                                        is LongSetting -> {
                                            setting.current = settingObject.getLong(cf4m.setting.getName(func, setting))
                                        }
                                        is ModeSetting -> {
                                            setting.current = settingObject.getString(cf4m.setting.getName(func, setting))
                                        }
                                        is BlockListSetting -> {
                                            setting.blockList.clear()
                                            val blockArray = JSON.parseArray(settingObject.getString(cf4m.setting.getName(func, setting)))
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
            val settings = cf4m.setting.getSettings(func)
            if (settings != null) {
                val settingObject = JSONObject(true)
                for (setting in settings) {
                    when (setting) {
                        is EnableSetting -> {
                            settingObject[cf4m.setting.getName(func, setting)] = setting.enable
                        }
                        is IntegerSetting -> {
                            settingObject[cf4m.setting.getName(func, setting)] = setting.current
                        }
                        is FloatSetting -> {
                            settingObject[cf4m.setting.getName(func, setting)] = setting.current
                        }
                        is DoubleSetting -> {
                            settingObject[cf4m.setting.getName(func, setting)] = setting.current
                        }
                        is LongSetting -> {
                            settingObject[cf4m.setting.getName(func, setting)] = setting.current
                        }
                        is ModeSetting -> {
                            settingObject[cf4m.setting.getName(func, setting)] = setting.current
                        }
                        is BlockListSetting -> {
                            settingObject[cf4m.setting.getName(func, setting)] = setting.blockList
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