package io.github.vialdevelopment.tori.client.gui.scene.module.button;

import io.github.vialdevelopment.tori.api.setting.impl.Setting;
import io.github.vialdevelopment.tori.util.FontUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

/**
 * @author cats
 * @since April 30, 2020
 */
public class SliderButton extends SettingButton {

    /**
     * determines the buttons sliding and if it is being slid
     */
    private pos sliderPos = null;

    /**
     * the value it starts with
     */
    private Object startValue;

    public SliderButton(Setting setting) {
        super(setting);
    }

    /**
     * starts or stops sliding on click
     */
    @Override
    public void click(double mouseX, double mouseY, int mouseButton) {
        if (this.sliderPos == null) {
            this.sliderPos = new pos(mouseX, mouseY);
            this.startValue = this.setting.getValue();
        }
    }

    @Override
    public void release(double mouseX, double mouseY, int button) {
        if (this.sliderPos != null) {
            this.sliderPos = null;
        }
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

        if (this.sliderPos != null) {

            double value = (mouseX - this.sliderPos.x)/* * ((double) this.setting.value)*/ / 10f;

            if (this.startValue instanceof Double) {
                this.setting.setValue((double) this.startValue + value);
            } else if (this.startValue instanceof Float) {
                this.setting.setValue((float) ((float) this.startValue + value));
            } else if (this.startValue instanceof Long) {
                this.setting.setValue((long) ((long) this.startValue + value));
            } else if (this.startValue instanceof Integer) {
                this.setting.setValue((int) ((int) this.startValue + value));
            }
        }

        Screen.fill(matrixStack, x, y, x + width, y + height, new Color(0,0, 128, 175).getRGB());
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

    /**
     * cute little class I use to keep track of pos
     */
    static class pos {
        public double x;
        public double y;

        pos(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
