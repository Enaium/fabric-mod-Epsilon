package cn.enaium.epsilon.setting.settings

import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.Setting

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class BlockListSetting(func: Func, name: String, var blockList: ArrayList<String>) : Setting(func, name)