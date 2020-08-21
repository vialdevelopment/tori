package io.github.vialdevelopment.tori.client.settings;

import io.github.vialdevelopment.tori.api.setting.Setting;

public class BooleanSetting extends Setting<Boolean> {

    private boolean booleanValue;

    public BooleanSetting(String name, boolean value) {
        super(name, value);
        this.booleanValue = value;
    }

    @Deprecated
    @Override
    public Boolean getValue() {
        return this.getBooleanValue();
    }

    @Deprecated
    @Override
    public void setValue(Boolean value) {
        this.setBooleanValue(value);
    }

    public boolean getBooleanValue() {
        return this.booleanValue;
    }

    public void setBooleanValue(boolean value) {
        this.booleanValue = value;
    }
}
