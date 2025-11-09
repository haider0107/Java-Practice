package org.example;

import org.example.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Laptop lap = context.getBean(Laptop.class);
        lap.config();










        //        System.out.println( "Hello World!" );
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
//
//        Alien obj = (Alien) context.getBean("alien");
//
//        System.out.println(obj.getAge());
//
//        obj.code();
    }
}
