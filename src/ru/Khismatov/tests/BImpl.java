package ru.Khismatov.tests;

public class BImpl implements B {
    private int y = 0;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String cacheTest() {
        System.out.println("original method");
        return "test";
    }
}