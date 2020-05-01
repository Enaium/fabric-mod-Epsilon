package cn.enaium.epsilon.plugin

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.plugin.api.PluginInitializer
import cn.enaium.epsilon.utils.FileUtils
import com.alibaba.fastjson.JSON
import java.io.File
import java.net.URL
import java.net.URLClassLoader

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class PluginManager {
    private val file = File(Epsilon.DIR + "plugins")
    private val plugins: ArrayList<PluginInitializer> = ArrayList()

    init {
        file.mkdir()
        try {
            if (file.listFiles().isNotEmpty()) {
                for (f in file.listFiles()) {
                    if (f.name.substring(f.name.lastIndexOf(".") + 1) == "jar") {
                        val u = URLClassLoader(arrayOf<URL>(f.toURL()), Thread.currentThread().contextClassLoader)
                        val json = JSON.parseObject(FileUtils.readResource(u.getResourceAsStream("epsilon.plugin.json")))
                        plugins.add(u.loadClass(json["mainClass"].toString()).newInstance() as PluginInitializer)
                    }
                }
            }

            if (plugins.isNotEmpty()) {
                for (p in plugins) {
                    p.onInitialize()
                }
            }
        } catch (e: Exception) {

        }
    }
}