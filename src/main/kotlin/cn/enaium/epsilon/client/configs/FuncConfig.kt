package cn.enaium.epsilon.client.configs

import cn.enaium.cf4m.annotation.config.Config
import cn.enaium.cf4m.annotation.config.Load
import cn.enaium.cf4m.annotation.config.Save
import cn.enaium.epsilon.client.bean.FuncBean
import cn.enaium.epsilon.client.bean.SettingBean
import cn.enaium.epsilon.client.cf4m
import cn.enaium.epsilon.client.settings.*
import cn.enaium.epsilon.client.utils.FileUtils
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.io.File


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
            val funcBean = FuncBean(func)
            if (funcObject != null) {
                if (funcObject.has(funcBean.name)) {
                    val funcClassObject = funcObject.getAsJsonObject(funcBean.name)
                    if (funcClassObject.get("enable").asBoolean) cf4m.module.enable(funcBean.func)
                    funcBean.key = funcClassObject.get("key").asInt
                    val settings = cf4m.setting.getSettings(funcBean.func)
                    if (settings != null) {
                        val settingObject = funcClassObject.getAsJsonObject("settings")
                        for (setting in settings) {
                            val settingBean = SettingBean(funcBean, setting)
                            if (settingObject != null) {
                                if (settingObject.has(settingBean.name)) {
                                    when (setting) {
                                        is EnableSetting -> {
                                            setting.enable =
                                                settingObject.get(
                                                    settingBean.name
                                                ).asBoolean
                                        }
                                        is IntegerSetting -> {
                                            setting.current =
                                                settingObject.get(settingBean.name).asInt
                                        }
                                        is FloatSetting -> {
                                            setting.current =
                                                settingObject.get(settingBean.name).asFloat
                                        }
                                        is DoubleSetting -> {
                                            setting.current =
                                                settingObject.get(settingBean.name).asDouble
                                        }
                                        is LongSetting -> {
                                            setting.current =
                                                settingObject.get(settingBean.name).asLong
                                        }
                                        is ModeSetting -> {
                                            setting.current =
                                                settingObject.get(settingBean.name).asString
                                        }
                                        is BlockListSetting -> {
                                            setting.blockList.clear()
                                            val blockArray = settingObject.getAsJsonArray(
                                                settingBean.name
                                            )
                                            for (ba in blockArray) {
                                                setting.blockList.add(ba.asString)
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
            val funcBean = FuncBean(func)
            val funcClassObject = JsonObject()
            funcClassObject.addProperty("enable", funcBean.enable)
            funcClassObject.addProperty("key", funcBean.key)
            val settings = cf4m.setting.getSettings(funcBean.func)
            if (settings != null) {
                val settingObject = JsonObject()
                for (setting in settings) {
                    val settingBean = SettingBean(funcBean, setting)
                    when (setting) {
                        is EnableSetting -> {
                            settingObject.addProperty(settingBean.name, setting.enable)
                        }
                        is IntegerSetting -> {
                            settingObject.addProperty(settingBean.name, setting.current)
                        }
                        is FloatSetting -> {
                            settingObject.addProperty(settingBean.name, setting.current)
                        }
                        is DoubleSetting -> {
                            settingObject.addProperty(settingBean.name, setting.current)
                        }
                        is LongSetting -> {
                            settingObject.addProperty(settingBean.name, setting.current)
                        }
                        is ModeSetting -> {
                            settingObject.addProperty(settingBean.name, setting.current)
                        }
                        is BlockListSetting -> {
                            val list = JsonArray()
                            for (block in setting.blockList) {
                                list.add(block)
                            }
                            settingObject.add(
                                settingBean.name,
                                list
                            )
                        }
                    }
                }
                funcClassObject.add("settings", settingObject)
            }
            funcObject.add(funcBean.name, funcClassObject)
        }
        FileUtils.write(
            cf4m.config.getPath(this),
            Gson().toJson(funcObject)
        )
    }

}