package by.vinty.stressTesting;

import by.vinty.entity.Employee;
import by.vinty.utils.ConcurrentUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Start_001_StressTest {
    private static ReentrantLock lock = new ReentrantLock();
    private static SessionFactory SESSION_FACTORY =
            new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    private final int THREAD_COUNT = 20;
    private final int NUMBER_OF_REPETITION = 10000;
    private final String ENTITY = "Employee";
    private int count = 0;
    private int countSync = 0;
    private long lastId = 0;

    @Test
    public void employeeDaoFindByNameStressTest() {

        final Session session = SESSION_FACTORY.openSession();
        lastId = ConcurrentUtils.getLastId(session, ENTITY);


        System.out.println("Количество потоков: " + THREAD_COUNT);
        System.out.println("Попытка выполнить метод save() " + NUMBER_OF_REPETITION + " раз.");

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        IntStream.range(0, NUMBER_OF_REPETITION)
                .forEach(element -> executor.submit(this::save));
        ConcurrentUtils.stop(executor);
        executor.shutdownNow();

        if (NUMBER_OF_REPETITION != count) {
            System.out.println("ВНИМАНИЕ! Метод save() не потокобезопасен!");
            System.out.println("При попытке выполниться " + NUMBER_OF_REPETITION + " раз," +
                    " он гарантированно выполнился " + count);
        } else {
            System.out.println("Метод save() выполнился " + count + " раз.");
        }
        System.out.println("--------------------------------------------");

        final ExecutorService executorSync = Executors.newFixedThreadPool(THREAD_COUNT);
        System.out.println("Метод save() обернут в потоконезависимую обертку: saveSync()");
        System.out.println("Количество потоков: " + THREAD_COUNT);
        System.out.println("Попытка выполнить метод save() " + NUMBER_OF_REPETITION + " раз.");
        IntStream.range(0, NUMBER_OF_REPETITION)
                .forEach(element -> executorSync.submit(this::saveSync));
        ConcurrentUtils.stop(executorSync);
//        executor.submit(this::save);
        executor.shutdownNow();

        if (NUMBER_OF_REPETITION != countSync) {
            System.out.println("ВНИМАНИЕ! Метод save() не потокобезопасен!");
            System.out.println("При попытке выполниться " + NUMBER_OF_REPETITION + " раз," +
                    " он гарантированно выполнился " + countSync);
        } else {
            System.out.println("Потоконезависимый Метод save() выполнился " + countSync + " раз.");
        }
        System.out.println("--------------------------------------------");
        if (countSync > count) {
            System.out.println("Вывод: Необходима синхронизация метода.");
        }

        ConcurrentUtils.deleteInsertedRecordsFromTable(session, ENTITY, lastId);
        session.close();
        SESSION_FACTORY.close();
        System.out.println("Тест окончен.");
    }

    private void save() {
        Session session = SESSION_FACTORY.openSession();
        Employee employee = new Employee();
        employee.setName(ConcurrentUtils.getRandomName());
        session.save(employee);
        count++;
        session.close();
    }

    private void saveSync() {
        Session session = SESSION_FACTORY.openSession();
        lock.lock();
        try {
            Employee employee = new Employee();
            employee.setName(ConcurrentUtils.getRandomName());
            session.save(employee);
            countSync++;
        } finally {
            lock.unlock();
        }
        session.close();
    }
}
