package cn.enaium.epsilon.setting.settings

import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.module.Module

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class SettingFloat(module: Module, name: String, var current: Float, var min: Float, var max: Float) : Setting(module, name)