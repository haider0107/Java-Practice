package org.example;

public class Alien {

    private int age;
    private Computer comp;

//    private Laptop lap;

    public Alien(){
        System.out.println("Constructor of Alien");
    }

//    public Alien(int age,Computer com){
//        this.age = age;
//        this.comp = com;
//        System.out.println("Constructor of Alien with age and computer");
//    }

//    public Alien(int age,Laptop lap){
//        this.age = age;
//        this.lap = lap;
//        System.out.println("Constructor of Alien with age and laptop");
//    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Computer getComp() {
        return comp;
    }

    public void setComp(Computer comp) {
        this.comp = comp;
    }

    //    public Laptop getLap() {
//        return lap;
//    }
//
//    public void setLap(Laptop lap) {
//        this.lap = lap;
//    }

    public void code(){
        comp.config();
        System.out.println("Alien Code !!!");
    }
}
