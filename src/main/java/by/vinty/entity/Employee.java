package by.vinty.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@Getter
@Setter
@ToString
public final class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name = "name")
    private String name;

    public void myMethod() {
        System.out.println("Я метод класса Employee. Совершенно простой.");
        System.out.println("Я изменил кардинальным образом метод!!!!");
    }

    public void myMethod2() {
        System.out.println("Я совершенно другой метод. Умею что-то считать.");
        System.out.println("Я изменил кардинальным образом метод!!!!");
    }

    public void myMethod3() {
        System.out.println("Я метод, который что-то там делает с именем.");
        System.out.println("Я изменил кардинальным образом метод!!!!");
    }
}
