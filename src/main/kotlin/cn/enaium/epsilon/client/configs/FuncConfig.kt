package cn.enaium.epsilon.client.configs

import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.annotation.config.Config
import cn.enaium.cf4m.annotation.config.Load
import cn.enaium.cf4m.annotation.config.Save
import cn.enaium.epsilon.client.setting.*
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
        val funcObject =
            Gson().fromJson(FileUtils.read(CF4M.config.getByInstance(this).path), JsonObject::class.java)
        for (func in CF4M.module.all) {
            if (funcObject != null) {
                if (funcObject.has(func.name)) {
                    val funcClassObject = funcObject.getAsJsonObject(func.name)
                    if (funcClassObject.get("enable").asBoolean) func.enable()
                    func.key = funcClassObject.get("key").asInt
                    val settings = func.setting.all
                    if (settings != null) {
                        val settingObject = funcClassObject.getAsJsonObject("settings")
                        for (setting in settings) {
                            if (settingObject != null) {
                                if (settingObject.has(setting.name)) {
                                    when (val s = setting.getSetting<Any>()) {
                                        is EnableSetting -> {
                                            s.enable =
                                                settingObject.get(
                                                    setting.name
                                                ).asBoolean
                                        }
                                        is IntegerSetting -> {
                                            s.current =
                                                settingObject.get(setting.name).asInt
                                        }
                                        is FloatSetting -> {
                                            s.current =
                                                settingObject.get(setting.name).asFloat
                                        }
                                        is DoubleSetting -> {
                                            s.current =
                                                settingObject.get(setting.name).asDouble
                                        }
                                        is LongSetting -> {
                                            s.current =
                                                settingObject.get(setting.name).asLong
                                        }
                                        is ModeSetting -> {
                                            s.current =
                                                settingObject.get(setting.name).asString
                                        }
                                        is BlockListSetting -> {
                                            s.blockList.clear()
                                            val blockArray = settingObject.getAsJsonArray(
                                                setting.name
                                            )
                                            for (ba in blockArray) {
                                                s.blockList.add(ba.asString)
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
        for (func in CF4M.module.all) {
            val funcClassObject = JsonObject()
            funcClassObject.addProperty("enable", func.enable)
            funcClassObject.addProperty("key", func.key)
            val settings = func.setting.all
            if (settings != null) {
                val settingObject = JsonObject()
                for (setting in settings) {
                    when (val s = setting.getSetting<Any>()) {
                        is EnableSetting -> {
                            settingObject.addProperty(setting.name, s.enable)
                        }
                        is IntegerSetting -> {
                            settingObject.addProperty(setting.name, s.current)
                        }
                        is FloatSetting -> {
                            settingObject.addProperty(setting.name, s.current)
                        }
                        is DoubleSetting -> {
                            settingObject.addProperty(setting.name, s.current)
                        }
                        is LongSetting -> {
                            settingObject.addProperty(setting.name, s.current)
                        }
                        is ModeSetting -> {
                            settingObject.addProperty(setting.name, s.current)
                        }
                        is BlockListSetting -> {
                            val list = JsonArray()
                            for (block in s.blockList) {
                                list.add(block)
                            }
                            settingObject.add(
                                setting.name,
                                list
                            )
                        }
                    }
                }
                funcClassObject.add("settings", settingObject)
            }
            funcObject.add(func.name, funcClassObject)
        }
        FileUtils.write(
            CF4M.config.getByInstance(this).path,
            Gson().toJson(funcObject)
        )
    }

}