package by.vinty.starters;

import by.vinty.entity.Robots;

import java.lang.reflect.InvocationTargetException;

public class StartCallMethods {
    private static int count = 0;

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Запуск в многопоточном фреймворке несколько раз
        //Все вручную созданные методы запускаются в многопоточной среде
        //на вход подаются тестовые случайные данные.
        ConcurrentUtils.execMethod(new Robots(), 20,3);
    }
}
