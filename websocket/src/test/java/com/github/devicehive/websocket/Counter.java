package com.github.devicehive.websocket;

class Counter {
    private int count;

    Counter() {
        count = 0;
    }

    void increment() {
        ++count;
    }

    int getCount() {
        return count;
    }
}