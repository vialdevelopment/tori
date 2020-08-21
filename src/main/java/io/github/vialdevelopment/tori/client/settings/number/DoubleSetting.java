package io.github.vialdevelopment.tori.client.settings.number;

import io.github.vialdevelopment.tori.api.setting.Setting;

public class DoubleSetting extends Setting<Double> {
    private double doubleValue;

    public DoubleSetting(String name, double value) {
        super(name, value);
        this.doubleValue = value;
    }

    @Deprecated
    @Override
    public Double getValue() {
        return this.getDoubleValue();
    }


    @Deprecated
    @Override
    public void setValue(Double value) {
        this.setDoubleValue(value);
    }

    public double getDoubleValue() {
        return this.doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }
}
