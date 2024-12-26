package ru.Khismatov.Annotations.ToString;

@ToString
public class ToStringExample {
    private final String name;
    @ToString(value = ToString.Values.NO)
    private final int age;
    private final String city;

    public ToStringExample(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getName() {return name;}

    public int getAge() {return age;}

    public String getCity() {return city;}

    @Override
    public String toString() {return "Person{" + "name='" + name + '\'' + ", age=" + age + ", city='" + city + '\'' + '}';}

    static public void testToString() {
        ToStringExample toStringExample = new ToStringExample("Alice", 30, "New York");
        boolean classToStringAnnotationPresent = ToStringExample.class.isAnnotationPresent(ToString.class);
        System.out.println("Annotation @ToString on class ToStringExample: " + classToStringAnnotationPresent);
        try {
            java.lang.reflect.Field ageField = ToStringExample.class.getDeclaredField("age");
            if (ageField.isAnnotationPresent(ToString.class)) {
                ToString annotation = ageField.getAnnotation(ToString.class);
                System.out.println("Annotation @ToString on field age and value of value: " + annotation.value());
            }
        } catch (NoSuchFieldException e) {e.printStackTrace();}
    }
}
