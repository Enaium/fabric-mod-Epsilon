package cn.enaium.epsilon.setting.settings

import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.module.Module

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class SettingInteger(module: Module, name: String, var current: Int, var min: Int, var max: Int) : Setting(module, name)