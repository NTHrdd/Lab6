package ru.Khismatov.tests;

import ru.Khismatov.Annotations.Cache.Cache;

/**
 * Класс для проверки кэширования.
 * Аннотирован с помощью @Cache, что позволяет указать методы, подлежащие кэшированию.
 */
@Cache({"getCachedValue", "anotherCachedMethod"})
public class TestClass implements С {
    private int x;

    /**
     * Кэшируемый метод.
     * Возвращает значение, зависящее от состояния объекта.
     */
    public String getCachedValue() {
        return "Cached Value: x = " + x + ", timestamp = " + System.currentTimeMillis();
    }

    /**
     * Кэшируемый метод.
     * Возвращает значение, зависящее только от времени.
     */
    public String anotherCachedMethod() {
        return "Another Cached Value: " + System.currentTimeMillis();
    }

    /**
     * Некэшируемый метод.
     * Возвращает значение, зависящее только от времени.
     */
    public String nonCachedMethod() {
        return "Non-Cached Value: " + System.currentTimeMillis();
    }

    /**
     * Изменение состояния объекта.
     */
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String cacheTest() {
        System.out.println("original method");
        return "test";
    }
}