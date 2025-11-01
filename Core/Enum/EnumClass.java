enum Laptop {
    // Objects
    Lenevo(2000), DELL(3000), HP(5000), MAC(10000);

    private int price;

    private Laptop(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

public class EnumClass {
    public static void main(String[] args) {
        // Laptop l = Laptop.Lenevo;

        // System.out.println(l);
        // System.out.println(l.getPrice());

        for(Laptop lap: Laptop.values()){
            System.out.println(lap + " : " + lap.getPrice());
        }
    }
}
