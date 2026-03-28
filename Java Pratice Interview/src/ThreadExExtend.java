class MyThread extends Thread {
    private String name;

    MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(name + " - count: " + i);
            try {
                Thread.sleep(500); // pause 500ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadExExtend {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("Thread-A");
        MyThread t2 = new MyThread("Thread-B");

        t1.start(); // calls run() in a new thread
        t2.start(); // both run concurrently
    }
}