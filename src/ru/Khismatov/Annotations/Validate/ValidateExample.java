package ru.Khismatov.Annotations.Validate;

@Validate({String.class, Integer.class})
public record ValidateExample(String name, int age) {
    static public void testValidate() {
        Class<ValidateExample> clazz = ValidateExample.class;
        if (clazz.isAnnotationPresent(Validate.class)) {
            Validate validateAnnotation = clazz.getAnnotation(Validate.class);
            System.out.println("Annotation @Validate found on class: " + clazz.getName());
            Class<?>[] classes = validateAnnotation.value();
            System.out.println("Classes indicated in annotation @Validate:");
            for (Class<?> c : classes) {
                System.out.println("- " + c.getName());
            }
        } else System.out.println("Annotation @Validate doesn't found on class: " + clazz.getName());
    }
}
