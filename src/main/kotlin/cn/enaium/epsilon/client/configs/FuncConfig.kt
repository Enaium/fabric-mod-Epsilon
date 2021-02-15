package cn.enaium.epsilon.client.configs

import cn.enaium.cf4m.annotation.config.Config
import cn.enaium.cf4m.annotation.config.Load
import cn.enaium.cf4m.annotation.config.Save
import cn.enaium.cf4m.setting.settings.*
import cn.enaium.cf4m.CF4M
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
        val funcObject = JSON.parseObject(FileUtils.read(CF4M.getInstance().config.getPath(this)))
        for (func in CF4M.getInstance().module.modules) {
            if (funcObject != null) {
                if (funcObject.containsKey(CF4M.getInstance().module.getName(func))) {
                    val funcClassObject =
                        JSON.parseObject(funcObject.getString(CF4M.getInstance().module.getName(func)))
                    if (funcClassObject.getBoolean("enable")) CF4M.getInstance().module.enable(func)
                    CF4M.getInstance().module.setKey(func, funcClassObject.getInteger("key"))
                    val settings = CF4M.getInstance().module.getSettings(func)
                    if (settings.isNotEmpty()) {
                        val settingObject = JSON.parseObject(funcClassObject.getString("settings"))
                        for (setting in settings) {
                            if (settingObject != null) {
                                if (settingObject.containsKey(setting.name)) {
                                    when (setting) {
                                        is EnableSetting -> {
                                            setting.isEnable = settingObject.getBoolean(setting.name)
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
        for (func in CF4M.getInstance().module.modules) {
            val funcClassObject = JSONObject(true)
            funcClassObject["enable"] = CF4M.getInstance().module.isEnable(func)
            funcClassObject["key"] = CF4M.getInstance().module.getKey(func)
            val settings = CF4M.getInstance().module.getSettings(func)
            if (settings.isNotEmpty()) {
                val settingObject = JSONObject(true)
                for (s in settings) {
                    when (s) {
                        is EnableSetting -> {
                            settingObject[s.name] = s.isEnable
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
            funcObject[CF4M.getInstance().module.getName(func)] = funcClassObject
        }
        FileUtils.write(
            CF4M.getInstance().config.getPath(this),
            JSON.toJSONString(funcObject, SerializerFeature.PrettyFormat)
        )
    }

}