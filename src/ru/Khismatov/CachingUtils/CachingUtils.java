package ru.Khismatov.CachingUtils;

import ru.Khismatov.Annotations.Cache.Cache;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class CachingUtils {

    public static Object[] cache(Object... objects) {
        if (objects == null || objects.length == 0) {
            throw new IllegalArgumentException("Objects cannot be null or empty");
        }
        Object[] proxiedObjects = new Object[objects.length];
        for (int i = 0; i < objects.length; i++) {
            Object obj = objects[i];
            if (obj == null) {throw new IllegalArgumentException("Object at index " + i + " cannot be null");}
            Class<?> objClass = obj.getClass();
            Cache cacheAnnotation = objClass.getAnnotation(Cache.class);
            if (cacheAnnotation != null) {
                String[] cacheableMethods = cacheAnnotation.value();
                proxiedObjects[i] = createProxy(obj, objClass.getInterfaces(), cacheableMethods);
            }
            else proxiedObjects[i] = obj;
        }
        return proxiedObjects;
    }

    @SuppressWarnings("unchecked")
    private static <T> T createProxy(T obj, Class<?>[] interfaces, String[] cacheableMethods) {
        return (T) Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                interfaces.length > 0 ? interfaces : new Class[]{obj.getClass()},
                new CachingInvocationHandler<>(obj, cacheableMethods)
        );
    }

    private static class CachingInvocationHandler<T> implements InvocationHandler {
        private final T target;
        private final Map<Method, Object> cache = new HashMap<>();
        private final Map<Method, Object> lastState = new HashMap<>();
        private final Set<String> cacheableMethodNames;

        public CachingInvocationHandler(T target, String[] cacheableMethods) {
            this.target = target;

            // Если список методов пустой (аннотация без параметров), кэшируем все методы
            if (cacheableMethods == null || cacheableMethods.length == 0) {
                this.cacheableMethodNames = null; // null означает "кэшировать все методы"
            } else {
                this.cacheableMethodNames = new HashSet<>(Arrays.asList(cacheableMethods));
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Не кэшируем методы с параметрами
            if (args != null && args.length > 0) {return method.invoke(target, args);}
            // Проверяем, является ли метод кэшируемым
            if (!isCacheableMethod(method)) {return method.invoke(target);}
            // Если метод уже есть в кэше, проверяем состояние объекта
            if (cache.containsKey(method)) {
                Object currentState = getState(target);
                Object lastKnownState = lastState.get(method);
                if (Objects.equals(currentState, lastKnownState)) {return cache.get(method);}
            }
            // Вызываем метод, кэшируем результат и обновляем состояние
            Object result = method.invoke(target);
            cache.put(method, result);
            lastState.put(method, getState(target));
            return result;
        }

        private boolean isCacheableMethod(Method method) {
            // Если список кэшируемых методов не задан, кэшируем все методы без параметров
            if (cacheableMethodNames == null) {return method.getParameterCount() == 0;}
            // Кэшируем только методы, перечисленные в аннотации
            return cacheableMethodNames.contains(method.getName()) && method.getParameterCount() == 0;
        }

        private Object getState(T obj) {
            // Универсальный способ определения состояния объекта
            try {
                List<Object> fieldValues = new ArrayList<>();
                for (Field field : obj.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    fieldValues.add(field.get(obj));
                }
                return fieldValues;
            } catch (Exception e) {throw new RuntimeException("Unable to retrieve object state", e);}
        }
    }
}