package io.github.vialdevelopment.tori.api.setting;

public interface ISetting<T> {
    T getValue();

    void setValue(T value);
}
