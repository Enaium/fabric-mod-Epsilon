package cn.enaium.epsilon.screen.clickgui

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.setting.settings.BlockListSetting
import cn.enaium.epsilon.ui.UI
import cn.enaium.epsilon.ui.elements.Button
import cn.enaium.epsilon.ui.elements.Image
import cn.enaium.epsilon.ui.elements.ScrollPanel
import cn.enaium.epsilon.ui.elements.TextField
import cn.enaium.epsilon.utils.BlockUtils
import cn.enaium.epsilon.utils.Render2DUtils
import net.minecraft.block.Blocks
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
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
            var itemStack = ItemStack(BlockUtils.getBlockFromName(block))
            if (itemStack.isEmpty) {
                itemStack = ItemStack(Blocks.GRASS)
            }
            scrollPanel.addElementAll(
                Image(0, y, itemStack),
                object : Button(24, y, 40, 16, "Remove") {
                    override fun onLeftClicked() {
                        blockListSetting.blockList.remove(block)
                        MC.openScreen(EditBlockListSettingScreen(blockListSetting))
                        super.onLeftClicked()
                    }
                })
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