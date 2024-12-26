package ru.Khismatov.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.Khismatov.CachingUtils.CachingUtils;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест для метода CachingUtils.cache.
 *
 * Проверяется:
 * 1. Корректность кэширования методов, указанных в аннотации @Cache.
 * 2. Отсутствие кэширования для методов, не указанных в аннотации @Cache.
 * 3. Сброс кэша при изменении состояния объекта.
 */
class CachingUtilsTest {

    private TestClass originalObject;
    private Object cachedObject;

    /**
     * Подготовка объектов для теста.
     * Создаётся оригинальный объект и его прокси с кэшированием.
     */
    @BeforeEach
    void setUp() {
        originalObject = new TestClass();
        Object[] proxiedObjects = CachingUtils.cache(originalObject);
        cachedObject = proxiedObjects[0];
    }

    /**
     * Тест: Проверка кэширования метода getCachedValue, указанного в аннотации @Cache.
     */
    @Test
    void testCachedMethod() throws Exception {
        // Первый вызов метода: результат должен быть рассчитан и кэширован
        String firstCall = (String) cachedObject.getClass().getMethod("getCachedValue").invoke(cachedObject);
        // Второй вызов метода: результат должен быть извлечён из кэша
        String secondCall = (String) cachedObject.getClass().getMethod("getCachedValue").invoke(cachedObject);

        // Проверяем, что результаты совпадают (кэш работает)
        assertEquals(firstCall, secondCall, "Кэшированный метод должен возвращать одно и то же значение при повторных вызовах без изменения состояния.");
    }

    /**
     * Тест: Проверка сброса кэша при изменении состояния объекта.
     */
    @Test
    void testCacheInvalidationOnStateChange() throws Exception {
        // Первый вызов метода: результат рассчитывается и кэшируется
        String firstCall = (String) cachedObject.getClass().getMethod("getCachedValue").invoke(cachedObject);

        // Изменяем состояние объекта
        originalObject.setX(10);

        // Второй вызов метода: результат должен быть пересчитан
        String secondCall = (String) cachedObject.getClass().getMethod("getCachedValue").invoke(cachedObject);

        // Проверяем, что результаты отличаются (кэш сброшен из-за изменения состояния)
        assertNotEquals(firstCall, secondCall, "Кэш должен сбрасываться при изменении состояния объекта.");
    }

    /**
     * Тест: Проверка отсутствия кэширования для метода nonCachedMethod, не указанного в аннотации @Cache.
     */
    @Test
    void testNonCachedMethod() throws Exception {
        // Первый вызов метода: результат рассчитывается
        String firstCall = (String) cachedObject.getClass().getMethod("nonCachedMethod").invoke(cachedObject);
        // Второй вызов метода: результат рассчитывается заново
        String secondCall = (String) cachedObject.getClass().getMethod("nonCachedMethod").invoke(cachedObject);

        // Проверяем, что результаты отличаются (метод не кэшируется)
        assertNotEquals(firstCall, secondCall, "Метод, не указанный в аннотации @Cache, не должен кэшироваться.");
    }
}