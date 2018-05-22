package by.vinty.dao;

import by.vinty.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EmployeeDaoTest {

    private static SessionFactory SESSION_FACTORY;
//            = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();

    @Test
    public void findByName() {
        SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        Session session = SESSION_FACTORY.openSession();

        Employee employee = new Employee();
        employee.setName("Vinty");
        session.save(employee);

        String iMustFindName = "Vinty";
        Employee resultEmployeeFromDb = EmployeeDao.findByName(iMustFindName, session);
        try {
//            assertThat(resultEmployeeFromDb.getName(), is(employee.getName()));
        } catch (NullPointerException e) {
            Assert.assertEquals(true, "--- Сработало исключение: Метод  'findByName' не работает или в БД не найдено: " + iMustFindName + " !!!---");
        }
        session.close();
    }

    @Test
    public void findByIdTest() {
        Session session = SESSION_FACTORY.openSession();

        Employee employee = new Employee();
        employee.setName("Aragorn");
        Long employeeId = EmployeeDao.saveEmployee(employee, session);

        Long iMustFindId = employeeId;
        Long idFromDb = 0l;
        Optional<Employee> employeeFromDb = Optional
                .of(EmployeeDao.findById(iMustFindId, session).orElseThrow(NullPointerException::new));
        try {
            if (employeeFromDb.isPresent()) {
                idFromDb = employeeFromDb.get().getId();
                System.out.println("В БД Найден Employee с Id: " + idFromDb + " и именем: " + employeeFromDb.get().getName());
            } else {
                System.out.println("Из БД не вернулся объект класса Employee!");
            }
        } catch (NullPointerException e) {
            Assert.assertEquals(true, "--- Сработало исключение: Метод  'findById' не работает или в БД не найдено: " + iMustFindId + " !!!---");
        }
//        assertThat(iMustFindId, is(idFromDb));
    }

    @AfterClass
    public static void finish() {
        SESSION_FACTORY.close();
    }
}