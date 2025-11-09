package in.kbblogs.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Laptop implements Computer {

    public void compile(){
        System.out.println("Compling in Laptop");
    }
}
