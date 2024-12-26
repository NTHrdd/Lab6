package ru.Khismatov;

import ru.Khismatov.Annotations.Cache.Cache;
import ru.Khismatov.Annotations.Cache.DatabaseService;
import ru.Khismatov.Annotations.Cache.JustClass;
import ru.Khismatov.CachingUtils.CachingUtils;
import ru.Khismatov.tests.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import static ru.Khismatov.Annotations.Default.DefaultExample.checkDefaultMethod;
import static ru.Khismatov.Annotations.Invoke.InvokeExample.callInvokeMethods;
import static ru.Khismatov.Annotations.ToString.ToStringExample.testToString;
import static ru.Khismatov.Annotations.Two.TwoExample.testTwo;
import static ru.Khismatov.Annotations.Validate.ValidateExample.testValidate;

public class Main {
    static public void testCache() {
        Class<?> dataProcessorClass = JustClass.class;
        if (dataProcessorClass.isAnnotationPresent(Cache.class)) {
            Cache cacheAnnotation = dataProcessorClass.getAnnotation(Cache.class);
            String[] cachedEntities = cacheAnnotation.value();
            System.out.println("Cached entities for JustClass: " + java.util.Arrays.toString(cachedEntities)); // Выведет: []
        }

        Class<?> databaseServiceClass = DatabaseService.class;
        if (databaseServiceClass.isAnnotationPresent(Cache.class)) {
            Cache cacheAnnotation = databaseServiceClass.getAnnotation(Cache.class);
            String[] cachedEntities = cacheAnnotation.value();
            System.out.println("Cached entities for DatabaseService: " + java.util.Arrays.toString(cachedEntities)); // Выведет: [users, products]
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of task: ");
        int num_task = scan.nextInt();
        while (num_task < 10) {
            switch (num_task) {
                case 1:
                    A a = new A();
                    Object[] proxiedObjects = CachingUtils.cache(a); // Обрабатываем объект для кэширования
                    A cachedA = (A) proxiedObjects[0]; // Получаем прокси для объекта A
                    System.out.println(cachedA.cacheTest()); // Вызывает метод, кэширует результат
                    System.out.println(cachedA.cacheTest()); // Возвращает из кэша
                    a.setX(10); // Изменяем состояние объекта
                    System.out.println(cachedA.cacheTest()); // Пересчитывает значение, так как состояние изменилось
                    System.out.println(cachedA.cacheTest()); // Возвращает из кэша
                    a.setX(20); // Изменяем состояние объекта снова
                    System.out.println(cachedA.cacheTest()); // Пересчитывает значение
                    B b = new BImpl();
                    proxiedObjects = CachingUtils.cache(b); // Обрабатываем объект для кэширования
                    B cachedB = (B) proxiedObjects[0]; // Получаем прокси для объекта B
                    System.out.println(cachedB.cacheTest()); // Вызывает метод, кэширует результат
                    System.out.println(cachedB.cacheTest()); // Возвращает из кэша
                    ((BImpl) b).setY(5); // Изменяем состояние объекта
                    System.out.println(cachedB.cacheTest()); // Пересчитывает значение, так как состояние изменилось
                    System.out.println(cachedB.cacheTest()); // Возвращает из кэша
                    break;
                case 2:
                    callInvokeMethods();
                    break;
                case 3:
                    checkDefaultMethod();
                    break;
                case 4:
                    testToString();
                    break;
                case 5:
                    testValidate();
                    break;
                case 6:
                    testTwo();
                    break;
                case 7:
                    testCache();
                    break;
                case 8:
                    TestClass testClass = new TestClass();
                    proxiedObjects = CachingUtils.cache(testClass);
                    С proxiedService = (С) proxiedObjects[0];
                    System.out.println(proxiedService.cacheTest()); // Кэшируется
                    System.out.println(proxiedService.cacheTest()); // Из кэша
                    System.out.println(testClass.nonCachedMethod()); // Не кэшируется
                    break;
            }
            System.out.println("Enter the number of task: ");
            num_task = scan.nextInt();
        }
    }
}