public class ThreadExample {
    public static void main(String[] args) {
        MyTask taskA = new MyTask("Task-A");
        MyTask taskB = new MyTask("Task-B");

        Thread t1 = new Thread(taskA); // wrap the Runnable
        Thread t2 = new Thread(taskB);

        t1.start();
        t2.start();
    }
}

class MyTask implements Runnable {
    private String name;

    MyTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(name + " - count: " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}