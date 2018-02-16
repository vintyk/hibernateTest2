package by.vinty.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ConcurrentUtils {

//    public static void executeThis(Class myClass, int countThread, int numberOfRepetition, Method method) {
//        ExecutorService executor = Executors.newFixedThreadPool(countThread);
//        Method m = method.getName();
//        IntStream.range(0, numberOfRepetition)
//                .forEach(element -> executor.submit(myClass::save));
//        ConcurrentUtils.stop(executor);
//        executor.shutdownNow();
//    }


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

    public synchronized static String getRandomName() {
        String symbols = "abcdefghijklmnopqrstuvwxyzABCEDFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder randString = new StringBuilder();
        int count = (int) (Math.random() * 20);
        for (int i = 0; i < count; i++) {
            randString.append(symbols.charAt((int) (Math.random() * symbols.length())));
        }
        return String.valueOf(randString);
    }

    public static long getLastId(Session session, String entity) {
        long result = 0;
        String hql = "SELECT Max(id) FROM " + entity;
        try {
            Query query = session.createQuery(hql);
            List list = query.list();
            try {
                result = (long) list.get(0);
            } catch (IndexOutOfBoundsException e) {
                e.fillInStackTrace();
            }
            return result;
        } catch (NullPointerException n) {
            n.fillInStackTrace();
        }
        return 0;
    }

    public static void deleteInsertedRecordsFromTable(Session session, String entity, long idMoreThan) {
        Transaction transaction = session.beginTransaction();
        String hql = "DELETE FROM " + entity + " WHERE id > " + idMoreThan;
        Query query = session.createQuery(hql);
        query.executeUpdate();
        transaction.commit();
    }
}
