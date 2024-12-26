package ru.Khismatov.Annotations.Invoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeExample {
    @Invoke
    public void invokeMethod1() {
        System.out.println("Invoke method is called");
        System.out.println("1");
    }

    @Invoke
    public void invokeMethod2() {
        System.out.println("Invoke method is called");
        System.out.println("2");
    }

    public void nonInvokeMethod() {System.out.println("Non invoke method is called");}

    public static void callInvokeMethods() throws InvocationTargetException, IllegalAccessException {
        InvokeExample example = new InvokeExample();
        Method[] methods = InvokeExample.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Invoke.class)) {
                method.invoke(example);
            }
        }
    }
}
