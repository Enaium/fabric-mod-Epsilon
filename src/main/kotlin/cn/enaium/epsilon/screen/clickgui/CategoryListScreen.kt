package cn.enaium.epsilon.screen.clickgui

import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.ui.UI
import cn.enaium.epsilon.ui.elements.Button
import cn.enaium.epsilon.client.utils.Render2DUtils

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class CategoryListScreen : UI() {
    override fun initUI() {
        super.initUI()
        var y = 50
        for (category in Category.values()) {
            addElement(object : Button(
                Render2DUtils.scaledWidth / 2 - 50,
                y,
                category.name,
                "icon/${category.name.toLowerCase()}.png"
            ) {
                override fun onLeftClicked() {
                    MC.openScreen(FuncListScreen(category))
                    super.onLeftClicked()
                }
            })
            y += 30
        }
    }

    override fun isPauseScreen(): Boolean {
        return false
    }
}