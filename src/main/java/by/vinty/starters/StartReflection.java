package by.vinty.starters;

import by.vinty.entity.Employee;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class StartReflection {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Employee employee = new Employee();

        Class aClass = employee.getClass();
        String myClass = aClass.getName();
        System.out.println(myClass);

        //Модификаторы доступа
        int mods = aClass.getModifiers();

        if (Modifier.isPublic(mods)){
            System.out.println("Public");
        }
        if (Modifier.isAbstract(mods)) {
            System.out.println("abstract");
        }
        if (Modifier.isFinal(mods)) {
            System.out.println("final");
        }
        //Получение родителя
        Class superClass = employee.getClass();
        System.out.println(superClass.getSuperclass());

        //Получение полей класса
        Field[] publicFields = aClass.getFields();
        for (Field field : publicFields) {
            Class fieldType = field.getType();
            System.out.println("Имя: " + field.getName() + " Тип: " + fieldType.getName());
        }

        Field[] allFields = aClass.getDeclaredFields();
        for (Field field : allFields){
            Class fieldType = field.getType();
            System.out.println("Имя: " + field.getName() + " тип: " + fieldType.getName());
        }

        Employee.class.getMethod("myMethod").invoke(employee);

    }
}
