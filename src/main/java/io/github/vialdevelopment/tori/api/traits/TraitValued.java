package io.github.vialdevelopment.tori.api.traits;

public interface TraitValued<T> {
    T getValue();

    void setValue(T value);
}
