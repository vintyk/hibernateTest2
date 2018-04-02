package by.vinty.entity;

import by.vinty.utils.ConcurrentUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EmployeeTest {

    //    private static SessionFactory SESSION_FACTORY;
//    private static Session SESSION;
    private static SessionFactory SESSION_FACTORY;

    @BeforeClass
    public static void init() {
    }

    @AfterClass
    public static void finish() {
        SESSION_FACTORY.close();
    }

    //Тестирование правильного маппинга
    @Test
    public void testSaveEmployee() {
        SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        Session session = SESSION_FACTORY.openSession();

        Employee employee = new Employee();
        String etalonName = "Vinty" + ConcurrentUtils.getRandomName();
        employee.setName(etalonName);
        Long id = (Long) session.save(employee);
        System.out.println(id);
        if (session.contains("Employee", employee)) {
            assertThat(employee.getName(), is(etalonName));
            assertThat(employee.getId(), is(id));
            System.out.println("Объект Employee в SESSION присутствует!");
        }
        Assert.assertEquals(etalonName, session.find(Employee.class, id).getName());

        List<Employee> resultList =
                session.createQuery(
                        "from Employee", Employee.class).getResultList();
//        "from Employee where id between 1 and 20", Employee.class).getResultList();
        resultList.forEach(System.out::println);
        session.close();
        System.out.println("Vinty запили тест и выложил его в репозиторий Git для Николая Бандюка");
    }

    @Test
    public void testFail(){
        boolean tempBoolean = true;
        assertThat(tempBoolean, is(1 == 2));
        System.out.println("Это сделано специально...");
        System.out.println("Так надо...");
    }
}