package io.github.vialdevelopment.tori.client.settings.number;

import io.github.vialdevelopment.tori.api.setting.Setting;

public class FloatSetting extends Setting<Float> {

    private float floatValue;

    public FloatSetting(String name, float value) {
        super(name, value);
        this.floatValue = value;
    }

    @Deprecated
    @Override
    public Float getValue() {
        return this.getFloatValue();
    }

    @Deprecated
    @Override
    public void setValue(Float value) {
        this.setFloatValue(value);
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public void setFloatValue(float value) {
        this.floatValue = value;
    }
}
