package cn.enaium.epsilon.screen.clickgui

import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.setting.BlockListSetting
import cn.enaium.epsilon.ui.UI
import cn.enaium.epsilon.ui.elements.*
import cn.enaium.epsilon.client.utils.BlockUtils
import net.minecraft.item.ItemStack

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class EditBlockListSettingScreen(val blockListSetting: BlockListSetting) : UI() {

    override fun initUI() {
        super.initUI()

        val scrollPanel = object : ScrollPanel(width / 2 - 35, height / 2 - 50, 70, 100) {
            override fun renderBackground() {
                renderBackground(BACKGROUND_STONE)
                super.renderBackground()
            }
        }
        var y = 0
        for (block in blockListSetting.blockList) {
            val itemStack = ItemStack(BlockUtils.getBlockFromName(block))
            scrollPanel.addElementAll(
                    Image(0, y, itemStack),
                    object : Button(24, y, 40, 16, "Remove") {
                        override fun onLeftClicked() {
                            blockListSetting.blockList.remove(block)
                            MC.openScreen(EditBlockListSettingScreen(blockListSetting))
                            super.onLeftClicked()
                        }
                    })
            if (itemStack.isEmpty) {
                scrollPanel.addElement(Label(0, y, 16, 16, "?"))
            }

            y += 24
        }
        addElement(scrollPanel)
        val textField = TextField(width / 2 - 60, 20, 100, 20)
        addElementAll(textField, object : Button(width / 2 - 60 + 120, 20, 20, 20, "Add") {
            override fun onLeftClicked() {
                if (textField.getText().contains(":")) {
                    blockListSetting.blockList.add(textField.getText())
                } else {
                    blockListSetting.blockList.add("minecraft:${textField.getText()}")
                }
                MC.openScreen(EditBlockListSettingScreen(blockListSetting))
                super.onLeftClicked()
            }
        })
    }

    override fun isPauseScreen(): Boolean {
        return false
    }
}