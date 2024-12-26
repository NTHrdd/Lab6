package ru.Khismatov.Annotations.Two;

@Two(first = "Hello", second = 42)
public class TwoExample {
    private String a;
    private int b;

    static public void testTwo() {
        Class<?> clazz = TwoExample.class;
        if (clazz.isAnnotationPresent(Two.class)) {
            Two annotation = clazz.getAnnotation(Two.class);
            String firstValue = annotation.first();
            int secondValue = annotation.second();
            System.out.println("Value of first: " + firstValue);
            System.out.println("Value of second: " + secondValue);
        }
    }
}
