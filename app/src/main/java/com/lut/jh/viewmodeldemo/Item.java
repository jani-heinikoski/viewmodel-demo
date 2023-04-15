package com.lut.jh.viewmodeldemo;

import java.io.Serializable;

public class Item implements Serializable {
    private final String content;

    public Item(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
