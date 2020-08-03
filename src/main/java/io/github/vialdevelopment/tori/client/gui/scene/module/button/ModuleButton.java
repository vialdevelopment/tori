package io.github.vialdevelopment.tori.client.gui.scene.module.button;

import io.github.vialdevelopment.tori.api.runnable.impl.module.Module;
import io.github.vialdevelopment.tori.client.gui.api.Button;
import io.github.vialdevelopment.tori.util.FontUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author cats
 * @since April 30, 2020
 * This is used for modules, it's very epic
 */
public class ModuleButton extends Button {

    /**
     * the module that this button is
     */
    public Module module;

    /**
     * the setting buttons that are associated with this module button
     */
    public ArrayList<SettingButton> settingButtons = new ArrayList<>();

    public ModuleButton(Module module) {
        super(module.getName(), Color.GRAY);
        this.module = module;
    }

    /**
     * dispatches a click for the button, and determines what it does
     */
    @Override
    public void click(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) this.module.toggle();
    }

    /**
     * it renders the button
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        if (this.module.getState()) {
            Color color = new Color(110, 150, 105);
            this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 240);
        } else {
            Color color = Color.GRAY;
            this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 175);
        }

        Screen.fill(matrixStack, x, y, x + width, y + height, this.color.getRGB());

        FontUtil.drawShadowedString(matrixStack, this.text,
                x + ((width / 2f)
                        - (FontUtil.getStringWidth(this.text, textScale) / 2)),
                y + 2,
                -1,
                textScale);
    }
}
