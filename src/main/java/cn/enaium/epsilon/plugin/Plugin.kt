package cn.enaium.epsilon.plugin

import cn.enaium.epsilon.plugin.api.PluginInitializer

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
open class Plugin(var name: String, var description: String, var authors: ArrayList<String>, var pluginInitializer: PluginInitializer)