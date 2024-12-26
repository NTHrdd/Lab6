package ru.Khismatov.tests;

public class A implements B {
    private int x = 0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String cacheTest() {
        System.out.println("original method");
        return "test";
    }

    public String testWithParam(int param) {
        return "test with param " + param;
    }
}