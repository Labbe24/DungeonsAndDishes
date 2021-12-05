package com.dungeonsanddishes.game;

public class Pair<TKey, TValue> {
    private TKey _key;
    private TValue _value;

    public Pair(TKey key, TValue value) {
        _key = key;
        _value = value;
    }

    public TKey GetKey() {
        return _key;
    }

    public TValue GetValue() {
        return _value;
    }
}