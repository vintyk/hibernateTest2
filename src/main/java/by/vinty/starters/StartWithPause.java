package by.vinty.starters;

import java.util.concurrent.TimeUnit;

public class StartWithPause {
    public static void main(String[] args) {

        Runnable runnable = () -> {
            try {

                final String name = Thread.currentThread().getName();
                System.out.println("StartReflection Foo " + name);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("End.");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };
        final Thread thread = new Thread(runnable);
        thread.start();
    }
}
