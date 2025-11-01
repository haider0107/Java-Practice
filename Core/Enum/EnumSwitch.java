public class EnumSwitch {
    public static void main(String[] args) {
        Status s = Status.Running;
        switch (s) {
            case Running:
                System.out.println("Running");
                break;
            case Failed:
                System.out.println("Failed");
                break;
            case Pending:
                System.out.println("Pending");
                break;
            case Success:
                System.out.println("Success");
                break;
        }
    }
}
