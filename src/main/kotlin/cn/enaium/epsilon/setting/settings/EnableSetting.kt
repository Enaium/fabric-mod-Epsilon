package cn.enaium.epsilon.setting.settings

import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.func.Func

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class EnableSetting(func: Func, name: String, var enable: Boolean) : Setting(func, name)