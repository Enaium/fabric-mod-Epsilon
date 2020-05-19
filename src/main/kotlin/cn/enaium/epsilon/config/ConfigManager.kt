package cn.enaium.epsilon.config

import cn.enaium.epsilon.Epsilon
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
    }

    fun load() {
        for (c in configs) {
            if(File(c.getPath()).exists()) {
                c.load()
            }
        }
    }

    fun save() {
        for (c in configs) {
            c.save()
        }
    }
}