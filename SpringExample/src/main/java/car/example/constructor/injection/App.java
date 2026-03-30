package car.example.constructor.injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationConstructorInjection.xml");

        Car myCar = applicationContext.getBean("myCar", Car.class);

        myCar.displayDetails();
    }
}
