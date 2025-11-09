package org.example;

public class Laptop implements Computer {

    @Override
    public void config(){
        System.out.println("i5, 16GB, 1TB");
    }
}
