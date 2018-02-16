package by.vinty.starters;

public class StartThread {
    public static void main(String[] args) {

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Привет тебе " + threadName);
        };
        task.run();

        Thread thread = new Thread(task);
        thread.start();
        System.out.println("OK!");
    }

}
