package com.infinitum;

import net.minecraft.item.Item;

public class RegistryType {
    public RegistryType setItem(Item item) {
        this.item = item;
        return this;
    }

    public RegistryType setName(String name) {
        this.name = name;
        return this;
    }

    protected Item item;
    protected String name;
}