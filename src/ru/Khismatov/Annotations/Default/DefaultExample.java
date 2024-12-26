package ru.Khismatov.Annotations.Default;

import java.lang.reflect.Field;

@Default(String.class)
public class DefaultExample {
    @Default(Integer.class)
    private Object field;

    public void printField() {
        System.out.println("Type's field: " + field.getClass().getSimpleName());
    }

    public static void checkDefaultMethod() {
        Class<?> clazz = DefaultExample.class;
        if (clazz.isAnnotationPresent(Default.class)) {
            Default annotation = clazz.getAnnotation(Default.class);
            System.out.println("Class annotated @Default with value = " + annotation.value().getSimpleName());
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Default.class)) {
                Default defaultAnnotation = field.getAnnotation(Default.class);
                System.out.println("Field " + field.getName() + " annotated @Default with value = " + defaultAnnotation.value().getSimpleName());
            }
        }
    }
}
