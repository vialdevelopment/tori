package io.github.vialdevelopment.tori.client.gui.scene.module.button;

import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.util.FontUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

/**
 * @author cats
 * @since April 30, 2020
 */
public class BooleanButton extends SettingButton {

    public BooleanButton(Setting setting) {
        super(setting);
    }

    /**
     * starts or stops sliding on click
     */
    @Override
    public void click(double mouseX, double mouseY, int mouseButton) {
        this.setting.setValue(!((boolean) this.setting.getValue()));
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

        Screen.fill(matrixStack, x, y, x + width, y + height, (boolean) this.setting.getValue() ? new Color(0, 0, 255, 175).getRGB() : new Color(128, 128, 128, 175).getRGB());
        // I am going to explain this mess, I use the scale to change the font size

        FontUtil.drawShadowedString(matrixStack, this.text,
                x + 2,
                y + 2,
                -1,
                textScale);

        FontUtil.drawShadowedString(matrixStack, this.setting.getValue().toString(),
                x + ((width / 2f)
                        - (FontUtil.getStringWidth(this.setting.getValue().toString(), textScale) / 2)),
                y + 2,
                -1,
                textScale);
    }
}
