package cn.enaium.epsilon.screen.clickgui

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.ui.UI
import cn.enaium.epsilon.ui.elements.Button
import cn.enaium.epsilon.utils.Render2DUtils

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class CategoryListScreen : UI() {
    init {
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