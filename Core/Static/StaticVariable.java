class Mobile{
    String brand;
    String price;
    static String name;

    public void show(){
        System.out.println(brand + " : " + price + " : " + name);
    }

    public static void show1(Mobile obj){
        System.out.println(obj.brand + " : " + obj.price + " : " + name);
    }
}

public class StaticVariable {
    public static void main(String[] args){
        Mobile m = new Mobile();

        m.brand = "Sumsung";
        m.price = "2000";

        Mobile.name = "10 X Plux";

        m.show();

        Mobile.show1(m);
    }
}
