package in.kbblogs.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Alien {

    @Autowired
    Laptop laptop;

    public void coding(){
//        System.out.println("Alien is coding");
        laptop.config();
    }

}

