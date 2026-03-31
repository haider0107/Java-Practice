package com.example.autowire.constructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("autowireByConstructor.xml");

        Car myCar = applicationContext.getBean("myCar", Car.class);

        myCar.displayDetails();
    }
}
