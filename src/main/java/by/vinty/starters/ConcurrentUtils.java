package by.vinty.starters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ConcurrentUtils {

    public static void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("termination interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("killing non-finished tasks");
            }
            executor.shutdownNow();
        }
    }

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
    //testAllMethodsOfThisClass - объект класса из которого будут извлечены все самописные методы + get-теры
    //numberOfRepetitions - количество повторов запуска каждого метода (рекомендовано 1000).
    //numberThreads - количество потоков (рекомендовано 10).
    public static void execMethod(Object testAllMethodsOfThisClass, int numberOfRepetitions, int numberThreads) throws NoSuchMethodException, IllegalAccessException {
        Class c = testAllMethodsOfThisClass.getClass();
        Method[] methods = c.getMethods();
        Map<Object,Object> firsResultHashMap = Collections.synchronizedMap(new LinkedHashMap<>());
        List<Object> synchronizedList = Collections.synchronizedList(new LinkedList<>());
        for (Method method : methods) {
            if (
                    (!method.getName().substring(0, 3).equals("set".substring(0, 3)))
                            &&
                            ((!method.getName().equals(("notify")))
                                    &&
                                    (!method.getName().equals("notifyAll"))
                                    &&
                                    (!method.getName().equals("wait"))
                                    &&
                                    (!method.getName().equals("toString"))
                                    &&
                                    (!method.getName().equals("equals"))
                                    &&
                                    (!method.getName().equals("hashCode"))
                                    &&
                                    (!method.getName().equals("getClass"))
                            )) {
                System.out.println();
                Class[] pTypes = method.getParameterTypes();
                System.out.println("Имя метода: "
                        + method.getName()
                        + "   Возвращаемый тип: "
                        + method.getReturnType().getName());
                System.out.print("Типы параметров: ------");
                for (Class paramType : pTypes) {
                    System.out.print("  " + paramType.getName());
                }
                System.out.println();
                System.out.println("-------------------------------------");

                Method methodWithParams = c.getMethod(method.getName(), pTypes);
                Object[] args = new Object[]{};
                if (pTypes.length == 0) {
                    args = pTypes;
                } else {
                    Object[] arrayArgs = new Object[pTypes.length];
                    for (int i = 0; i < pTypes.length; i++) {
                        if (pTypes[i].getName().equals("java.lang.String")) {
                            arrayArgs[i] = ConcurrentUtils.getRandomName();
                        } else if (pTypes[i].getName().equals("java.lang.Integer")
                                || pTypes[i].getName().equals("int")) {
                            arrayArgs[i] = (int) ConcurrentUtils.getRandomNumber();
                        } else if (pTypes[i].getName().equals("java.lang.Long")
                                || pTypes[i].getName().equals("long")) {
                            arrayArgs[i] = ConcurrentUtils.getRandomNumber();
                        } else {
                            Class t = pTypes[i];
                            try {
                                arrayArgs[i] = t.newInstance();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            }
                        }
                        args = arrayArgs;
                    }
                }
                Object[] finalArgs = args;
                    ExecutorService executor = Executors.newFixedThreadPool(numberThreads);
                synchronizedList.clear();
                IntStream.range(0, numberOfRepetitions)
                            .forEach(element -> executor.submit(() -> {
                                try {
                                    synchronizedList.add((methodWithParams.invoke(testAllMethodsOfThisClass, finalArgs)));
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                                if (synchronizedList.size() == numberOfRepetitions){
                                    System.out.println(synchronizedList);
                                }
                            }));
                stop(executor);
            }
        }
    }

    public synchronized static String getRandomName() {
        String symbols = "abcdefghijklmnopqrstuvwxyzABCEDFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder randString = new StringBuilder();
        int count = (int) (Math.random() * 50);
        for (int i = 0; i < count; i++) {
            randString.append(symbols.charAt((int) (Math.random() * symbols.length())));
        }
        return String.valueOf(randString);
    }

    public synchronized static long getRandomNumber() {
        long count = (long) (Math.random() * 1000000);
        return count;
    }
}
