package by.vinty.dao;

import by.vinty.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class EmployeeDao {

    public static Employee findByName(String name, Session session) {
        Employee result = null;
        String hql = "FROM Employee E WHERE E.name = '" + name + "'";
        Query query = session.createQuery(hql);
        List employeeList = query.list();
        try {
            result = (Employee) employeeList.get(0);
        } catch (IndexOutOfBoundsException e) {
            e.fillInStackTrace();
        }
        return result;
    }

    public static Optional<Employee> findById(long id, Session session){
        Employee employee = new Employee();
        String hql = "FROM Employee E WHERE E.id = " + id;
        Query query = session.createQuery(hql);
        List employeeList = query.list();
        try {
            employee = (Employee) employeeList.get(0);
        } catch (IndexOutOfBoundsException e) {
            e.fillInStackTrace();
        }
        return Optional.ofNullable(employee);
    }
}
