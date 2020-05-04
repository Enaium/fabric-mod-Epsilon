package cn.enaium.epsilon.module.modules.render;

import cn.enaium.epsilon.Epsilon;
import cn.enaium.epsilon.event.EventTarget;
import cn.enaium.epsilon.event.events.EventKeyboard;
import cn.enaium.epsilon.event.events.EventRender2D;
import cn.enaium.epsilon.module.Category;
import cn.enaium.epsilon.module.Module;
import cn.enaium.epsilon.setting.Setting;
import cn.enaium.epsilon.setting.SettingManager;
import cn.enaium.epsilon.setting.settings.*;
import cn.enaium.epsilon.utils.*;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HUDModule extends Module {

    private ArrayList<Category> categoryValues;
    private int currentCategoryIndex, currentModIndex, currentSettingIndex;
    private boolean editMode;

    private int screen;

    private SettingEnable tabGUI = new SettingEnable(this, "TabGUI", true);
    private SettingEnable list = new SettingEnable(this, "List", true);

    public HUDModule() {
        super("HUD", GLFW.GLFW_KEY_P, Category.RENDER);
        this.categoryValues = new ArrayList();
        this.currentCategoryIndex = 0;
        this.currentModIndex = 0;
        this.currentSettingIndex = 0;
        this.editMode = false;
        this.screen = 0;
        this.categoryValues.addAll(Arrays.asList(Category.values()));
        addSettings(Arrays.asList(tabGUI, list));
    }

    @EventTarget
    public void list(EventRender2D e) {
        if (!this.list.getEnable()) return;

        int yStart = 1;

        ArrayList<Module> modules = new ArrayList<>();
        for (Module m : Epsilon.moduleManager.getModules()) {
            if (m.getEnable()) {
                modules.add(m);
            }
        }

        List<Module> mods = modules;
        mods.sort((o1, o2) -> FontUtils.INSTANCE.getStringWidth(o2.getDisplayTag()) - FontUtils.INSTANCE.getStringWidth(o1.getDisplayTag()));

        for (Module module : mods) {

            int startX = e.getWidth() - FontUtils.INSTANCE.getStringWidth(module.getDisplayTag()) - 6;

            Render2DUtils.drawRect(startX, yStart - 1, e.getWidth(), yStart + 12, ColorUtils.BG);
            Render2DUtils.drawRect(e.getWidth() - 2, yStart - 1, e.getWidth(), yStart + 12, ColorUtils.SELECT);

            Render2DUtils.drawVerticalLine(startX - 1, yStart - 2, yStart + 12, ColorUtils.SELECT);
            Render2DUtils.drawHorizontalLine(startX - 1, e.getWidth(), yStart + 12, ColorUtils.SELECT);

            FontUtils.INSTANCE.drawStringWithShadow(module.getName(), startX + 3, yStart, ColorUtils.SELECT);

            yStart += FontUtils.INSTANCE.getFontHeight() + 4;
        }
    }

    @EventTarget
    public void tabGUI(EventRender2D e) {
        if (!this.tabGUI.getEnable()) return;

        this.renderTopString(5, 5);
        int startX = 5;
        int startY = (5 + 9) + 2;
        Render2DUtils.drawRect(startX, startY, startX + this.getWidestCategory() + 5, startY + this.categoryValues.size() * (FontUtils.INSTANCE.getFontHeight() + 2), ColorUtils.BG);
        for (Category c : this.categoryValues) {
            if (this.getCurrentCategory().equals(c)) {
                Render2DUtils.drawRect(startX + 1, startY, startX + this.getWidestCategory() + 5 - 1, startY + FontUtils.INSTANCE.getFontHeight() + 2, ColorUtils.SELECT);
            }

            String name = c.name();
            FontUtils.INSTANCE.drawStringWithShadow(name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase(), startX + 2 + (this.getCurrentCategory().equals(c) ? 2 : 0), startY + 2, -1);
            startY += FontUtils.INSTANCE.getFontHeight() + 2;
        }

        if (screen == 1 || screen == 2) {
            int startModsX = startX + this.getWidestCategory() + 6;
            int startModsY = ((5 + 9) + 2) + currentCategoryIndex * (FontUtils.INSTANCE.getFontHeight() + 2);
            Render2DUtils.drawRect(startModsX, startModsY, startModsX + this.getWidestMod() + 5, startModsY + this.getModsForCurrentCategory().size() * (FontUtils.INSTANCE.getFontHeight() + 2), ColorUtils.BG);
            for (Module m : getModsForCurrentCategory()) {
                if (this.getCurrentModule().equals(m)) {
                    Render2DUtils.drawRect(startModsX + 1, startModsY, startModsX + this.getWidestMod() + 5 - 1, startModsY + FontUtils.INSTANCE.getFontHeight() + 2, ColorUtils.SELECT);
                }
                FontUtils.INSTANCE.drawStringWithShadow(m.getName() + (SettingManager.INSTANCE.getSettingsForModule(m) != null ? ">" : ""), startModsX + 2 + (this.getCurrentModule().equals(m) ? 2 : 0), startModsY + 2, m.getEnable() ? -1 : Color.GRAY.getRGB());
                startModsY += FontUtils.INSTANCE.getFontHeight() + 2;
            }
        }
        if (screen == 2) {
            int startSettingX = (startX + this.getWidestCategory() + 6) + this.getWidestCategory() + 8;
            int startSettingY = ((5 + 9) + 2) + (currentCategoryIndex * (9 + 2)) + currentModIndex * (9 + 2);

            Render2DUtils.drawRect(startSettingX, startSettingY, startSettingX + this.getWidestSetting() + 5, startSettingY + this.getSettingForCurrentMod().size() * (FontUtils.INSTANCE.getFontHeight() + 2), ColorUtils.BG);
            for (Setting s : this.getSettingForCurrentMod()) {

                if (this.getCurrentSetting().equals(s)) {
                    Render2DUtils.drawRect(startSettingX + 1, startSettingY, startSettingX + this.getWidestSetting() + 5 - 1, startSettingY + FontUtils.INSTANCE.getFontHeight() + 2, ColorUtils.SELECT);
                }
                if (s instanceof SettingEnable) {
                    FontUtils.INSTANCE.drawStringWithShadow(s.getName() + ": " + ((SettingEnable) s).getEnable(), startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2, editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof SettingInteger) {
                    FontUtils.INSTANCE.drawStringWithShadow(s.getName() + ": " + ((SettingInteger) s).getCurrent(), startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2, editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof SettingDouble) {
                    FontUtils.INSTANCE.drawStringWithShadow(s.getName() + ": " + ((SettingDouble) s).getCurrent(), startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2, editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof SettingFloat) {
                    FontUtils.INSTANCE.drawStringWithShadow(s.getName() + ": " + ((SettingFloat) s).getCurrent(), startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2, editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof SettingLong) {
                    FontUtils.INSTANCE.drawStringWithShadow(s.getName() + ": " + ((SettingLong) s).getCurrent(), startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2, editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof SettingMode) {
                    FontUtils.INSTANCE.drawStringWithShadow(s.getName() + ": " + ((SettingMode) s).getCurrent(), startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2, editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                }
                startSettingY += FontUtils.INSTANCE.getFontHeight() + 2;
            }
        }
    }

    private void renderTopString(int x, int y) {
        FontUtils.INSTANCE.drawStringWithShadow(Epsilon.INSTANCE.getNAME() + " B" + Epsilon.INSTANCE.getVERSION(), x, y, new Color(67, 0, 99).getRGB());
    }

    private void up() {
        if (this.currentCategoryIndex > 0 && this.screen == 0) {
            this.currentCategoryIndex--;
        } else if (this.currentCategoryIndex == 0 && this.screen == 0) {
            this.currentCategoryIndex = this.categoryValues.size() - 1;
        } else if (this.currentModIndex > 0 && this.screen == 1) {
            this.currentModIndex--;
        } else if (this.currentModIndex == 0 && this.screen == 1) {
            this.currentModIndex = this.getModsForCurrentCategory().size() - 1;
        } else if (this.currentSettingIndex > 0 && this.screen == 2 && !this.editMode) {
            this.currentSettingIndex--;
        } else if (this.currentSettingIndex == 0 && this.screen == 2 && !this.editMode) {
            this.currentSettingIndex = this.getSettingForCurrentMod().size() - 1;
        }

        if (editMode) {
            Setting s = this.getCurrentSetting();
            if (s instanceof SettingEnable) {
                ((SettingEnable) s).setEnable(!((SettingEnable) s).getEnable());
            } else if (s instanceof SettingInteger) {
                if (((SettingInteger) s).getCurrent() < ((SettingInteger) s).getMax())
                    ((SettingInteger) s).setCurrent(((SettingInteger) s).getCurrent() + 1);
            } else if (s instanceof SettingDouble) {
                if (((SettingDouble) s).getCurrent() < ((SettingDouble) s).getMax())
                    ((SettingDouble) s).setCurrent(Utils.INSTANCE.valueFix(((SettingDouble) s).getCurrent() + 0.1D));
            } else if (s instanceof SettingFloat) {
                if (((SettingFloat) s).getCurrent() < ((SettingFloat) s).getMax())
                    ((SettingFloat) s).setCurrent(Utils.INSTANCE.valueFix(((SettingFloat) s).getCurrent() + 0.1F));
            } else if (s instanceof SettingLong) {
                if (((SettingLong) s).getCurrent() < ((SettingLong) s).getMax())
                    ((SettingLong) s).setCurrent(((SettingLong) s).getCurrent() + 1);
            } else {
                try {
                    ((SettingMode) s).setCurrent(((SettingMode) s).getModes().get(((SettingMode) s).getCurrentIndex() - 1));
                } catch (Exception e) {
                    ((SettingMode) s).setCurrent(((SettingMode) s).getModes().get(((SettingMode) s).getModes().size() - 1));
                }
            }
        }
    }

    private void down() {
        if (this.currentCategoryIndex < this.categoryValues.size() - 1 && this.screen == 0) {
            this.currentCategoryIndex++;
        } else if (this.currentCategoryIndex == this.categoryValues.size() - 1 && this.screen == 0) {
            this.currentCategoryIndex = 0;
        } else if (this.currentModIndex < this.getModsForCurrentCategory().size() - 1 && this.screen == 1) {
            this.currentModIndex++;
        } else if (this.currentModIndex == this.getModsForCurrentCategory().size() - 1 && this.screen == 1) {
            this.currentModIndex = 0;
        } else if (this.currentSettingIndex < this.getSettingForCurrentMod().size() - 1 && this.screen == 2 && !this.editMode) {
            this.currentSettingIndex++;
        } else if (this.currentSettingIndex == this.getSettingForCurrentMod().size() - 1 && this.screen == 2 && !this.editMode) {
            this.currentSettingIndex = 0;
        }

        if (editMode) {
            Setting s = this.getCurrentSetting();
            if (s instanceof SettingEnable) {
                ((SettingEnable) s).setEnable(!((SettingEnable) s).getEnable());
            } else if (s instanceof SettingInteger) {
                if (((SettingInteger) s).getCurrent() > ((SettingInteger) s).getMin())
                    ((SettingInteger) s).setCurrent(((SettingInteger) s).getCurrent() - 1);
            } else if (s instanceof SettingDouble) {
                if (((SettingDouble) s).getCurrent() > ((SettingDouble) s).getMin())
                    ((SettingDouble) s).setCurrent(Utils.INSTANCE.valueFix(((SettingDouble) s).getCurrent() - 0.1D));
            } else if (s instanceof SettingFloat) {
                if (((SettingFloat) s).getCurrent() > ((SettingFloat) s).getMin())
                    ((SettingFloat) s).setCurrent(Utils.INSTANCE.valueFix(((SettingFloat) s).getCurrent() - 0.1F));
            } else if (s instanceof SettingLong) {
                if (((SettingLong) s).getCurrent() > ((SettingLong) s).getMin())
                    ((SettingLong) s).setCurrent(((SettingLong) s).getCurrent() - 1);
            } else {
                try {
                    ((SettingMode) s).setCurrent(((SettingMode) s).getModes().get(((SettingMode) s).getCurrentIndex() + 1));
                } catch (Exception e) {
                    ((SettingMode) s).setCurrent(((SettingMode) s).getModes().get(0));
                }
            }
        }
    }


    private void right(int key) {
        if (this.screen == 0) {
            this.screen = 1;
        } else if (this.screen == 1 && this.getCurrentModule() != null && this.getSettingForCurrentMod() == null) {
            this.getCurrentModule().enable();
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null && key == GLFW.GLFW_KEY_ENTER) {
            this.getCurrentModule().enable();
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null) {
            this.screen = 2;
        } else if (this.screen == 2) {
            this.editMode = !this.editMode;
        }

    }

    private void left() {
        if (this.screen == 1) {
            this.screen = 0;
            this.currentModIndex = 0;
        } else if (this.screen == 2) {
            this.screen = 1;
            this.currentSettingIndex = 0;
        }

    }

    @EventTarget
    public void onKey(EventKeyboard eventKeyBoard) {

        if (Epsilon.INSTANCE.getMC().currentScreen != null) return;
        if (eventKeyBoard.getAction() != GLFW.GLFW_PRESS) return;

        switch (eventKeyBoard.getKeyCode()) {
            case GLFW.GLFW_KEY_UP:
                this.up();
                break;
            case GLFW.GLFW_KEY_DOWN:
                this.down();
                break;
            case GLFW.GLFW_KEY_RIGHT:
                this.right(GLFW.GLFW_KEY_RIGHT);
                break;
            case GLFW.GLFW_KEY_LEFT:
                this.left();
                break;
            case GLFW.GLFW_KEY_ENTER:
                this.right(GLFW.GLFW_KEY_ENTER);
                break;
        }
    }

    private Setting getCurrentSetting() {
        return getSettingForCurrentMod().get(currentSettingIndex);
    }

    private ArrayList<Setting> getSettingForCurrentMod() {
        return SettingManager.INSTANCE.getSettingsForModule(getCurrentModule());
    }

    private Category getCurrentCategory() {
        return this.categoryValues.get(this.currentCategoryIndex);
    }

    private Module getCurrentModule() {
        return getModsForCurrentCategory().get(currentModIndex);
    }

    private ArrayList<Module> getModsForCurrentCategory() {
        return Epsilon.moduleManager.getModulesForCategory(getCurrentCategory());
    }

    private int getWidestSetting() {
        int width = 0;
        for (Setting s : getSettingForCurrentMod()) {
            String name;
            if (s instanceof SettingEnable) {
                name = s.getName() + ": " + ((SettingEnable) s).getEnable();
            } else if (s instanceof SettingInteger) {
                name = s.getName() + ": " + ((SettingInteger) s).getCurrent();
            } else if (s instanceof SettingFloat) {
                name = s.getName() + ": " + ((SettingFloat) s).getCurrent();
            } else if (s instanceof SettingDouble) {
                name = s.getName() + ": " + ((SettingDouble) s).getCurrent();
            } else if (s instanceof SettingLong) {
                name = s.getName() + ": " + ((SettingLong) s).getCurrent();
            } else {
                name = s.getName() + ": " + ((SettingMode) s).getCurrent();
            }
            if (FontUtils.INSTANCE.getStringWidth(name) > width) {
                width = FontUtils.INSTANCE.getStringWidth(name);
            }
        }
        return width;
    }

    private int getWidestMod() {
        int width = 0;
        for (Module m : Epsilon.moduleManager.getModules()) {
            int cWidth = FontUtils.INSTANCE.getStringWidth(m.getName());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }

    private int getWidestCategory() {
        int width = 0;
        for (Category c : this.categoryValues) {
            String name = c.name();
            int cWidth = FontUtils.INSTANCE.getStringWidth(name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }


}
