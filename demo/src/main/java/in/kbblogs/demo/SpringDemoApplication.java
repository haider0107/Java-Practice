package in.kbblogs.demo;

import in.kbblogs.demo.model.Alien;
import in.kbblogs.demo.model.Laptop;
import in.kbblogs.demo.service.LaptopService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringDemoApplication.class, args);
//        System.out.println("Hello World");

        LaptopService service = context.getBean(LaptopService.class);

        Laptop laptop = context.getBean(Laptop.class);
        service.addLaptop(laptop);

        Alien ali = context.getBean(Alien.class);
        ali.coding();
    }

}
