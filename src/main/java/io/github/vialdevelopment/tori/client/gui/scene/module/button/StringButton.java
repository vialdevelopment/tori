package io.github.vialdevelopment.tori.client.gui.scene.module.button;

import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.util.FontUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

/**
 * @author cats
 */
public class StringButton extends SettingButton {
    public StringButton(Setting setting) {
        super(setting);
    }

    /**
     * the string drawn
     */
    private String currentString;

    /**
     * shows if the button is being drawn on
     */
    private boolean writing;

    /**
     * starts or stops sliding on click
     */
    @Override
    public void click(double mouseX, double mouseY, int mouseButton) {
        this.writing = !this.writing;
    }

    /**
     * I handle the settings in this render
     * On render, I draw the settings, and also set them
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, int width, int height, int x, int y) {

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        // If we're not writing, set the current string
        if (!this.writing) {
            this.currentString = (String) this.setting.getValue();
        }

        Screen.fill(matrixStack, x, y, x + width, y + height, Color.PINK.getRGB());
        FontUtil.drawShadowedString(matrixStack, this.text,
                x + 2,
                y + 2,
                -1,
                textScale);

        FontUtil.drawShadowedString(matrixStack, this.currentString,
                x + ((width / 2f)
                        - (FontUtil.getStringWidth(this.currentString, textScale)/ 2)),
                y + 2,
                -1,
                textScale);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (this.writing) {
            switch(keyCode) {
                case 1:
                    break;
                case 14:
                    if (this.currentString.length() > 0) {
                        this.currentString = (this.currentString).substring(0, this.currentString.length() - 1);
                    }
                    break;
                case 28:
                    this.setSettingString(this.currentString);
                    break;
                default:
                    this.currentString = this.currentString + typedChar;
            }
        }
    }

    /**
     * sets the value
     * nice and simple, doesn't need much
     */
    private void setSettingString(String string) {
        if (this.writing) {
            this.setting.setValue(string);
            this.writing = false;
        }
    }
}
