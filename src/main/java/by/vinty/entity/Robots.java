package by.vinty.entity;

import by.vinty.starters.ConcurrentUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public final class Robots {

    private long id;
    private String name;
    private Ram ram;

//    public void printAbout(String str, Integer countRandom) {
//        System.out.println("Я простой метод класса Robots. Печатаю: " + str+ " и добавляю число: " + countRandom);
//    }

    public long summaChar(String name) {
        String randomName = name;
        long numberChar = 0;
        for (char element : randomName.toCharArray()) {
            numberChar++;
        }
        id++;
        System.out.println("Считаю символы в случайном слове. В '"
                + randomName + "'. Их: " + numberChar + " id = " + id);
        return id;
    }

    public String rename(String newName) {
        name = newName;
//        System.out.println("Устанавливаем значение поля 'name' на случайное: " + name);
        return newName;
    }
//
//    public long sumNumber(long a, long b){
//    long result = a+b;
//        System.out.println("Я складываю 2 числа. Результат работы метода: " + result);
//        return result;
//    }
//
//    public boolean yesOrNo(long a, int b, Integer i){
//        boolean result;
//        if ((a==b)||(b!=i)){
//            result = true;
//        }else {
//            result = false;
//        }
//        System.out.println(result);
//        return result;
//    }
//
//    public Robots createObjectOfClass(){
//        final Robots robots = new Robots();
//        robots.setName(ConcurrentUtils.getRandomName());
//        System.out.println(robots);
//        return robots;
//    }
//
//    public Robots createObjectOfClassByName(Ram obj){
//        final Robots robots = new Robots();
//        robots.setName(obj.getName());
//        System.out.println(robots);
//        return robots;
//    }
//
//    public Robots newRobotsFromOldcomplectRobors(Robots robots, Ram ram, long countRam, String nameRam){
//        ram.setName(nameRam);
//        ram.setCount((int) countRam);
//        final Robots robots1 = new Robots();
//        robots1.setName(robots.getName());
//        robots1.setRam(ram);
//        System.out.println(robots1);
//        return robots1;
//    }
}
