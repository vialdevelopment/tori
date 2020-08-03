package io.github.vialdevelopment.tori.client.gui.scene.module;

import io.github.vialdevelopment.tori.api.runnable.impl.module.Module;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.gui.api.Button;
import io.github.vialdevelopment.tori.client.gui.api.ClickGUIScene;
import io.github.vialdevelopment.tori.client.gui.scene.module.button.*;
import io.github.vialdevelopment.tori.client.modules.render.ClickGUIModule;
import io.github.vialdevelopment.tori.util.Logger;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class ModuleScene implements ClickGUIScene {


    private final int buttonHeight = (int) ((9 * Button.textScale) + 4);

    public double moduleRelativeY = 20;

    public double settingRelativeY = 20;

    private final ArrayList<ModuleButton> moduleButtons = new ArrayList<>();

    private ModuleButton currentButton = null;


    @Override
    public void init() {
        if (this.moduleButtons.isEmpty()) {
            for (Module module : Tori.INSTANCE.moduleManager.getModules()) {
                ModuleButton button = new ModuleButton(module);
                if (ClickGUIModule.INSTANCE.debug.getValue()) Logger.println("Created module button " + button.text);
                if (!module.getSettings().isEmpty()) {
                    for (Setting setting : module.getSettings()) {
                        if (setting.getValue() instanceof Boolean) {
                            button.settingButtons.add(new BooleanButton(setting));
                        }
                        if (setting.getValue() instanceof Number) {
                            button.settingButtons.add(new SliderButton(setting));
                        }
                        if (setting.getValue() instanceof String) {
                            button.settingButtons.add(new StringButton(setting));
                        }
                    }
                }
                this.moduleButtons.add(button);
            }
        }
    }


    /**
     * draw the screen
     * @param mouseX the mouse x, dispatched with the render function
     * @param mouseY the mouse y, dispatched with the render function
     * @param partialTicks the partialTicks, also dispatched with the render function
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        final Window window = this.getMinecraft().getWindow();

        int moduleRelativeY = (int) this.moduleRelativeY;

        int settingRelativeY = (int) this.settingRelativeY;

        final int relativeX = 10;
        final int width = (window.getScaledWidth() / 2) - (relativeX * 2);

        final int settingX = relativeX + width + (relativeX * 2);

        for (ModuleButton moduleButton : this.moduleButtons) {
            moduleButton.render(matrixStack, mouseX, mouseY, partialTicks, width, this.buttonHeight, relativeX, moduleRelativeY);
            moduleRelativeY += this.buttonHeight + 1;
        }

        if (this.currentButton != null && !this.currentButton.settingButtons.isEmpty()) {
            for (SettingButton button : this.currentButton.settingButtons) {
                button.render(matrixStack, mouseX, mouseY, partialTicks, width, this.buttonHeight, settingX, settingRelativeY);
                settingRelativeY += this.buttonHeight + 1;
            }
        }
    }

    /**
     * runs and processes mouse clicks
     * @param mouseX the x pos of the mouse
     * @param mouseY the y pos of the mouse
     * @param mouseButton the button clicked
     */
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        for (ModuleButton button : this.moduleButtons) {
            if (this.isHovered(mouseX, mouseY, button.width, button.height, button.x, button.y)) {
                if (mouseButton == 1) {
                    this.currentButton = button;
                }
                button.click(mouseX, mouseY, mouseButton);
                return;
            }
        }
        if (this.currentButton != null && !this.currentButton.settingButtons.isEmpty()) {
            for (SettingButton settingButton : this.currentButton.settingButtons) {
                if (this.isHovered(mouseX, mouseY, settingButton.width, settingButton.height, settingButton.x, settingButton.y)) {
                    settingButton.click(mouseX, mouseY, mouseButton);
                    return;
                }
            }
        }

    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (this.currentButton != null) {
            for (SettingButton settingButton : this.currentButton.settingButtons) {
                settingButton.release(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {

        char typedChar = 0;

        String str = InputUtil.fromKeyCode(keyCode, 0).getTranslationKey();

        if (str != null) {
            str = str.replaceFirst("key.keyboard.", "");

            if (str.length() > 0) {
                typedChar = str.charAt(0);
            }
        }


        for (ModuleButton button : this.moduleButtons) {
            button.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double amount) {
        this.moduleRelativeY += amount;
        this.settingRelativeY += amount;
    }
}
