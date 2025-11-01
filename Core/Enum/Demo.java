enum Status{
    Running, Failed, Pending, Success;
}

public class Demo {
    public static void main(String[] args) {
        // int n = 5;

        Status s = Status.Running;

        // System.out.println(s);

        System.out.println(s.ordinal()); // 0, 1, 2, 3 number of enum

        Status[] ss = Status.values(); // Running, Failed, Pending, Success

        // Printing all the values where s1 is the enum
        for (Status s1 : ss) {
            System.out.println(s1);
        }
    }
}
