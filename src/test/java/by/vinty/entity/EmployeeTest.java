package by.vinty.entity;

import by.vinty.utils.ConcurrentUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EmployeeTest {

//    private static SessionFactory SESSION_FACTORY;
//    private static Session SESSION;
    private static SessionFactory SESSION_FACTORY;

    @BeforeClass
    public static void init(){
    }

    //Тестирование правильного маппинга
    @Test
    public void testSaveEmployee(){
        SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        Session session = SESSION_FACTORY.openSession();

        Employee employee = new Employee();
        String etalonName = "Vinty"+ConcurrentUtils.getRandomName();
        employee.setName(etalonName);
        Long id = (Long) session.save(employee);
        System.out.println(id);
        if (session.contains("Employee", employee)){
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
    }

    @AfterClass
    public static void finish(){
        SESSION_FACTORY.close();
    }
}