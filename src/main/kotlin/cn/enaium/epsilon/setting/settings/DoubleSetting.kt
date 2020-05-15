package cn.enaium.epsilon.setting.settings

import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.module.Module

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class DoubleSetting(module: Module, name: String, var current: Double, var min: Double, var max: Double) : Setting(module, name)