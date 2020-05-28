package cn.enaium.epsilon.config

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.config.configs.ClickGUIConfig
import cn.enaium.epsilon.config.configs.FuncConfig
import java.io.File

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class ConfigManager {
    val configs: ArrayList<Config> = ArrayList()

    init {
        File(Epsilon.DIR + "/configs").mkdir()
        configs.add(FuncConfig())
        configs.add(ClickGUIConfig())
    }

    fun load() {
        for (c in configs) {
            c.load()
        }
    }

    fun save() {
        for (c in configs) {
            if(File(c.getPath()).exists()) {
                c.save()
            }
        }
    }
}