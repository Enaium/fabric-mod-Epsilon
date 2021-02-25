package cn.enaium.epsilon.client.configs

import cn.enaium.cf4m.annotation.config.Config
import cn.enaium.cf4m.annotation.config.Load
import cn.enaium.cf4m.annotation.config.Save
import cn.enaium.epsilon.client.cf4m
import cn.enaium.epsilon.client.settings.*
import cn.enaium.epsilon.client.utils.FileUtils
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Config("Functions")
class FuncConfig {
    @Load
    fun load() {
        val funcObject = Gson().fromJson(FileUtils.read(cf4m.config.getPath(this)), JsonObject::class.java)
        for (func in cf4m.module.modules) {
            if (funcObject != null) {
                if (funcObject.has(cf4m.module.getName(func))) {
                    val funcClassObject = funcObject.getAsJsonObject(cf4m.module.getName(func))
                    if (funcClassObject.get("enable").asBoolean) cf4m.module.enable(func)
                    cf4m.module.setKey(func, funcClassObject.get("key").asInt)
                    val settings = cf4m.setting.getSettings(func)
                    if (settings != null) {
                        val settingObject = funcClassObject.getAsJsonObject("settings")
                        for (setting in settings) {
                            if (settingObject != null) {
                                if (settingObject.has(cf4m.setting.getName(func, setting))) {
                                    when (setting) {
                                        is EnableSetting -> {
                                            setting.enable =
                                                settingObject.get(cf4m.setting.getName(func, setting)).asBoolean
                                        }
                                        is IntegerSetting -> {
                                            setting.current =
                                                settingObject.get(cf4m.setting.getName(func, setting)).asInt
                                        }
                                        is FloatSetting -> {
                                            setting.current =
                                                settingObject.get(cf4m.setting.getName(func, setting)).asFloat
                                        }
                                        is DoubleSetting -> {
                                            setting.current =
                                                settingObject.get(cf4m.setting.getName(func, setting)).asDouble
                                        }
                                        is LongSetting -> {
                                            setting.current =
                                                settingObject.get(cf4m.setting.getName(func, setting)).asLong
                                        }
                                        is ModeSetting -> {
                                            setting.current =
                                                settingObject.get(cf4m.setting.getName(func, setting)).asString
                                        }
                                        is BlockListSetting -> {
                                            setting.blockList.clear()
                                            val blockArray = settingObject.getAsJsonArray(
                                                cf4m.setting.getName(
                                                    func,
                                                    setting
                                                )
                                            )
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
        val funcObject = JsonObject()
        for (func in cf4m.module.modules) {
            val funcClassObject = JsonObject()
            funcClassObject.addProperty("enable", cf4m.module.getEnable(func))
            funcClassObject.addProperty("key", cf4m.module.getKey(func))
            val settings = cf4m.setting.getSettings(func)
            if (settings != null) {
                val settingObject = JsonObject()
                for (setting in settings) {
                    when (setting) {
                        is EnableSetting -> {
                            settingObject.addProperty(cf4m.setting.getName(func, setting), setting.enable)
                        }
                        is IntegerSetting -> {
                            settingObject.addProperty(cf4m.setting.getName(func, setting), setting.current)
                        }
                        is FloatSetting -> {
                            settingObject.addProperty(cf4m.setting.getName(func, setting), setting.current)
                        }
                        is DoubleSetting -> {
                            settingObject.addProperty(cf4m.setting.getName(func, setting), setting.current)
                        }
                        is LongSetting -> {
                            settingObject.addProperty(cf4m.setting.getName(func, setting), setting.current)
                        }
                        is ModeSetting -> {
                            settingObject.addProperty(cf4m.setting.getName(func, setting), setting.current)
                        }
                        is BlockListSetting -> {
                            val list = JsonArray()
                            for (block in setting.blockList) {
                                list.add(block)
                            }
                            settingObject.add(cf4m.setting.getName(func, setting), list)
                        }
                    }
                }
                funcClassObject.add("settings", settingObject)
            }
            funcObject.add(cf4m.module.getName(func), funcClassObject)
        }
        FileUtils.write(
            cf4m.config.getPath(this),
            funcObject.asString
        )
    }

}